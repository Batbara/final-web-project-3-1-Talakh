package by.tr.web.dao.impl;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.ShowDAO;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import by.tr.web.domain.builder.UserBuilder;
import by.tr.web.domain.builder.UserReviewBuilder;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.common.TransactionInterruptionException;
import by.tr.web.exception.dao.movie.CounterDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ShowDAOSqlImpl implements ShowDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addReview(UserReview userReview) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            boolean isReviewPresent = isReviewPresentInDB(connection, userReview);
            if (isReviewPresent) {
                updateReview(connection, userReview);
            } else {
                addReviewToDB(connection, userReview);
            }
            connection.commit();
        } catch (SQLException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Error while preparing SQL-statement", e);
        } catch (TransactionInterruptionException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Transaction was interrupted", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void rateShow(UserReview userReview) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            boolean isReviewPresent = isReviewPresentInDB(connection, userReview);
            if (isReviewPresent) {
                updateRate(connection, userReview);
            } else {
                addRate(connection, userReview);
            }
            connection.commit();
        } catch (SQLException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Error while preparing SQL-statement", e);
        } catch (TransactionInterruptionException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Transaction was interrupted", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public List<UserReview> takeReviewList(int startNum, int reviewsNum, String reviewStatus, int showId)
            throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String reviewListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_CONTENT_REVIEW_LIST);

            preparedStatement = connection.prepareStatement(reviewListQuery);

            preparedStatement.setString(1, reviewStatus);
            preparedStatement.setInt(2, showId);
            preparedStatement.setInt(3, startNum);
            preparedStatement.setInt(4, reviewsNum);

            resultSet = preparedStatement.executeQuery();

            List<UserReview> reviews = new ArrayList<>();
            UserReview review;
            User user;
            while (resultSet.next()) {

                String reviewTitle = resultSet.getString(1);
                String reviewContent = resultSet.getString(2);
                Timestamp postDate = resultSet.getTimestamp(3);
                int userId = resultSet.getInt(4);
                String userName = resultSet.getString(5);

                user = new UserBuilder()
                        .addId(userId)
                        .addUserName(userName)
                        .create();

                review = new UserReviewBuilder()
                        .addShowId(showId)
                        .addUser(user)
                        .addReviewTitle(reviewTitle)
                        .addReviewContent(reviewContent)
                        .addPostDate(postDate)
                        .create();

                reviews.add(review);
            }
            return reviews;

        } catch (SQLException e) {
            throw new DAOException("SQL eError while taking movie list from DB", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int countShowReviews(int showId) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String reviewsCounterQuery = queryConfig.getSqlQuery(SqlQueryName.COUNT_POSTED_REVIEWS_QUERY);

            preparedStatement = connection.prepareStatement(reviewsCounterQuery);
            preparedStatement.setInt(1, showId);

            resultSet = preparedStatement.executeQuery();

            int reviewsCounter = 0;
            if (resultSet.next()) {
                reviewsCounter = resultSet.getInt(1);
            }
            return reviewsCounter;
        } catch (SQLException e) {
            throw new CounterDAOException("Error while executing reviews counter query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public double takeShowRating(int showId) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String showRatingQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_SHOW_RATING);

            preparedStatement = connection.prepareStatement(showRatingQuery);
            preparedStatement.setInt(1, showId);

            resultSet = preparedStatement.executeQuery();

            double showRating = 0;
            if (resultSet.next()) {
                showRating = resultSet.getDouble(1);
            }
            return showRating;
        } catch (SQLException e) {
            throw new CounterDAOException("Error while executing show rating query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private void addRate(Connection connection, UserReview userReview) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = userReview.getUser().getId();
            int showId = userReview.getShowId();
            int userRate = userReview.getUserRate();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String addRateQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_USER_RATE);

            preparedStatement = connection.prepareStatement(addRateQuery);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, showId);
            preparedStatement.setInt(3, userRate);

            int rowsAdded = preparedStatement.executeUpdate();
            if (rowsAdded == 0) {
                throw new TransactionInterruptionException("Unexpected result while adding user rate");
            }

        } catch (SQLException e) {
            throw new TransactionInterruptionException("Cannot add review to data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement);
        }
    }

    private void updateRate(Connection connection, UserReview userReview) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = userReview.getUser().getId();
            int showId = userReview.getShowId();
            int userRate = userReview.getUserRate();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String updateRateQuery = queryConfig.getSqlQuery(SqlQueryName.UPDATE_USER_RATE);

            preparedStatement = connection.prepareStatement(updateRateQuery);
            preparedStatement.setInt(1, userRate);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, showId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new TransactionInterruptionException("Unexpected result while updating user rate");
            }

        } catch (SQLException e) {
            throw new TransactionInterruptionException("Cannot update review in data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement);
        }
    }

    private void updateReview(Connection connection, UserReview userReview) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = userReview.getUser().getId();
            int showId = userReview.getShowId();
            String reviewTitle = userReview.getReviewTitle();
            String reviewContent = userReview.getReviewContent();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String updateReviewQuery = queryConfig.getSqlQuery(SqlQueryName.UPDATE_USER_REVIEW);

            preparedStatement = connection.prepareStatement(updateReviewQuery);

            preparedStatement.setString(1, reviewTitle);
            preparedStatement.setString(2, reviewContent);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, showId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new TransactionInterruptionException("Unexpected result while updating user review");
            }

        } catch (SQLException e) {
            throw new TransactionInterruptionException("Cannot update review in data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement);
        }
    }

    private void addReviewToDB(Connection connection, UserReview userReview) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = userReview.getUser().getId();
            int showId = userReview.getShowId();
            String reviewTitle = userReview.getReviewTitle();
            String reviewContent = userReview.getReviewContent();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String checkReviewPresenceQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_USER_REVIEW);

            preparedStatement = connection.prepareStatement(checkReviewPresenceQuery);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, showId);
            preparedStatement.setString(3, reviewTitle);
            preparedStatement.setString(4, reviewContent);

            int rowsAdded = preparedStatement.executeUpdate();
            if (rowsAdded == 0) {
                throw new TransactionInterruptionException("Unexpected result while adding user rate");
            }

        } catch (SQLException e) {
            throw new TransactionInterruptionException("Cannot add review to data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement);
        }
    }

    private boolean isReviewPresentInDB(Connection connection, UserReview userReview) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            int userId = userReview.getUser().getId();
            int showId = userReview.getShowId();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String checkReviewPresenceQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_USER_REVIEW_PRESENCE);

            preparedStatement = connection.prepareStatement(checkReviewPresenceQuery);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, showId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int isReviewPresent = resultSet.getInt(1);
                return isReviewPresent > 0;
            } else {
                throw new DAOException("Cannot find user review");
            }

        } catch (SQLException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Cannot check review in data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }
}
