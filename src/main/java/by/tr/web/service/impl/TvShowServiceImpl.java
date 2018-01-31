package by.tr.web.service.impl;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.dao.TvShowDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.TvShow;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.movie.CounterDAOException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.show.CountingServiceException;
import by.tr.web.service.TvShowService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.ShowValidator;

import java.util.List;

public class TvShowServiceImpl implements TvShowService {
    @Override
    public List<TvShow> takeOrderedTvShowList(int startRecordNum, int tvShowsNumber, String orderType, String lang) throws ServiceException {
        ShowValidator validator = ValidatorFactory.getInstance().getTvShowValidator();
        validator.validateTakeListParameters(startRecordNum, tvShowsNumber, orderType, lang);

        if (validator.isOrderDescending(orderType)) {
            orderType = orderType + FrontControllerParameter.DESCENDING_ORDER;
        }
        TvShowDAO tvShowDAO = DAOFactory.getInstance().getTvShowDAO();
        try {
            return tvShowDAO.takeSortedTvShowList(startRecordNum, tvShowsNumber, orderType, lang);
        } catch (DAOException e) {
            throw new ServiceException("Error while getting tv-shows list", e);
        }
    }

    @Override
    public TvShow takeTvShow(String id, String lang) throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        ShowValidator tvShowValidator = validatorFactory.getTvShowValidator();

        tvShowValidator.validateShowIdParameters(id, lang);
        int tvShowId = Integer.parseInt(id);

        TvShowDAO tvShowDAO = DAOFactory.getInstance().getTvShowDAO();
        try {
            return tvShowDAO.takeTvShow(tvShowId, lang);
        } catch (DAOException e) {
            throw new ServiceException("Error while taking tv-show", e);
        }
    }



    @Override
    public int countTvShow() throws ServiceException {
        TvShowDAO tvShowDAO = DAOFactory.getInstance().getTvShowDAO();
        try {
            return tvShowDAO.countTvShow();
        } catch (CounterDAOException e) {
            throw new CountingServiceException("Can't get number of tv-shows", e);
        } catch (DAOException e) {
            throw new ServiceException("Error while counting tv-shows", e);
        }
    }

}
