package by.tr.web.dao.impl.connection_pool;

import by.tr.web.dao.impl.DBParameter;
import by.tr.web.dao.impl.DBResourceManager;
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
        //  Locale.setDefault(Locale.ENGLISH);

        try {
            Class.forName(driverName);
            givenAwayConQueue = new
                    ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(connection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException(
                    "Can't find database driver class", e);
        }

    }

    public void dispose() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            // logger.log(Level.ERROR, "Error closing the connection.", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(
                    "Error connecting to the data source.", e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, Statement st, ResultSet rs) throws ConnectionPoolException {
        try {
            rs.close();
        } catch (SQLException e) {
            // logger.log(Level.ERROR, "ResultSet isn't closed.");
        }

        try {
            st.close();
        } catch (SQLException e) {
            // logger.log(Level.ERROR, "Statement isn't closed.");
        }
        closeConnection(connection);
}
    public void closeConnection(Connection connection) throws ConnectionPoolException {
        try {
            if (connection.isClosed()) {

                throw new ConnectionPoolException("Trying to close closed connection");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Error with reading connection",e);
        }


        if (!givenAwayConQueue.remove(connection)) {
            throw new ConnectionPoolException(
                    "Error deleting connection from the given away connections pool.");
        }

        if (!connectionQueue.offer(connection)) {
            throw new ConnectionPoolException(
                    "Error allocating connection in the pool.");
        }
    }
    public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {
        try {
            st.close();
        } catch (SQLException e) {
            // logger.log(Level.ERROR, "Statement isn't closed.");
        }
        closeConnection(con);
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue)
            throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
        }
    }


}
