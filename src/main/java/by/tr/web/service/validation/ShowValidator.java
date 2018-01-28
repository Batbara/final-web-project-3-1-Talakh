package by.tr.web.service.validation;

import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.show.InvalidOrderTypeException;
import by.tr.web.service.factory.ValidatorFactory;

public interface ShowValidator {

    boolean checkOrderType(String orderType) throws InvalidOrderTypeException;

    boolean isOrderDescending(String orderType) throws InvalidOrderTypeException;

    boolean validateTakeListParameters(int startRecordNum, int moviesNumber,
                                       String orderType, String lang) throws ServiceException;

    default boolean validateShowIdParameters(String showId, String lang) throws ServiceException {

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();
        if (!dataTypeValidator.checkLanguage(lang) && !dataTypeValidator.checkForPositive(showId)) {
            throw new ServiceException("Invalid input parameters");
        }
        return true;
    }
}
