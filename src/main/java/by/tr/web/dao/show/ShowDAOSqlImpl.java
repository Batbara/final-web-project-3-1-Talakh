package by.tr.web.dao.show;

import by.tr.web.dao.configuration.Configuration;
import by.tr.web.dao.configuration.ConfigurationFactory;
import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.constant.SqlQueryName;
import by.tr.web.dao.exception.CounterDAOException;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Review;
import by.tr.web.domain.Show;
import by.tr.web.domain.User;
import by.tr.web.domain.builder.ShowBuildingDirector;
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

public class ShowDAOSqlImpl implements ShowDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public List<Show> takeSortedShowList(int startPosition, int showsNumber, String lang) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String takeShowListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_SHOWS_LIST);
            preparedStatement = connection.prepareStatement(takeShowListQuery);

            preparedStatement.setString(1, lang);
            preparedStatement.setInt(2, startPosition);
            preparedStatement.setInt(3, showsNumber);

            resultSet = preparedStatement.executeQuery();
            ShowBuildingDirector buildingDirector = new ShowBuildingDirector();
            List<Show> shows = new ArrayList<>();
            Show show;
            while (resultSet.next()) {
                int showId = resultSet.getInt(1);
                String title = resultSet.getString(2);

                int year = resultSet.getInt(3);
                String showType = resultSet.getString(4);
                double rating = resultSet.getDouble(5);

                buildingDirector.setShowType(showType);
                show = buildingDirector.create(showId, title, year, rating);
                shows.add(show);
            }
            return shows;

        } catch (SQLException e) {
            throw new DAOException("SQL error while taking movie list from DB", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void deleteShow(int showId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String deleteShow = queryConfig.getSqlQuery(SqlQueryName.DELETE_SHOW);

            preparedStatement = connection.prepareStatement(deleteShow);

            preparedStatement.setInt(1, showId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error while preparing SQL-statement for show deletion", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public int countAllShows() throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String movieCounterQuery = queryConfig.getSqlQuery(SqlQueryName.COUNT_ALL_SHOWS_QUERY);

            resultSet = statement.executeQuery(movieCounterQuery);

            int movieCounter = 0;
            if (resultSet.next()) {
                movieCounter = resultSet.getInt(1);
            }
            return movieCounter;
        } catch (SQLException e) {
            throw new CounterDAOException("Error while executing all shows counter query", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    @Override
    public List<Review> takeShowReviewList(int startNum, int reviewsNum, int showId)
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

            List<Review> reviews = new ArrayList<>();
            Review review;
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

}
