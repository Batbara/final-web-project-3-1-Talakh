package by.tr.web.dao.impl;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.ShowDAO;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.common.TransactionInterruptionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowDAOSqlImpl implements ShowDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addReview(UserReview userReview) throws DAOException {

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

    private void addRate(Connection connection, UserReview userReview) throws TransactionInterruptionException {
        PreparedStatement preparedStatement = null;
        try {
            int userId = userReview.getUser().getId();
            int showId = userReview.getShowId();
            int userRate = userReview.getUserRate();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String checkReviewPresenceQuery = queryConfig.getSqlQuery(SqlQueryName.ADD_USER_RATE);

            preparedStatement = connection.prepareStatement(checkReviewPresenceQuery);

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
            String checkReviewPresenceQuery = queryConfig.getSqlQuery(SqlQueryName.UPDATE_USER_RATE);

            preparedStatement = connection.prepareStatement(checkReviewPresenceQuery);
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

    private boolean isReviewPresentInDB(Connection connection, UserReview userReview) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            int userId = userReview.getUser().getId();
            int showId = userReview.getShowId();

            Configuration queryConfig = ConfigurationFactory.getInstance().getShowQueryConfig();
            String checkReviewPresenceQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_USER_REIVEW_PRESENCE);

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
