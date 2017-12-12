package by.tr.web.dao.impl.connection_pool;

import by.tr.web.exception.dao.ConnectionPoolException;

public class ConnectionPoolDAOImpl implements ConnectionPoolDAO {
    @Override
    public void initConnectionPool() throws ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            throw new ConnectionPoolException("Failed to init connection pool", e);
        }
    }
}
