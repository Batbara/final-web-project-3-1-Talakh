package by.tr.web.dao;

import by.tr.web.dao.movie.MovieDAO;
import by.tr.web.dao.movie.MovieDAOSqlImpl;
import by.tr.web.dao.show.ShowDAO;
import by.tr.web.dao.show.ShowDAOSqlImpl;
import by.tr.web.dao.show.review.ReviewDAO;
import by.tr.web.dao.show.review.ReviewDAOImpl;
import by.tr.web.dao.tv_show.TvShowDAO;
import by.tr.web.dao.tv_show.TvShowDAOSqlImpl;
import by.tr.web.dao.user.UserDAO;
import by.tr.web.dao.user.UserDAOSqlImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private UserDAO userDAO = new UserDAOSqlImpl();
    private MovieDAO movieDAO = new MovieDAOSqlImpl();
    private ShowDAO showDAO = new ShowDAOSqlImpl();
    private ReviewDAO reviewDAO = new ReviewDAOImpl();
    private TvShowDAO tvShowDAO = new TvShowDAOSqlImpl();

    private DAOFactory() {

    }

    public TvShowDAO getTvShowDAO() {
        return tvShowDAO;
    }

    public ShowDAO getShowDAO() {
        return showDAO;
    }

    public ReviewDAO getReviewDAO() {
        return reviewDAO;
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
