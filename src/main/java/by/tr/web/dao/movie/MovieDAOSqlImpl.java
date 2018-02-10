package by.tr.web.dao.movie;

import by.tr.web.controller.util.TypeFormatUtil;
import by.tr.web.dao.ShowDaoUtil;
import by.tr.web.dao.configuration.Configuration;
import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.configuration.QueryConfigurationFactory;
import by.tr.web.dao.constant.SqlQueryName;
import by.tr.web.dao.exception.CounterDAOException;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.exception.EntityNotUniqueException;
import by.tr.web.dao.exception.TransactionInterruptionException;
import by.tr.web.domain.Movie;
import by.tr.web.domain.Show;
import by.tr.web.domain.builder.MovieBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
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
                int showId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String showPoster = resultSet.getString(3);
                int year = resultSet.getInt(4);
                double rating = resultSet.getDouble(5);


                movie = new MovieBuilder()
                        .addId(showId)
                        .addTitle(title)
                        .addYear(year)
                        .addPoster(showPoster)
                        .addUserRating(rating)
                        .create();

                movies.add(movie);
            }
            return movies;

        } catch (SQLException e) {
            throw new DAOException("SQL error while taking movie list from DB", e);
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

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getMovieQueryConfig();
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

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getMovieQueryConfig();
            String takeMovieInfoQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_MOVIE_INFO_QUERY);

            preparedStatement = connection.prepareStatement(takeMovieInfoQuery);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("Movie with id " + id + " not found");
            }

            Movie movie = setMovieInfo(id, resultSet);
            movie = (Movie) ShowDaoUtil.setShowGenres(movie, lang, connection);
            movie = (Movie) ShowDaoUtil.setShowCountries(movie, lang, connection);

            return movie;
        } catch (SQLException e) {
            throw new DAOException("Error while executing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int addMovie(Movie movieEnglish, Show russianTranslation) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            ShowDaoUtil.checkShowForUniqueness(connection, movieEnglish);

            String showType = Show.ShowType.MOVIE.toString().toLowerCase();
            int showId = ShowDaoUtil.addNewShow(connection, movieEnglish, showType);

            movieEnglish.setShowId(showId);
            russianTranslation.setShowId(showId);

            ShowDaoUtil.addShowCountries(connection, movieEnglish);
            ShowDaoUtil.addShowGenres(connection, movieEnglish);

            ShowDaoUtil.addNewShowTranslation(connection, movieEnglish);
            ShowDaoUtil.addNewShowTranslation(connection, russianTranslation);

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getMovieQueryConfig();
            String addMovieInfoQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_NEW_MOVIE);

            preparedStatement = connection.prepareStatement(addMovieInfoQuery);

            preparedStatement.setInt(1, movieEnglish.getShowId());
            preparedStatement.setLong(2, movieEnglish.getBudget());
            preparedStatement.setLong(3, movieEnglish.getBoxOffice());
            preparedStatement.setString(4, movieEnglish.getFormattedMpaaRating());

            preparedStatement.executeUpdate();
            connection.commit();
            return showId;
        } catch (SQLException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Error occurred while adding new movie to data base ", e);
        } catch (TransactionInterruptionException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Transaction was interrupted while adding new show", e);
        } catch (EntityNotUniqueException e) {
            connectionPool.rollbackConnection(connection);
            throw new EntityNotUniqueException("Such movie is already in data base", e);
        }finally {
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

            int year = TypeFormatUtil.getYearFromDate(premiereDate);

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


    private String formMovieListQuery(String orderType) {

        Configuration queryConfig = QueryConfigurationFactory.getInstance().getMovieQueryConfig();
        String takeMovieListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_MOVIE_LIST_QUERY);

        return String.format(takeMovieListQuery, orderType);
    }
}
