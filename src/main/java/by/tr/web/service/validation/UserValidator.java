package by.tr.web.service.validation;

import by.tr.web.exception.service.common.ValidationException;
import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.InvalidEmailException;
import by.tr.web.exception.service.user.InvalidLoginException;

public interface UserValidator {
    String EMAIL_REGEXP = "^[A-Za-z0-9+_.-]+@(.+)$";
    String LOGIN_REGEXP = "^[a-zA-Z0-9_]{3,}$";
    int MIN_PASSWORD_LENGTH = 5;
    int MAX_PASSWORD_LENGTH = 16;

    boolean checkLogin(String login);

    boolean checkPassword(String password);

    boolean checkEmail(String email);

    boolean checkUserStatus(String userStatus) throws ValidationException;

    default boolean validateCredentials(String login, String password) throws ValidationException {
        if (!checkLogin(login)) {
            throw new InvalidLoginException("Incorrect login");
        }
        if (!checkPassword(password)) {
            throw new IncorrectPasswordException("Invalid password");
        }
        return true;
    }

    default boolean validateCredentials(String login, String password, String email) throws ValidationException {
        if (!checkLogin(login)) {
            throw new InvalidLoginException("Incorrect login");
        }
        if (!checkEmail(email)) {
            throw new InvalidEmailException("Invalid email");
        }
        if (!checkPassword(password)) {
            throw new IncorrectPasswordException("Invalid password");
        }
        return true;
    }
}
