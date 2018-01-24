package by.tr.web.dao.impl;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.TvShowDAO;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.domain.TvShow;
import by.tr.web.domain.builder.TvShowBuilder;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.movie.CounterDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TvShowDAOSqlImpl implements TvShowDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public List<TvShow> takeSortedTvShowList(int startPosition, int tvShowsNumber, String orderType, String lang)
            throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            String showListQuery = formTvShowListQuery(orderType);
            preparedStatement = connection.prepareStatement(showListQuery);

            preparedStatement.setString(1, lang);
            preparedStatement.setInt(2, startPosition);
            preparedStatement.setInt(3, tvShowsNumber);

            resultSet = preparedStatement.executeQuery();

            List<TvShow> tvShows = new ArrayList<>();
            TvShow tvShow;
            while (resultSet.next()) {
                int showID = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String poster = resultSet.getString(3);
                int year = resultSet.getInt(4);
                int seasonsNum = resultSet.getInt(5);
                String tvShowStatus = resultSet.getString(6);
                double rating = resultSet.getDouble(7);

                tvShow = new TvShowBuilder()
                        .addId(showID)
                        .addTitle(title)
                        .addPoster(poster)
                        .addYear(year)
                        .addUserRating(rating)
                        .addSeasonsNum(seasonsNum)
                        .addShowStatus(TvShow.ShowStatus.valueOf(tvShowStatus.toUpperCase()))
                        .create();

                tvShows.add(tvShow);
            }
            return tvShows;

        } catch (SQLException e) {
            throw new DAOException("SQL error while taking tv-show list", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public TvShow takeTvShow(int id, String lang) throws DAOException {
        return null;
    }

    @Override
    public int countTvShow() throws CounterDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();

            Configuration queryConfig = ConfigurationFactory.getInstance().getTvShowConfig();
            String tvShowCounterQuery = queryConfig.getSqlQuery(SqlQueryName.TVSHOW_COUNTER_QUERY);

            resultSet = statement.executeQuery(tvShowCounterQuery);

            int tvShowCounter = 0;
            if (resultSet.next()) {
                tvShowCounter = resultSet.getInt(1);
            }
            return tvShowCounter;
        } catch (SQLException e) {
            throw new CounterDAOException("Error while executing tv-show counter query", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }
    private String formTvShowListQuery(String orderType) {

        Configuration queryConfig = ConfigurationFactory.getInstance().getTvShowConfig();
        String takeTvShowListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_TVSHOW_LIST_QUERY);

        return String.format(takeTvShowListQuery, orderType);
    }
}
