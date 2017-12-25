package by.tr.web.service.impl;

import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.Movie;
import by.tr.web.exception.dao.movie.MovieCounterDAOException;
import by.tr.web.exception.dao.movie.MovieDAOException;
import by.tr.web.exception.service.movie.CountingMoviesException;
import by.tr.web.exception.service.movie.MovieServiceException;
import by.tr.web.service.MovieService;
import org.apache.log4j.Logger;

import java.util.List;

public class MovieServiceImpl implements MovieService {
    private static final Logger logger = Logger.getLogger(MovieServiceImpl.class);

    @Override
    public List<Movie> takeOrderedMovieList(int startID, int moviesNumber, String orderType, String lang) throws MovieServiceException {
        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.takeOrderedMovieList(startID, moviesNumber, orderType, lang);
        } catch (MovieDAOException e) {
            throw new MovieServiceException("Error while getting movies list", e);
        }
    }

    @Override
    public int countMovies() throws MovieServiceException {
        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.countMovies();
        } catch (MovieCounterDAOException e) {
            logger.error("Can't get number of movies", e);
            throw new CountingMoviesException("Can't get number of movies", e);
        }
    }
}
