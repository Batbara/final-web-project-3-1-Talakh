package by.tr.web.service.validation;

public interface UserValidator {
    String EMAIL_REGEXP = "^[A-Za-z0-9+_.-]+@(.+)$";
    String LOGIN_REGEXP = "^[a-zA-Z0-9_]{3,}$";
    int MIN_PASSWORD_LENGTH = 5;
    int MAX_PASSWORD_LENGTH = 16;

    boolean checkLogin(String login);

    boolean checkPassword(String password);

}
