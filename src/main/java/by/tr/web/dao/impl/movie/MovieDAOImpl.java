package by.tr.web.dao.impl.movie;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Movie;
import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import by.tr.web.domain.builder.MovieBuilder;
import by.tr.web.domain.builder.UserBuilder;
import by.tr.web.domain.builder.UserReviewBuilder;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.movie.MovieCounterDAOException;
import by.tr.web.exception.dao.movie.MovieInitializationException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public List<Movie> takeOrderedMovieList(int startPosition, int moviesNumber, String orderType, String lang)
            throws DAOException {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            String movieListQuery = formMovieListQuery(orderType);
            preparedStatement = connection.prepareStatement(movieListQuery);

            preparedStatement.setString(1, lang);
            preparedStatement.setInt(2, startPosition);
            preparedStatement.setInt(3, moviesNumber);

            resultSet = preparedStatement.executeQuery();

            List<Movie> movies = new ArrayList<>();
            Movie movie;
            while (resultSet.next()) {
                int showID = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String poster = resultSet.getString(3);
                int year = resultSet.getInt(4);
                double rating = resultSet.getDouble(5);

                movie = new MovieBuilder()
                        .addId(showID)
                        .addTitle(title)
                        .addPoster(poster)
                        .addYear(year)
                        .addUserRating(rating)
                        .create();

                movies.add(movie);
            }
            return movies;

        } catch (SQLException e) {
            throw new DAOException("Error while preparing SQL-statement", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int countMovies() throws MovieCounterDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();

            Configuration queryConfig = ConfigurationFactory.getInstance().getMovieQueryConfig();
            String movieCounterQuery = queryConfig.getSqlQuery(SqlQueryName.MOVIE_COUNTER_QUERY);

            resultSet = statement.executeQuery(movieCounterQuery);

            int movieCounter = 0;
            if (resultSet.next()) {
                movieCounter = resultSet.getInt(1);
            }
            return movieCounter;
        } catch (SQLException e) {
            throw new MovieCounterDAOException("Error while executing movie counter query", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    @Override
    public Movie takeMovie(int id, String lang) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getMovieQueryConfig();
            String takeMovieInfoQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_MOVIE_INFO_QUERY);

            preparedStatement = connection.prepareStatement(takeMovieInfoQuery);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("Movie with id " + id + " not found");
            }

            Movie movie = setMovieInfo(id, resultSet);

            setMovieGenres(movie, lang, connection);
            setMovieCountries(movie, lang, connection);
            setMovieReviews(movie, connection);

            return movie;
        } catch (SQLException e) {
            throw new DAOException("Error while executing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private Movie setMovieInfo(int movieId, ResultSet resultSet) throws DAOException {
        try {

            String title = resultSet.getString(1);
            Date yearDate = resultSet.getDate(2);
            Date premiereDate = resultSet.getDate(3);
            Time runtime = resultSet.getTime(4);
            Long boxOffice = resultSet.getLong(5);
            Long movieBudget = resultSet.getLong(6);
            String mpaaRating = resultSet.getString(7);
            String synopsis = resultSet.getString(8);
            String poster = resultSet.getString(9);

            int year = getYearFromDate(yearDate);

            Movie movie = new MovieBuilder()
                    .addId(movieId)
                    .addTitle(title)
                    .addYear(year)
                    .addPremiereDate(premiereDate)
                    .addRuntime(runtime)
                    .addBoxOffice(boxOffice)
                    .addBudget(movieBudget)
                    .addMpaaRating(Movie.MPAARating.valueOf(mpaaRating))
                    .addSynopsis(synopsis)
                    .addPoster(poster)
                    .create();

            return movie;
        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing movie information", e);
        }

    }

    private void setMovieGenres(Movie movie, String lang, Connection connection) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String takeGenreQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_GENRE_OF_SHOW_QUERY);

            preparedStatement = connection.prepareStatement(takeGenreQuery);

            preparedStatement.setInt(1, movie.getShowID());
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            Genre genre;
            while (resultSet.next()) {
                String genreName = resultSet.getString(1);

                genre = new Genre(genreName);
                movie.addGenre(genre);
            }

        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing genre list", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private void setMovieCountries(Movie movie, String lang, Connection connection) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String takeCountry = queryConfig.getSqlQuery(SqlQueryName.TAKE_COUNTRY_OF_SHOW_QUERY);

            preparedStatement = connection.prepareStatement(takeCountry);

            preparedStatement.setInt(1, movie.getShowID());
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            Country country;
            while (resultSet.next()) {
                String countryName = resultSet.getString(1);

                country = new Country();
                country.setCountryName(countryName);
                movie.addCountry(country);
            }

        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing country list", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private void setMovieReviews(Movie movie, Connection connection) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String takeReviewsQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_REVIEWS_OF_SHOW_QUERY);

            preparedStatement = connection.prepareStatement(takeReviewsQuery);
            preparedStatement.setInt(1, movie.getShowID());

            resultSet = preparedStatement.executeQuery();

            UserReview review;
            User user;
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                int userRate = resultSet.getInt(3);
                String reviewContent = resultSet.getString(4);
                Timestamp postDate = resultSet.getTimestamp(5);

                user = new UserBuilder()
                        .addId(userID)
                        .addUserName(userName)
                        .create();

                review = new UserReviewBuilder()
                        .addShowId(movie.getShowID())
                        .addUser(user)
                        .addUserRate(userRate)
                        .addReviewContent(reviewContent)
                        .addPostDate(postDate)
                        .create();

                movie.addReview(review);
            }

        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing country list", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        return year;
    }

    private String formMovieListQuery(String orderType) {

        Configuration queryConfig = ConfigurationFactory.getInstance().getMovieQueryConfig();
        String takeMovieListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_MOVIE_LIST_QUERY);

        return String.format(takeMovieListQuery, orderType);
    }
}
