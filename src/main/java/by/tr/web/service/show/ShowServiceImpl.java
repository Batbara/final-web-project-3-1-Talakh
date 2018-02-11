package by.tr.web.service.show;

import by.tr.web.dao.DAOFactory;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.show.ShowDAO;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Review;
import by.tr.web.domain.Show;
import by.tr.web.service.CountingServiceException;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.input_validator.ValidatorFactory;
import by.tr.web.service.show.review.ReviewException;

import java.util.List;

public class ShowServiceImpl implements ShowService {


    @Override
    public List<Show> takeSortedShowList(int start, int showsNumber, String lang) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkLanguage(lang);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.takeSortedShowList(start, showsNumber, lang);
        } catch (DAOException e) {
            throw new ReviewException("Cannot take list of all shows from data base", e);
        }
    }

    @Override
    public void deleteShow(String showId) throws ServiceException {
        DataTypeValidator typeValidator = ValidatorFactory.getInstance().getDataTypeValidator();
        typeValidator.checkForPositive(showId);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            showDAO.deleteShow(Integer.parseInt(showId));
        } catch (DAOException e) {
            throw new ServiceException("Cannot delete show from data base", e);
        }
    }

    @Override
    public int countAllShows() throws ServiceException {
        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.countAllShows();
        } catch (DAOException e) {
            throw new CountingServiceException("Cannot count shows in data base", e);
        }
    }

    @Override
    public List<Review> takeShowReviewList(int startNumber, int reviewsNumber, int showId)
            throws ServiceException {

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.takeShowReviewList(startNumber, reviewsNumber, showId);
        } catch (DAOException e) {
            throw new ReviewException("Cannot take reviews list for show id=" + showId, e);
        }
    }


    @Override
    public List<Country> takeCountryList(String lang) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkLanguage(lang);
        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();

        try {
            return showDAO.takeCountryList(lang);
        } catch (DAOException e) {
            throw new ServiceException("Cannot take country list on language: " + lang, e);
        }
    }

    @Override
    public List<Genre> takeGenreList(String lang) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkLanguage(lang);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();

        try {
            return showDAO.takeGenreList(lang);
        } catch (DAOException e) {
            throw new ServiceException("Cannot take genre list on language: " + lang, e);
        }
    }


    @Override
    public double takeShowRating(String showId) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkForPositive(showId);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.takeShowRating(Integer.parseInt(showId));
        } catch (DAOException e) {
            throw new ServiceException("Cannot take show[id=" + showId + "] rating", e);
        }
    }

    @Override
    public int countShowReviews(String showId) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkForPositive(showId);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.countShowReviews(Integer.parseInt(showId));
        } catch (DAOException e) {
            throw new CountingServiceException("Cannot count show reviews", e);
        }
    }

}
