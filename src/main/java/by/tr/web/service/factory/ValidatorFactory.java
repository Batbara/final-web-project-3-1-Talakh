package by.tr.web.service.factory;

import by.tr.web.service.validation.DataTypeValidator;
import by.tr.web.service.validation.MovieValidator;
import by.tr.web.service.validation.UserValidator;
import by.tr.web.service.validation.impl.DataTypeValidatorImpl;
import by.tr.web.service.validation.impl.LoginValidatorImpl;
import by.tr.web.service.validation.impl.MovieValidatorImpl;
import by.tr.web.service.validation.impl.RegisterValidatorImpl;

public class ValidatorFactory {
    private static ValidatorFactory instance = new ValidatorFactory();
    private UserValidator registerValidator = new RegisterValidatorImpl();
    private UserValidator loginValidator = new LoginValidatorImpl();
    private MovieValidator movieValidator = new MovieValidatorImpl();
    private DataTypeValidator dataTypeValidator = new DataTypeValidatorImpl();

    private ValidatorFactory() {
    }

    public static ValidatorFactory getInstance() {
        return instance;
    }

    public UserValidator getRegisterValidator() {
        return registerValidator;
    }

    public UserValidator getLoginValidator() {
        return loginValidator;
    }

    public MovieValidator getMovieValidator() {
        return movieValidator;
    }

    public DataTypeValidator getDataTypeValidator() {
        return dataTypeValidator;
    }
}
