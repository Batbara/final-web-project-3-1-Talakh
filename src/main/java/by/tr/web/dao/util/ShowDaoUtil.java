package by.tr.web.dao.util;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Show;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.movie.MovieInitializationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
