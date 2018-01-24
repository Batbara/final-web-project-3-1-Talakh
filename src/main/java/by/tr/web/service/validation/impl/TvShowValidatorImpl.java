package by.tr.web.service.validation.impl;

import by.tr.web.domain.TvShow;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.show.InvalidOrderTypeException;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.DataTypeValidator;
import by.tr.web.service.validation.ShowValidator;

public class TvShowValidatorImpl implements ShowValidator {
    @Override
    public boolean checkOrderType(String inputOrderType) throws InvalidOrderTypeException {
        if (inputOrderType == null) {
            throw new InvalidOrderTypeException("Order type is empty");
        }
        for (TvShow.TvShowOrderType orderType : TvShow.TvShowOrderType.values()) {
            String orderTypeName = orderType.name();
            if (orderTypeName.equalsIgnoreCase(inputOrderType)) {
                return true;
            }
        }
        throw new InvalidOrderTypeException("Invalid order type parameter");
    }

    @Override
    public boolean isOrderDescending(String orderType) throws InvalidOrderTypeException {
        checkOrderType(orderType);
        String ratingOrderType = TvShow.TvShowOrderType.RATING.toString();
        return orderType.equalsIgnoreCase(ratingOrderType);
    }

    @Override
    public boolean validateTakeListParameters(int startRecordNum, int moviesNumber, String orderType, String lang) throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        ShowValidator tvShowValidator = validatorFactory.getTvShowValidator();
        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();

        tvShowValidator.checkOrderType(orderType);

        boolean isInputValid = dataTypeValidator.validateInputParameters(startRecordNum, moviesNumber, lang);
        if (!isInputValid) {
            throw new ServiceException("Unexpected error while validating ordered movie list parameters");
        }
        return true;
    }
}
