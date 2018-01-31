package by.tr.web.service.factory;

import by.tr.web.service.validation.DataTypeValidator;
import by.tr.web.service.validation.ShowValidator;
import by.tr.web.service.validation.UserReviewValidator;
import by.tr.web.service.validation.UserValidator;
import by.tr.web.service.validation.impl.DataTypeValidatorImpl;
import by.tr.web.service.validation.impl.MovieValidatorImpl;
import by.tr.web.service.validation.impl.TvShowValidatorImpl;
import by.tr.web.service.validation.impl.UserReviewValidatorImpl;
import by.tr.web.service.validation.impl.UserValidatorImpl;

public class ValidatorFactory {
    private static ValidatorFactory instance = new ValidatorFactory();

    private UserValidator userValidator = new UserValidatorImpl();

    private DataTypeValidator dataTypeValidator = new DataTypeValidatorImpl();
    private UserReviewValidator userReviewValidator = new UserReviewValidatorImpl();

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

    public UserReviewValidator getUserReviewValidator() {
        return userReviewValidator;
    }
}
