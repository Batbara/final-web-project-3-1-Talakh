package by.tr.web.service.impl;

import by.tr.web.dao.ShowDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.ShowService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.UserReviewValidator;

public class ShowServiceImpl implements ShowService {

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


}
