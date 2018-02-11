package by.tr.web.dao.util;

import by.tr.web.dao.configuration.Configuration;
import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.configuration.QueryConfigurationFactory;
import by.tr.web.dao.configuration.SqlQueryName;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.exception.EntityNotUniqueException;
import by.tr.web.dao.exception.TransactionInterruptionException;
import by.tr.web.dao.movie.MovieInitializationException;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Show;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;

public class ShowDaoUtil {
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    public static Show setShowGenres(Show show, String lang, Connection connection) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String takeGenreQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_GENRE_OF_SHOW_QUERY);

            preparedStatement = connection.prepareStatement(takeGenreQuery);

            preparedStatement.setInt(1, show.getShowId());
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            Genre genre;
            while (resultSet.next()) {
                String genreName = resultSet.getString(1);

                genre = new Genre(genreName);
                show.addGenre(genre);
            }
            return show;
        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing genre list", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    public static Show setShowCountries(Show show, String lang, Connection connection) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String takeCountry = queryConfig.getSqlQuery(SqlQueryName.TAKE_COUNTRY_OF_SHOW_QUERY);

            preparedStatement = connection.prepareStatement(takeCountry);

            preparedStatement.setInt(1, show.getShowId());
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            Country country;
            while (resultSet.next()) {
                String countryName = resultSet.getString(1);

                country = new Country();
                country.setCountryName(countryName);
                show.addCountry(country);
            }
            return show;
        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing country list", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    public static int addNewShow(Connection connection, Show show, String showType)
            throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int showYear = show.getYear();
            Time showRuntime = show.getRuntime();
            Date showPremiereDate = show.getPremiereDate();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String addShowQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_NEW_SHOW);

            preparedStatement = connection.prepareStatement(addShowQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, showType);
            preparedStatement.setInt(2, showYear);
            preparedStatement.setTime(3, showRuntime);
            preparedStatement.setDate(4, showPremiereDate);

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            int showId;
            if (resultSet.next()) {
                showId = resultSet.getInt(1);
            } else {
                throw new TransactionInterruptionException("Unexpected result while adding new show");
            }
            return showId;

        } catch (SQLException e) {
            throw new TransactionInterruptionException("Cannot add new show to data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    public static void addNewShowTranslation(Connection connection, Show show)
            throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int showId = show.getShowId();
            String showLang = show.getLanguage();
            String showTitle = show.getTitle();
            String showSynopsis = show.getSynopsis();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String addShowQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_SHOW_TRANSLATION);

            preparedStatement = connection.prepareStatement(addShowQuery);

            preparedStatement.setInt(1, showId);
            preparedStatement.setString(2, showLang);
            preparedStatement.setString(3, showTitle);
            preparedStatement.setString(4, showSynopsis);

            preparedStatement.executeUpdate();


            String showPoster = show.getPoster();
            if (showPoster != null) {
                String addPosterQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_SHOW_POSTER);

                preparedStatement = connection.prepareStatement(addPosterQuery);

                preparedStatement.setString(1, showPoster);
                preparedStatement.setInt(2, showId);
                preparedStatement.setString(3, showLang);

                preparedStatement.executeUpdate();
            }


        } catch (SQLException e) {
            throw new TransactionInterruptionException("Cannot add show translation to data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement);
        }
    }

    public static void addShowGenres(Connection connection, Show show)
            throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int showId = show.getShowId();
            List<Genre> genreList = show.getGenreList();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String addShowQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_SHOW_GENRES);

            preparedStatement = connection.prepareStatement(addShowQuery);

            for (Genre genre : genreList) {
                preparedStatement.setInt(1, showId);
                preparedStatement.setInt(2, genre.getGenreId());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new TransactionInterruptionException("Cannot add show genres to data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement);
        }
    }

    public static void addShowCountries(Connection connection, Show show)
            throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int showId = show.getShowId();
            List<Country> countryList = show.getCountryList();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String addShowQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_SHOW_COUNTRIES);

            preparedStatement = connection.prepareStatement(addShowQuery);

            for (Country country : countryList) {
                preparedStatement.setInt(1, showId);
                preparedStatement.setInt(2, country.getCountryId());

                preparedStatement.executeUpdate();
            }


        } catch (SQLException e) {
            throw new TransactionInterruptionException("Cannot add show countries to data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement);
        }
    }

    public static boolean checkShowForUniqueness(Connection connection, Show show)
            throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            Configuration queryConfig = QueryConfigurationFactory.getInstance().getShowQueryConfig();
            String takeCountry = queryConfig.getSqlQuery(SqlQueryName.CHECK_SHOW_EXISTENCE_IN_DB);

            preparedStatement = connection.prepareStatement(takeCountry);

            preparedStatement.setString(1, show.getTitle());
            preparedStatement.setDate(2, show.getPremiereDate());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                throw new EntityNotUniqueException("Show " + show.toString() + " already present in data base");
            }
            return true;
        } catch (SQLException e) {
            throw new DAOException("Error while checking show " + show.toString() + " presence in data base", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }
}
