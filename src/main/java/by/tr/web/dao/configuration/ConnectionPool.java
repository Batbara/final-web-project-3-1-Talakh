package by.tr.web.dao.configuration;

import by.tr.web.dao.constant.DBParameter;
import by.tr.web.dao.exception.ConnectionPoolException;
import by.tr.web.dao.exception.TransactionError;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ConnectionPool {
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

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

        return instance;
    }

    private ConnectionPool() {
        ResourceBundle dbBundle =
                ResourceBundle.getBundle(DBParameter.BASE_NAME);

        this.driverName = dbBundle.getString(DBParameter.DB_DRIVER);
        this.url = dbBundle.getString(DBParameter.DB_URL);
        this.user = dbBundle.getString(DBParameter.DB_USER);
        this.password = dbBundle.getString(DBParameter.DB_PASSWORD);

        setPoolSize(dbBundle);
    }

    private void setPoolSize(ResourceBundle dbBundle) {

        String poolSize = dbBundle.getString(DBParameter.DB_POOL_SIZE);
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
            logger.error("Error while getting connection from Driver Manager", e);
            throw new ConnectionPoolException("Error while getting connection from Driver Manager", e);
        } catch (ClassNotFoundException e) {
            logger.error("Can't find database driver class", e);
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
            logger.error("Closing connection error", e);
            throw new ConnectionPoolException("Closing connection error", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            if(connection.isClosed()){
                connection = reopenConnection();
            }
            givenAwayConQueue.add(connection);
            return connection;
        } catch (InterruptedException e) {
            String message = "Error connecting to the data source";
            logger.error(message, e);
            throw new ConnectionPoolException(message, e);
        } catch (SQLException e) {
            String message = "Error while reopening connection";
            logger.error(message, e);
            throw new ConnectionPoolException(message, e);
        }

    }

    public void rollbackConnection(Connection connection) throws TransactionError{
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            String message = "Error while rolling back transaction";
            logger.error(message, e);
            throw new TransactionError(message, e);
        }
    }

    public void closeConnection(Connection connection, Statement st, ResultSet rs) throws ConnectionPoolException {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            closeConnection(connection);
            logger.error("Closing result set error", e);
            throw new ConnectionPoolException("Closing result set error", e);
        }

        closeStatement(st);

        closeConnection(connection);
    }

    public void closeResources(Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
    }

    public void closeResources(Statement st) {
        closeStatement(st);
    }


    public void closeConnection(Connection connection) throws ConnectionPoolException {
        if (!givenAwayConQueue.remove(connection)) {
            logger.log(Level.ERROR, "Error deleting connection from the given away connections pool");
            throw new ConnectionPoolException("Error deleting connection from the given away connections pool");
        }
        try {
            if (connection.isClosed()) {
                connection = reopenConnection();
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }

        } catch (SQLException e) {
            logger.error("Can't access connection", e);
            throw new ConnectionPoolException("Can't access connection", e);
        }

        if (!connectionQueue.offer(connection)) {
            throw new ConnectionPoolException("Error allocating connection in the pool");
        }
    }

    public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {
        try {
            st.close();
        } catch (SQLException e) {
            logger.error("Closing statement error", e);
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

    private Connection reopenConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    private void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            logger.error("Closing statement error", e);
            throw new ConnectionPoolException("Closing statement error", e);
        }
    }
    private void closeResultSet(ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("Closing result set error", e);
            throw new ConnectionPoolException("Closing result set error", e);
        }
    }

}
