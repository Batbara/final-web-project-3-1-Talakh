package by.tr.web.service.impl;

import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.dao.ShowDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import by.tr.web.domain.builder.UserReviewBuilder;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.ShowService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.DataTypeValidator;
import by.tr.web.service.validation.UserReviewValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowServiceImpl implements ShowService {


    @Override
    public List<UserReview> takeShowReviewList(int startNumber, int reviewsNumber, int showId)
            throws ServiceException {

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.takeShowReviewList(startNumber, reviewsNumber, showId);
        } catch (DAOException e) {
            throw new ServiceException("Cannot take reviews list from data base", e);
        }
    }

    @Override
    public List<UserReview> takeReviewsOnModeration(int startReview, int reviewsNumber) throws ServiceException {
        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.takeReviewsOnModeration(startReview, reviewsNumber);
        } catch (DAOException e) {
            throw new ServiceException("Cannot take reviews on moderation from data base", e);
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
            throw new ServiceException("Cannot take country list", e);
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
            throw new ServiceException("Cannot take genre list", e);
        }
    }

    @Override
    public void addUserRate(UserReview userReviewRate) throws ServiceException {

        UserReviewValidator validator = ValidatorFactory.getInstance().getUserReviewValidator();
        validator.checkUserRate(userReviewRate);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            showDAO.rateShow(userReviewRate);
        } catch (DAOException e) {
            throw new ServiceException("Cannot set user rate for show", e);
        }

    }

    @Override
    public void addUserReview(UserReview userReview) throws ServiceException {
        UserReviewValidator validator = ValidatorFactory.getInstance().getUserReviewValidator();
        validator.checkUserReviewContent(userReview);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            showDAO.addReview(userReview);
        } catch (DAOException e) {
            throw new ServiceException("Cannot add user review for show", e);
        }
    }

    @Override
    public void postUserReview(UserReview userReview) throws ServiceException {
        UserReviewValidator validator = ValidatorFactory.getInstance().getUserReviewValidator();
        validator.checkShowInReview(userReview);
        validator.checkUserInReview(userReview);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        int showId = userReview.getShowId();
        int userId = userReview.getUser().getId();
        try {
            showDAO.postReview(userId, showId);
        } catch (DAOException e) {
            throw new ServiceException("Cannot post user review", e);
        }
    }

    @Override
    public void deleteUserReview(UserReview userReview) throws ServiceException {
        UserReviewValidator validator = ValidatorFactory.getInstance().getUserReviewValidator();
        validator.checkShowInReview(userReview);
        validator.checkUserInReview(userReview);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        int showId = userReview.getShowId();
        int userId = userReview.getUser().getId();
        try {
            showDAO.deleteReview(userId, showId);
        } catch (DAOException e) {
            throw new ServiceException("Cannot delete user review", e);
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
            throw new ServiceException("Cannot take show rating", e);
        }
    }

    @Override
    public String retrieveReviewTitle(HttpServletRequest request) throws ServiceException {
        String reviewTitle = request.getParameter(JspAttribute.REVIEW_TITLE);
        if (reviewTitle == null) {
            throw new ServiceException("Review title is empty");
        }
        return reviewTitle;
    }

    @Override
    public String retrieveReviewContent(HttpServletRequest request) throws ServiceException {
        String reviewTitle = request.getParameter(JspAttribute.REVIEW_CONTENT);
        if (reviewTitle == null) {
            throw new ServiceException("Review content is empty");
        }
        return reviewTitle;
    }

    @Override
    public int retrieveUserRate(HttpServletRequest request) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        String parameter = request.getParameter(JspAttribute.USER_RATE);

        validator.checkForPositive(parameter);
        return Integer.parseInt(parameter);

    }

    @Override
    public int retrieveShowId(HttpServletRequest request) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        String parameter = request.getParameter(JspAttribute.SHOW_ID);

        validator.checkForPositive(parameter);
        return Integer.parseInt(parameter);
    }

    @Override
    public UserReview retrieveUserReview(HttpServletRequest request) throws ServiceException {

        int showId = retrieveShowId(request);

        String userIdString = request.getParameter(JspAttribute.USER_ID);

        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkForPositive(userIdString);
        int userId = Integer.parseInt(userIdString);

        UserReview userReview = new UserReviewBuilder()
                .addShowId(showId)
                .addUser(new User(userId))
                .create();

        return userReview;

    }


    @Override
    public int countShowReviews(String showId) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkForPositive(showId);

        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.countShowReviews(Integer.parseInt(showId));
        } catch (DAOException e) {
            throw new ServiceException("Cannot count show reviews", e);
        }
    }

    @Override
    public int countReviewsOnModeration() throws ServiceException {
        ShowDAO showDAO = DAOFactory.getInstance().getShowDAO();
        try {
            return showDAO.countReviewsOnModeration();
        } catch (DAOException e) {
            throw new ServiceException("Cannot count reviews on moderation", e);
        }
    }


}
