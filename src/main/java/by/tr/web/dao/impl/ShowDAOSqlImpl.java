package by.tr.web.dao.impl;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.ShowDAO;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
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
import java.sql.Statement;
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
    public List<UserReview> takeShowReviewList(int startNum, int reviewsNum, int showId)
            throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String reviewListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_POSTED_REVIEW_LIST);

            preparedStatement = connection.prepareStatement(reviewListQuery);

            preparedStatement.setInt(1, showId);
            preparedStatement.setInt(2, startNum);
            preparedStatement.setInt(3, reviewsNum);

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
                String userStatus = resultSet.getString(6);

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
            throw new DAOException("SQL eError while taking show reviews from DB", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public List<UserReview> takeReviewsOnModeration(int startReview, int reviewsNum) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String reviewListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_MODERATED_REVIEW_LIST_QUERY);

            preparedStatement = connection.prepareStatement(reviewListQuery);

            preparedStatement.setInt(1, startReview);
            preparedStatement.setInt(2, reviewsNum);

            resultSet = preparedStatement.executeQuery();

            List<UserReview> reviews = new ArrayList<>();
            UserReview review;
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
    public List<Country> takeCountryList(String lang) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String countryListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_COUNTRY_LIST_QUERY);

            preparedStatement = connection.prepareStatement(countryListQuery);
            preparedStatement.setString(1, lang);

            resultSet = preparedStatement.executeQuery();

            List<Country> countries = new ArrayList<>();
            Country country;
            while (resultSet.next()) {
                int countryId = resultSet.getInt(1);
                String countryName = resultSet.getString(2);

                country = new Country(countryId, countryName);
                countries.add(country);
            }
            return countries;

        } catch (SQLException e) {
            throw new DAOException("SQL error while taking country list from DB", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Genre> takeGenreList(String lang) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String genreListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_GENRE_LIST_QUERY);

            preparedStatement = connection.prepareStatement(genreListQuery);
            preparedStatement.setString(1, lang);

            resultSet = preparedStatement.executeQuery();

            List<Genre> genres = new ArrayList<>();
            Genre genre;
            while (resultSet.next()) {
                int genreId = resultSet.getInt(1);
                String genreName = resultSet.getString(2);

                genre = new Genre(genreId, genreName);
                genres.add(genre);
            }
            return genres;

        } catch (SQLException e) {
            throw new DAOException("SQL error while taking genre list from DB", e);
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
    public void postReview(int userId, int showId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
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

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
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

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
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
