package by.tr.web.service.validation.impl;

import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.InvalidEMailException;
import by.tr.web.exception.service.user.InvalidLoginException;
import by.tr.web.exception.service.user.UserServiceException;
import by.tr.web.service.validation.UserValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterValidatorImpl implements UserValidator {
    private Pattern eMailPattern = Pattern.compile(UserValidator.EMAIL_REGEXP);
    private Pattern loginPattern = Pattern.compile(UserValidator.LOGIN_REGEXP);
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

    @Override
    public boolean validate(String... parameters) throws UserServiceException {
        String login = parameters[0];
        String password = parameters[1];
        String eMail = parameters[2];
        if(!checkLogin(login)){
            throw new InvalidLoginException("Incorrect login");
        }
        if(!checkEMail(eMail)){
            throw  new InvalidEMailException("Invalid email");
        }
        if(!checkPassword(password)){
            throw new IncorrectPasswordException("Invalid password");
        }
        return true;
    }

    private boolean checkEMail(String eMail){
        if(eMail.isEmpty()){
            return false;
        }
        Matcher matcher = eMailPattern.matcher(eMail);
        return matcher.matches();
    }

}
