package by.tr.web.dao.factory;

import by.tr.web.dao.impl.connection_pool.ConnectionPoolDAO;
import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPoolDAOImpl;
import by.tr.web.dao.impl.SQLUserDAOImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private UserDAO userDAO = new SQLUserDAOImpl();
    private ConnectionPoolDAO connectionPoolDAO = new ConnectionPoolDAOImpl();
    private DAOFactory() {

    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public ConnectionPoolDAO getConnectionPoolDAO() {
        return connectionPoolDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
