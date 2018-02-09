package by.tr.web.service.input_validator;

import by.tr.web.service.movie.MovieValidatorImpl;
import by.tr.web.service.show.ShowValidator;
import by.tr.web.service.show.review.ReviewValidator;
import by.tr.web.service.show.review.ReviewValidatorImpl;
import by.tr.web.service.tv_show.TvShowValidatorImpl;
import by.tr.web.service.user.UserValidator;
import by.tr.web.service.user.UserValidatorImpl;

public class ValidatorFactory {
    private static ValidatorFactory instance = new ValidatorFactory();

    private UserValidator userValidator = new UserValidatorImpl();

    private DataTypeValidator dataTypeValidator = new DataTypeValidatorImpl();
    private ReviewValidator reviewValidator = new ReviewValidatorImpl();

    private ShowValidator movieValidator = new MovieValidatorImpl();
    private ShowValidator tvShowValidator = new TvShowValidatorImpl();

    private ValidatorFactory() {
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public static ValidatorFactory getInstance() {
        return instance;
    }

    public ShowValidator getMovieValidator() {
        return movieValidator;
    }

    public ShowValidator getTvShowValidator() {
        return tvShowValidator;
    }


    public DataTypeValidator getDataTypeValidator() {
        return dataTypeValidator;
    }

    public ReviewValidator getReviewValidator() {
        return reviewValidator;
    }
}
