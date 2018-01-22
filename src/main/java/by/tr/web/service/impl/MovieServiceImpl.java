package by.tr.web.service.impl;

import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.Movie;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.movie.MovieCounterDAOException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.movie.CountingMoviesException;
import by.tr.web.exception.service.movie.InvalidOrderTypeException;
import by.tr.web.service.MovieService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.DataTypeValidator;
import by.tr.web.service.validation.MovieValidator;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    @Override
    public List<Movie> takeOrderedMovieList(int startRecordNum, int moviesNumber, String orderType, String lang) throws ServiceException {

        validate(startRecordNum, moviesNumber, orderType, lang);

        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.takeOrderedMovieList(startRecordNum, moviesNumber, orderType, lang);
        } catch (DAOException e) {
            throw new ServiceException("Error while getting movies list", e);
        }
    }

    @Override
    public Movie takeMovie(int id, String lang) throws ServiceException {
        validate(id, lang);
        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.takeMovie(id, lang);
        } catch (DAOException e) {
            throw new ServiceException("Error while taking movie", e);
        }
    }

    private void validate(int startRecordNum, int moviesNumber, String orderType, String lang) throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        MovieValidator movieValidator = validatorFactory.getMovieValidator();
        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();

        if (!movieValidator.checkOrderType(orderType)) {
            throw new InvalidOrderTypeException("Invalid order type parameter");
        }

        boolean isInputValid = dataTypeValidator.validateInputParameters(startRecordNum, moviesNumber, lang);
        if (!isInputValid) {
            throw new ServiceException("Unexpected error while validating ordered movie list parameters");
        }
    }

    private void validate(int id, String lang) throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();
        if (!dataTypeValidator.checkLanguage(lang) && id <= 0) {
            throw new ServiceException("Invalid input parameters");
        }
    }

    @Override
    public int countShow() throws ServiceException {
        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.countMovies();
        } catch (MovieCounterDAOException e) {
            throw new CountingMoviesException("Can't get number of movies", e);
        }
    }
}
