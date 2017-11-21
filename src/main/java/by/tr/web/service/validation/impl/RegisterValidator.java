package by.tr.web.service.validation.impl;

import by.tr.web.exception.ExceptionMessage;
import by.tr.web.exception.service.ValidationException;
import by.tr.web.service.validation.UserValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterValidator implements UserValidator {
    public boolean checkLogin(String login) {
        if(login.isEmpty()){
            return false;
        }
        Pattern pattern = Pattern.compile(UserValidator.LOGIN_REGEXP);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public boolean checkPassword(String password) {
        if(password.isEmpty()){
            return false;
        }
        return password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH;
    }
    public boolean checkEMail(String eMail){
        if(eMail.isEmpty()){
            return false;
        }
        Pattern pattern = Pattern.compile(UserValidator.EMAIL_REGEXP);
        Matcher matcher = pattern.matcher(eMail);
        return matcher.matches();
    }
    public boolean validate(String login, String password, String eMail) throws ValidationException {
        if(!checkLogin(login)){
            throw new ValidationException(ExceptionMessage.INVALID_LOGIN);
        }
        if(!checkEMail(eMail)){
            throw  new ValidationException(ExceptionMessage.INVALID_EMAIL);
        }
        if(!checkPassword(password)){
            throw new ValidationException(ExceptionMessage.INVALID_PASSWORD);
        }

        return true;
    }


}
