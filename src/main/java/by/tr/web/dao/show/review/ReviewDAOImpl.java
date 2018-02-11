package by.tr.web.dao.show.review;

import by.tr.web.dao.configuration.Configuration;
import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.configuration.QueryConfigurationFactory;
import by.tr.web.dao.configuration.SqlQueryName;
import by.tr.web.dao.exception.CounterDAOException;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.exception.NoSuchEntityException;
import by.tr.web.dao.exception.TransactionInterruptionException;
import by.tr.web.domain.Review;
import by.tr.web.domain.User;
import by.tr.web.domain.builder.UserBuilder;
import by.tr.web.domain.builder.UserReviewBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addReview(Review review) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            boolean isReviewPresent = isReviewPresentInDB(connection, review);
            if (isReviewPresent) {
                updateReview(connection, review);
            } else {
                addReviewToDB(connection, review);
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
    public void rateShow(Review review) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            boolean isReviewPresent = isReviewPresentInDB(connection, review);
            if (isReviewPresent) {
                updateRate(connection, review);
            } else {
                addRate(connection, review);
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
    public List<Review> takeReviewsOnModeration(int startReview, int reviewsNum) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String reviewListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_MODERATED_REVIEW_LIST_QUERY);

            preparedStatement = connection.prepareStatement(reviewListQuery);

            preparedStatement.setInt(1, startReview);
            preparedStatement.setInt(2, reviewsNum);

            resultSet = preparedStatement.executeQuery();

            List<Review> reviews = new ArrayList<>();
            Review review;
            User user;
            while (resultSet.next()) {

                int showId = resultSet.getInt(1);
                String reviewTitle = resultSet.getString(2);
                String reviewContent = resultSet.getString(3);
                Timestamp postDate = resultSet.getTimestamp(4);
                int userId = resultSet.getInt(5);
                String userName = resultSet.getString(6);
                String userStatus = resultSet.getString(7);

                user = new UserBuilder()
                        .addId(userId)
                        .addUserName(userName)
                        .addUserStatus(userStatus)
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
            throw new DAOException("SQL eError while taking moderating reviews from DB", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void postReview(int userId, int showId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String postReviewQuery = queryConfig.getSqlQuery(SqlQueryName.POST_USER_REVIEW);

            preparedStatement = connection.prepareStatement(postReviewQuery);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, showId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error while preparing SQL-statement for review posting", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void deleteReview(int userId, int showId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            isShowPresentInDB(connection, showId);

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String deleteUserReview = queryConfig.getSqlQuery(SqlQueryName.DELETE_USER_REVIEW);

            preparedStatement = connection.prepareStatement(deleteUserReview);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, showId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error while preparing SQL-statement for review deletion", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public int countReviewsOnModeration() throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String reviewsCounterQuery = queryConfig.getSqlQuery(SqlQueryName.COUNT_MODERATED_REVIEWS_QUERY);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(reviewsCounterQuery);

            int reviewsCounter = 0;
            if (resultSet.next()) {
                reviewsCounter = resultSet.getInt(1);
            }
            return reviewsCounter;
        } catch (SQLException e) {
            throw new CounterDAOException("Error while counting reviews on moderation", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    private void addRate(Connection connection, Review review) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = review.getUser().getId();
            int showId = review.getShowId();
            int userRate = review.getUserRate();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
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

    private void updateRate(Connection connection, Review review) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = review.getUser().getId();
            int showId = review.getShowId();
            int userRate = review.getUserRate();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
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

    private void updateReview(Connection connection, Review review) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = review.getUser().getId();
            int showId = review.getShowId();
            String reviewTitle = review.getReviewTitle();
            String reviewContent = review.getReviewContent();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
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

    private void addReviewToDB(Connection connection, Review review) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = review.getUser().getId();
            int showId = review.getShowId();
            String reviewTitle = review.getReviewTitle();
            String reviewContent = review.getReviewContent();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
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

    private boolean isReviewPresentInDB(Connection connection, Review review) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            int userId = review.getUser().getId();
            int showId = review.getShowId();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
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

    private boolean isShowPresentInDB(Connection connection, int showId) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String checkShowPresenceQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_SHOW_EXISTENCE_BY_ID);

            preparedStatement = connection.prepareStatement(checkShowPresenceQuery);
            preparedStatement.setInt(1, showId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int isShowPresent = resultSet.getInt(1);
                return isShowPresent > 0;
            } else {
                throw new NoSuchEntityException("Show with id=" + showId + " doesn't exist");
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot check if show with id=" + showId + " is present in data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

}
