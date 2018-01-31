package by.tr.web.dao.impl;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.dao.util.ShowDaoUtil;
import by.tr.web.domain.Movie;
import by.tr.web.domain.builder.MovieBuilder;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.movie.CounterDAOException;
import by.tr.web.exception.dao.movie.MovieInitializationException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MovieDAOSqlImpl implements MovieDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public List<Movie> takeSortedMovieList(int startPosition, int moviesNumber, String orderType, String lang)
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
            throw new DAOException("SQL eError while taking movie list from DB", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int countMovie() throws CounterDAOException {
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
            throw new CounterDAOException("Error while executing movie counter query", e);
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
            movie = (Movie) ShowDaoUtil.setShowGenres(movie,lang, connection);
            movie = (Movie) ShowDaoUtil.setShowCountries(movie, lang, connection);

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
            Date premiereDate = resultSet.getDate(2);
            Time runtime = resultSet.getTime(3);
            Long boxOffice = resultSet.getLong(4);
            Long movieBudget = resultSet.getLong(5);
            String mpaaRating = resultSet.getString(6);
            String synopsis = resultSet.getString(7);
            String poster = resultSet.getString(8);

            int year = getYearFromDate(premiereDate);

            Movie movie = new MovieBuilder()
                    .addId(movieId)
                    .addTitle(title)
                    .addYear(year)
                    .addPremiereDate(premiereDate)
                    .addRuntime(runtime)
                    .addBoxOffice(boxOffice)
                    .addBudget(movieBudget)
                    .addMpaaRating(mpaaRating)
                    .addSynopsis(synopsis)
                    .addPoster(poster)
                    .create();

            return movie;
        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing movie information", e);
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
