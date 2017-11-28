package by.tr.web.dao.factory;

import by.tr.web.dao.AppDAO;
import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.AppDAOImpl;
import by.tr.web.dao.impl.SQLUserDAOImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private UserDAO userDAO = new SQLUserDAOImpl();
    private AppDAO appDAO = new AppDAOImpl();
    private DAOFactory() {

    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public AppDAO getAppDAO() {
        return appDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
