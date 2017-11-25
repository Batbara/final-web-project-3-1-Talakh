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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionPool {
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private Pattern isNumberPattern = Pattern.compile("\\d+");

    private static volatile ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        if(instance.connectionQueue == null){
            return null;
        }
       return instance;
    }

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

        setPoolSize(dbResourceManager);
        try {
            initPoolData();
        } catch (ConnectionPoolException e) {
           //TODO: log this exception
        }
    }

    private void setPoolSize(DBResourceManager dbResourceManager) {

        String poolSize = dbResourceManager.getValue(DBParameter.DB_POOL_SIZE);
        Matcher matcher = isNumberPattern.matcher(poolSize);
        if (matcher.matches()) {
            this.poolSize = Integer.parseInt(poolSize);
        } else {
            this.poolSize = 5;
        }

    }

    public void initPoolData() throws ConnectionPoolException {

        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(connection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Error while getting connection from Driver Manager", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't find database driver class", e);
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
            throw new ConnectionPoolException("Closing connection error", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source", e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, Statement st, ResultSet rs) throws ConnectionPoolException {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Closing result set error", e);
        }

        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Closing statement error", e);
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
            throw new ConnectionPoolException("Can't access connection", e);
        }

        if (!givenAwayConQueue.remove(connection)) {
            throw new ConnectionPoolException("Error deleting connection from the given away connections pool");
        }

        if (!connectionQueue.offer(connection)) {
            throw new ConnectionPoolException("Error allocating connection in the pool");
        }
    }

    public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {
        try {
            st.close();
        } catch (SQLException e) {
            throw new ConnectionPoolException("Closing statement error", e);
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
