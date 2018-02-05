package by.tr.web.dao.util;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Show;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.common.TransactionInterruptionException;
import by.tr.web.exception.dao.movie.MovieInitializationException;

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
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String takeGenreQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_GENRE_OF_SHOW_QUERY);

            preparedStatement = connection.prepareStatement(takeGenreQuery);

            preparedStatement.setInt(1, show.getShowID());
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
            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String takeCountry = queryConfig.getSqlQuery(SqlQueryName.TAKE_COUNTRY_OF_SHOW_QUERY);

            preparedStatement = connection.prepareStatement(takeCountry);

            preparedStatement.setInt(1, show.getShowID());
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

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
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
            int showId = show.getShowID();
            String showLang = show.getLanguage();
            String showTitle = show.getTitle();
            String showSynopsis = show.getSynopsis();
            String showPoster = show.getPoster();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String addShowQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_SHOW_TRANSLATION);

            preparedStatement = connection.prepareStatement(addShowQuery);

            preparedStatement.setInt(1, showId);
            preparedStatement.setString(2, showLang);
            preparedStatement.setString(3, showTitle);
            preparedStatement.setString(4, showSynopsis);
            preparedStatement.setString(5, showPoster);

            preparedStatement.executeUpdate();


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
            int showId = show.getShowID();
            List<Genre> genreList = show.getGenreList();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String addShowQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_SHOW_GENRES);

            preparedStatement = connection.prepareStatement(addShowQuery);

            for (Genre genre : genreList){
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
            int showId = show.getShowID();
            List<Country> countryList = show.getCountryList();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String addShowQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_SHOW_COUNTRIES);

            preparedStatement = connection.prepareStatement(addShowQuery);

            for (Country country : countryList){
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
}
