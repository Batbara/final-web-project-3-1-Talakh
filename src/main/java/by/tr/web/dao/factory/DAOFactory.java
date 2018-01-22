package by.tr.web.dao.factory;

import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.ShowDAO;
import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.movie.MovieDAOImpl;
import by.tr.web.dao.impl.show.ShowDAOImpl;
import by.tr.web.dao.impl.user.SQLUserDAOImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private UserDAO userDAO = new SQLUserDAOImpl();
    private MovieDAO movieDAO = new MovieDAOImpl();
    private ShowDAO showDAO = new ShowDAOImpl();
    private DAOFactory() {

    }

    public ShowDAO getShowDAO() {
        return showDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public MovieDAO getMovieDAO() {
        return movieDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
