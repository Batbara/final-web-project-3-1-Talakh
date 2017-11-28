package by.tr.web.dao.impl;

import by.tr.web.dao.AppDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.exception.dao.ApplicationDAOException;
import by.tr.web.exception.dao.ConnectionPoolException;

public class AppDAOImpl implements AppDAO {
    @Override
    public void initConnectionPool() throws ApplicationDAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            throw new ApplicationDAOException("Failed to init connection pool", e);
        }
    }
}
