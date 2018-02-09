package by.tr.web.service.movie;

import by.tr.web.domain.Movie;
import by.tr.web.service.InvalidOrderTypeException;
import by.tr.web.service.input_validator.ValidationException;
import by.tr.web.service.show.ShowValidator;

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
        throw new InvalidOrderTypeException("Invalid order type constant");
    }

    @Override
    public boolean isOrderDescending(String orderType) throws InvalidOrderTypeException {
        checkOrderType(orderType);
        String ratingOrderType = Movie.MovieOrderType.RATING.toString();
        return orderType.equalsIgnoreCase(ratingOrderType);
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
        throw new ValidationException("Invalid mpaa rating constant");
    }
}
