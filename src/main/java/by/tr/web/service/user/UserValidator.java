package by.tr.web.service.user;

import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.input_validator.ValidationException;
import by.tr.web.service.user.exception.InvalidEmailException;
import by.tr.web.service.user.exception.InvalidLoginException;
import by.tr.web.service.user.exception.InvalidPasswordException;

public interface UserValidator {
    String EMAIL_REGEXP = "^[A-Za-z0-9+_.-]+@(.+)$";
    String LOGIN_REGEXP = "^[a-zA-Z0-9_]{3,}$";
    int MIN_PASSWORD_LENGTH = 5;
    int MAX_PASSWORD_LENGTH = 16;

    boolean checkLogin(String login) throws RequestParameterNotFound, InvalidLoginException;

    boolean checkPassword(String password) throws RequestParameterNotFound, InvalidPasswordException;

    boolean checkEmail(String email) throws RequestParameterNotFound, InvalidEmailException;

    boolean checkUserStatus(String userStatus) throws ValidationException;

    default boolean validateCredentials(String login, String password) throws ValidationException {
        checkLogin(login);
        checkPassword(password);
        return true;
    }

    default boolean validateCredentials(String login, String password, String email) throws ValidationException {
        checkLogin(login);
        checkEmail(email);
        checkPassword(password);
        return true;
    }
}
