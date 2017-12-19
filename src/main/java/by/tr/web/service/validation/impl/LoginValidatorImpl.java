package by.tr.web.service.validation.impl;

import by.tr.web.service.validation.UserValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidatorImpl implements UserValidator {
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

    @Override
    public boolean checkEMail(String email) {
        return false;
    }
}
