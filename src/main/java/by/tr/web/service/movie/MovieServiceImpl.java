package by.tr.web.service.movie;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.dao.DAOFactory;
import by.tr.web.dao.exception.CounterDAOException;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.exception.EntityNotUniqueException;
import by.tr.web.dao.movie.MovieDAO;
import by.tr.web.domain.Movie;
import by.tr.web.domain.Show;
import by.tr.web.service.CountingServiceException;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.input_validator.ValidatorFactory;
import by.tr.web.service.show.ShowAlreadyExistsException;
import by.tr.web.service.show.ShowValidator;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    @Override
    public List<Movie> takeOrderedMovieList(int start, int moviesNumber, String orderType, String lang)
            throws ServiceException {

        ShowValidator validator = ValidatorFactory.getInstance().getMovieValidator();
        validator.checkOrderType(orderType);

        DataTypeValidator dataTypeValidator = ValidatorFactory.getInstance().getDataTypeValidator();
        dataTypeValidator.checkLanguage(lang);

        if (validator.isOrderDescending(orderType)) {
            orderType = orderType + FrontControllerParameter.DESCENDING_ORDER;
        }
        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.takeSortedMovieList(start, moviesNumber, orderType, lang);
        } catch (DAOException e) {
            throw new ServiceException("Error while getting movies list", e);
        }
    }

    @Override
    public Movie takeMovie(String id, String lang) throws ServiceException {

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();

        dataTypeValidator.checkLanguage(lang);
        dataTypeValidator.checkForPositive(id);

        int movieId = Integer.parseInt(id);

        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.takeMovie(movieId, lang);
        } catch (DAOException e) {
            throw new ServiceException("Error while taking movie id="+movieId, e);
        }
    }

    @Override
    public int addMovie(Movie movie, Show russianTranslation) throws ServiceException {
        MovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            return movieDAO.addMovie(movie, russianTranslation);
        } catch (EntityNotUniqueException e) {
            throw new ShowAlreadyExistsException("Trying to add movie "+movie.toString()+" already existing in data base", e);
        }
        catch (DAOException e) {
            throw new ServiceException("Cannot add movie to data base", e);
        }
    }

    @Override
    public int countMovies() throws ServiceException {
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
