package by.tr.web.listener;

import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class UserStatusUpdater implements Runnable {
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String USER_STATUS_UPDATE_QUERY = "{call auto_set_user_status()}";

    private final static Logger logger = Logger.getLogger(UserStatusUpdateManager.class);

    @Override
    public void run() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = connectionPool.takeConnection();

            callableStatement = connection.prepareCall(USER_STATUS_UPDATE_QUERY);

            callableStatement.executeUpdate();


        } catch (SQLException e) {
            logger.error("Sql error while calling user status update statement", e);
        } finally {
            connectionPool.closeConnection(connection, callableStatement);
        }
    }
}
