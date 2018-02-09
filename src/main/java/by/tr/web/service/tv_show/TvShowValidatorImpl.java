package by.tr.web.service.tv_show;

import by.tr.web.domain.TvShow;
import by.tr.web.service.InvalidOrderTypeException;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.input_validator.ValidationException;
import by.tr.web.service.show.ShowValidator;

public class TvShowValidatorImpl implements ShowValidator, TvShowValidator {
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
        throw new InvalidOrderTypeException("Invalid order type constant");
    }

    @Override
    public boolean isOrderDescending(String orderType) throws InvalidOrderTypeException {
        checkOrderType(orderType);
        String ratingOrderType = TvShow.TvShowOrderType.RATING.toString();
        return orderType.equalsIgnoreCase(ratingOrderType);
    }

    @Override
    public boolean validateShowStatus(String showStatusInput) throws ValidationException {
        if (showStatusInput == null) {
            throw new RequestParameterNotFound("Show status is null");
        }
        for (TvShow.ShowStatus status : TvShow.ShowStatus.values()) {
            String orderTypeName = status.name();
            if (orderTypeName.equalsIgnoreCase(showStatusInput)) {
                return true;
            }
        }
        throw new InvalidTvShowStatusException(showStatusInput + " is not valid tv-show status");
    }
}
