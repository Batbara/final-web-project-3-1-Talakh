package by.tr.web.service.validation.impl;

import by.tr.web.exception.service.IncorrectPasswordException;
import by.tr.web.exception.service.InvalidLoginException;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.service.validation.UserValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator implements UserValidator {
    private  Pattern loginPattern = Pattern.compile(UserValidator.LOGIN_REGEXP);
    public boolean checkLogin(String login) {
        if(login.isEmpty()){
            return false;
        }

        Matcher matcher = loginPattern.matcher(login);
        return matcher.matches();
    }

    public boolean checkPassword(String password) {
        if(password.isEmpty()){
            return false;
        }
        return password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH;
    }
    public boolean validate(String login, String password) throws UserServiceException {
        if(!checkLogin(login)){
            throw new InvalidLoginException("Incorrect login");
        }
        if(!checkPassword(password)){
            throw new IncorrectPasswordException("Invalid password");
        }
        return true;
    }
}
