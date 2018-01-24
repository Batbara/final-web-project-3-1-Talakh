package by.tr.web.service.impl;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.Movie;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.movie.CounterDAOException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.show.CountingServiceException;
import by.tr.web.service.MovieService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.ShowValidator;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    @Override
    public List<Movie> takeOrderedMovieList(int startRecordNum, int moviesNumber, String orderType, String lang) throws ServiceException {

        ShowValidator validator = ValidatorFactory.getInstance().getMovieValidator();
        validator.validateTakeListParameters(startRecordNum, moviesNumber, orderType, lang);

        if (validator.isOrderDescending(orderType)) {
            orderType = orderType + FrontControllerParameter.DESCENDING_ORDER;
        }
        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.takeSortedMovieList(startRecordNum, moviesNumber, orderType, lang);
        } catch (DAOException e) {
            throw new ServiceException("Error while getting movies list", e);
        }
    }

    @Override
    public Movie takeMovie(int id, String lang) throws ServiceException {

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        ShowValidator movieValidator = validatorFactory.getMovieValidator();

        movieValidator.validateShowIdParameters(id, lang);

        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.takeMovie(id, lang);
        } catch (DAOException e) {
            throw new ServiceException("Error while taking movie", e);
        }
    }

    @Override
    public int countMovie() throws ServiceException {
        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.countMovie();
        } catch (CounterDAOException e) {
            throw new CountingServiceException("Can't get number of movies", e);
        } catch (DAOException e) {
            throw new ServiceException("Error while counting movies", e);
        }
    }
}
