package by.tr.web.service.impl;

import by.tr.web.dao.impl.connection_pool.ConnectionPoolDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.exception.dao.ConnectionPoolException;
import by.tr.web.exception.service.user.ServletServiceException;
import by.tr.web.service.ServletService;

public class ServletServiceImpl implements ServletService {
    @Override
    public void initResources() throws ServletServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        ConnectionPoolDAO connectionPoolDAO = daoFactory.getConnectionPoolDAO();
        try {
            connectionPoolDAO.initConnectionPool();
        } catch (ConnectionPoolException e) {
            throw new ServletServiceException("Failed to init connection pool",e);
        }
    }
}
