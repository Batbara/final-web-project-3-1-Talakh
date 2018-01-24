package by.tr.web.dao.factory;

import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.ShowDAO;
import by.tr.web.dao.TvShowDAO;
import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.MovieDAOSqlImpl;
import by.tr.web.dao.impl.ShowDAOSqlImpl;
import by.tr.web.dao.impl.TvShowDAOSqlImpl;
import by.tr.web.dao.impl.UserDAOSqlImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private UserDAO userDAO = new UserDAOSqlImpl();
    private MovieDAO movieDAO = new MovieDAOSqlImpl();
    private ShowDAO showDAO = new ShowDAOSqlImpl();
    private TvShowDAO tvShowDAO = new TvShowDAOSqlImpl();

    private DAOFactory() {

    }

    public TvShowDAO getTvShowDAO() {
        return tvShowDAO;
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
