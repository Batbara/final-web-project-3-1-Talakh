package by.tr.web.service.validation;

import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.InvalidEMailException;
import by.tr.web.exception.service.user.InvalidLoginException;
import by.tr.web.exception.service.user.UserServiceException;

public interface UserValidator {
    String EMAIL_REGEXP = "^[A-Za-z0-9+_.-]+@(.+)$";
    String LOGIN_REGEXP = "^[a-zA-Z0-9_]{3,}$";
    int MIN_PASSWORD_LENGTH = 5;
    int MAX_PASSWORD_LENGTH = 16;

    boolean checkLogin(String login);
    boolean checkPassword(String password);
    boolean checkEMail(String email);

    default boolean validateCredentials (String login, String password) throws UserServiceException{
        if(!checkLogin(login)){
            throw new InvalidLoginException("Incorrect login");
        }
        if(!checkPassword(password)){
            throw new IncorrectPasswordException("Invalid password");
        }
        return true;
    }
    default boolean validateCredentials (String login, String password, String eMail) throws UserServiceException{
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
}
