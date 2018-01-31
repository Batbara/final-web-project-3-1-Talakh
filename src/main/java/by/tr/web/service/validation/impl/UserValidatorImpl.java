package by.tr.web.service.validation.impl;

import by.tr.web.domain.User;
import by.tr.web.exception.service.common.ValidationException;
import by.tr.web.exception.service.show.InvalidStatusException;
import by.tr.web.service.validation.UserValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidatorImpl implements UserValidator{
    private Pattern eMailPattern = Pattern.compile(UserValidator.EMAIL_REGEXP);
    private Pattern loginPattern = Pattern.compile(UserValidator.LOGIN_REGEXP);
    @Override
    public boolean checkLogin(String login) {
        if(login.isEmpty()){
            return false;
        }

        Matcher matcher = loginPattern.matcher(login);
        return matcher.matches();
    }

    @Override
    public boolean checkPassword(String password) {
        if(password == null){
            return false;
        }
        return password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH;
    }

    @Override
    public boolean checkEmail(String email) {
        if(email == null){
            return false;
        }
        Matcher matcher = eMailPattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean checkUserStatus(String inputUserStatus) throws ValidationException {
        if (inputUserStatus == null) {
            throw new InvalidStatusException("Input user status is empty");
        }
        for (User.UserStatus userStatus : User.UserStatus.values()) {
            String statusName = userStatus.name();
            if (statusName.equalsIgnoreCase(inputUserStatus)) {
                return true;
            }
        }
        throw new InvalidStatusException("Invalid user status parameter");
    }
}
