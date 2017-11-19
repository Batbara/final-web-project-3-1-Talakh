package by.tr.web.dao.factory;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.SQLUserDAOImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private UserDAO userDAO = new SQLUserDAOImpl();
    private DAOFactory() {}

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
