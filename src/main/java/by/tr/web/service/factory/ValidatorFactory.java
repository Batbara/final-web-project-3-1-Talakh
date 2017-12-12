package by.tr.web.service.factory;

import by.tr.web.service.validation.UserValidator;
import by.tr.web.service.validation.impl.LoginValidatorImpl;
import by.tr.web.service.validation.impl.RegisterValidatorImpl;

public class ValidatorFactory {
    private static ValidatorFactory instance = new ValidatorFactory();
    private UserValidator registerValidator = new RegisterValidatorImpl();
    private UserValidator loginValidator = new LoginValidatorImpl();

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

}
