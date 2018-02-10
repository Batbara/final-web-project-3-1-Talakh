package by.tr.web.dao.tv_show;

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
import by.tr.web.domain.Show;
import by.tr.web.domain.TvShow;
import by.tr.web.domain.builder.TvShowBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
                int showId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String poster = resultSet.getString(3);
                int year = resultSet.getInt(4);
                int seasonsNum = resultSet.getInt(5);
                String tvShowStatus = resultSet.getString(6);
                double rating = resultSet.getDouble(7);

                tvShow = new TvShowBuilder()
                        .addId(showId)
                        .addTitle(title)
                        .addPoster(poster)
                        .addYear(year)
                        .addUserRating(rating)
                        .addSeasonsNum(seasonsNum)
                        .addShowStatus(tvShowStatus)
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
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getTvShowConfig();
            String takeMovieInfoQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_TV_SHOW);

            preparedStatement = connection.prepareStatement(takeMovieInfoQuery);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("Tv-show with id " + id + " not found");
            }

            TvShow tvShow = setTvShowInfo(id, resultSet);

            tvShow = (TvShow) ShowDaoUtil.setShowGenres(tvShow, lang, connection);
            tvShow = (TvShow) ShowDaoUtil.setShowCountries(tvShow, lang, connection);

            return tvShow;
        } catch (SQLException e) {
            throw new DAOException("Error while executing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int countTvShow() throws CounterDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getTvShowConfig();
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

    @Override
    public int addTvShow(TvShow tvShowEnglish, Show russianTranslation) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            ShowDaoUtil.checkShowForUniqueness(connection, tvShowEnglish);

            String showType = Show.ShowType.TV_SERIES.toString().toLowerCase();
            int showId = ShowDaoUtil.addNewShow(connection, tvShowEnglish, showType);

            tvShowEnglish.setShowId(showId);
            russianTranslation.setShowId(showId);

            ShowDaoUtil.addShowCountries(connection, tvShowEnglish);
            ShowDaoUtil.addShowGenres(connection, tvShowEnglish);

            ShowDaoUtil.addNewShowTranslation(connection, tvShowEnglish);
            ShowDaoUtil.addNewShowTranslation(connection, russianTranslation);

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getTvShowConfig();
            String addTvShowQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_NEW_TV_SHOW);

            preparedStatement = connection.prepareStatement(addTvShowQuery);

            preparedStatement.setInt(1, tvShowEnglish.getShowId());
            preparedStatement.setInt(2, tvShowEnglish.getSeasonsNum());
            preparedStatement.setInt(3, tvShowEnglish.getEpisodesNum());
            preparedStatement.setString(4, tvShowEnglish.getChannel().toString());
            preparedStatement.setString(5, tvShowEnglish.getFormattedShowStatus());

            preparedStatement.executeUpdate();

            if (tvShowEnglish.getShowStatus() == TvShow.ShowStatus.FINISHED) {
                String addFinishedYearQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_TV_SHOW_FINISHED_YEAR);

                preparedStatement = connection.prepareStatement(addFinishedYearQuery);

                preparedStatement.setInt(1, tvShowEnglish.getYear());
                preparedStatement.setInt(2, showId);

                preparedStatement.executeUpdate();
            }
            connection.commit();
            return showId;
        } catch (SQLException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Error occurred while adding new tv-show to data base ", e);
        } catch (TransactionInterruptionException e) {
            connectionPool.rollbackConnection(connection);
            throw new DAOException("Transaction was interrupted while adding new tv-show", e);
        } catch (EntityNotUniqueException e) {
            connectionPool.rollbackConnection(connection);
            throw new EntityNotUniqueException("Such tv-show is already in data base", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private String formTvShowListQuery(String orderType) {

        Configuration queryConfig = QueryConfigurationFactory.getInstance().getTvShowConfig();
        String takeTvShowListQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_TVSHOW_LIST_QUERY);

        return String.format(takeTvShowListQuery, orderType);
    }

    private TvShow setTvShowInfo(int tvShowId, ResultSet resultSet) throws SQLException {
        String title = resultSet.getString(1);
        Date premiereDate = resultSet.getDate(2);
        Time runtime = resultSet.getTime(3);
        int seasons = resultSet.getInt(4);
        int episodesNumber = resultSet.getInt(5);
        String tvChannel = resultSet.getString(6);
        String showStatus = resultSet.getString(7);
        Date finishedYearDate = resultSet.getDate(8);

        String synopsis = resultSet.getString(9);
        String poster = resultSet.getString(10);

        int premiereYear = TypeFormatUtil.getYearFromDate(premiereDate);
        int finishedYear = 0;
        if (finishedYearDate != null) {
            finishedYear = TypeFormatUtil.getYearFromDate(finishedYearDate);
        }

        TvShow tvShow = new TvShowBuilder()
                .addId(tvShowId)
                .addTitle(title)
                .addYear(premiereYear)
                .addPremiereDate(premiereDate)
                .addRuntime(runtime)
                .addSeasonsNum(seasons)
                .addEpisodesNum(episodesNumber)
                .addChannel(tvChannel)
                .addShowStatus(showStatus)
                .addFinishedYear(finishedYear)
                .addSynopsis(synopsis)
                .addPoster(poster)
                .create();

        return tvShow;

    }
}
