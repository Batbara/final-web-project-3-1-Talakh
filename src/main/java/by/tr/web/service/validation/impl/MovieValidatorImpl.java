package by.tr.web.service.validation.impl;

import by.tr.web.domain.Movie;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.common.ValidationException;
import by.tr.web.exception.service.show.InvalidOrderTypeException;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.DataTypeValidator;
import by.tr.web.service.validation.MovieValidator;
import by.tr.web.service.validation.ShowValidator;

public class MovieValidatorImpl implements ShowValidator, MovieValidator {
    @Override
    public boolean checkOrderType(String inputOrderType) throws InvalidOrderTypeException {
        if (inputOrderType == null) {
            throw new InvalidOrderTypeException("Order type is empty");
        }
        for (Movie.MovieOrderType movieOrderType : Movie.MovieOrderType.values()) {
            String orderTypeName = movieOrderType.name();
            if (orderTypeName.equalsIgnoreCase(inputOrderType)) {
                return true;
            }
        }
        throw new InvalidOrderTypeException("Invalid order type parameter");
    }

    @Override
    public boolean isOrderDescending(String orderType) throws InvalidOrderTypeException {
        checkOrderType(orderType);
        String ratingOrderType = Movie.MovieOrderType.RATING.toString();
        return orderType.equalsIgnoreCase(ratingOrderType);
    }

    @Override
    public boolean validateTakeListParameters(int startRecordNum, int moviesNumber, String orderType, String lang)
            throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        ShowValidator movieValidator = validatorFactory.getMovieValidator();
        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();

        movieValidator.checkOrderType(orderType);

        boolean isInputValid = dataTypeValidator.validateInputParameters(startRecordNum, moviesNumber, lang);
        if (!isInputValid) {
            throw new ServiceException("Unexpected error while validating ordered movie list parameters");
        }
        return true;
    }

    @Override
    public boolean validateMpaaRating(String mpaaRatingInput) throws ValidationException {
        if (mpaaRatingInput == null) {
            throw new ValidationException("Mpaa rating is empty");
        }
        for (Movie.MPAARating mpaaRating : Movie.MPAARating.values()) {
            String ratingName = mpaaRating.name();
            if (ratingName.equalsIgnoreCase(mpaaRatingInput)) {
                return true;
            }
        }
        throw new ValidationException("Invalid mpaa rating parameter");
    }
}
