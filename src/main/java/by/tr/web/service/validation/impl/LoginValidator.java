package by.tr.web.service.validation.impl;

import by.tr.web.service.validation.UserValidator;

public class LoginValidator implements UserValidator {
    public boolean checkLogin(String login) {
        return false;
    }

    public boolean checkPassword(String password) {
        return false;
    }
}
