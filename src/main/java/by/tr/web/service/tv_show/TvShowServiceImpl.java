package by.tr.web.service.tv_show;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.dao.DAOFactory;
import by.tr.web.dao.exception.CounterDAOException;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.exception.EntityNotUniqueException;
import by.tr.web.dao.tv_show.TvShowDAO;
import by.tr.web.domain.Show;
import by.tr.web.domain.TvShow;
import by.tr.web.service.CountingServiceException;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.input_validator.ValidatorFactory;
import by.tr.web.service.show.ShowAlreadyExistsException;
import by.tr.web.service.show.ShowValidator;

import java.util.List;

public class TvShowServiceImpl implements TvShowService {
    @Override
    public List<TvShow> takeOrderedTvShowList(int startRecordNum, int tvShowsNumber, String orderType, String lang) throws ServiceException {
        ShowValidator validator = ValidatorFactory.getInstance().getTvShowValidator();
        validator.checkOrderType(orderType);

        DataTypeValidator dataTypeValidator = ValidatorFactory.getInstance().getDataTypeValidator();
        dataTypeValidator.checkLanguage(lang);

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
        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();

        dataTypeValidator.checkLanguage(lang);
        dataTypeValidator.checkForPositive(id);

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

    @Override
    public int addTvShow(TvShow tvShow, Show russianTranslation) throws ServiceException {
        TvShowDAO movieDAO = DAOFactory.getInstance().getTvShowDAO();
        try {
            return movieDAO.addTvShow(tvShow, russianTranslation);
        } catch (EntityNotUniqueException e) {
            throw new ShowAlreadyExistsException("Trying to add tv-show "+tvShow.toString()+" already existing in data base", e);
        }
        catch (DAOException e) {
            throw new ServiceException("Cannot add tv-show to data base", e);
        }
    }

}
