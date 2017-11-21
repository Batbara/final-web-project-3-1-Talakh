package by.tr.web.dao.impl.connection_pool;

import by.tr.web.dao.impl.DBParameter;
import by.tr.web.dao.impl.DBResourceManager;
import by.tr.web.exception.ExceptionMessage;
import by.tr.web.exception.dao.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private static volatile ConnectionPool instance;

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    private ConnectionPool() throws ConnectionPoolException {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

        try {
            this.poolSize = Integer.parseInt(dbResourceManager
                    .getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = 5;
        }
        initPoolData();
    }

    private void initPoolData() throws ConnectionPoolException {

        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(connection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(ExceptionMessage.SQL_ERROR, e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException(ExceptionMessage.MISSING_DB_DRIVER, e);
        }
    }

    public void dispose() throws ConnectionPoolException {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() throws ConnectionPoolException {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            throw new ConnectionPoolException(ExceptionMessage.CLOSING_CONNECTION_ERROR, e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(ExceptionMessage.CONNECTING_TO_DATA_SOURCE_ERROR, e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, Statement st, ResultSet rs) throws ConnectionPoolException {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(ExceptionMessage.CLOSING_RESULT_SET_ERROR, e);
        }

        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(ExceptionMessage.CLOSING_STATEMENT_ERROR, e);
        }
        closeConnection(connection);
    }

    public void closeConnection(Connection connection) throws ConnectionPoolException {
        try {
            if (connection.isClosed()) {
                throw new ConnectionPoolException(ExceptionMessage.CONNECTION_ALREADY_CLOSED);
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(ExceptionMessage.CONNECTION_ACCESS_ERROR, e);
        }

        if (!givenAwayConQueue.remove(connection)) {
            throw new ConnectionPoolException(ExceptionMessage.GIVEN_AWAY_CONNECTION_QUEUE_ERROR);
        }

        if (!connectionQueue.offer(connection)) {
            throw new ConnectionPoolException(ExceptionMessage.CONNECTION_QUEUE_ERROR);
        }
    }

    public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {
        try {
            st.close();
        } catch (SQLException e) {
            throw new ConnectionPoolException(ExceptionMessage.CLOSING_STATEMENT_ERROR, e);
        }
        closeConnection(con);
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
        }
    }


}
