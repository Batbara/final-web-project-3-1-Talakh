package by.tr.web.service.user;

import by.tr.web.domain.User;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.input_validator.ValidationException;
import by.tr.web.service.show.review.InvalidReviewStatusException;
import by.tr.web.service.user.exception.InvalidEmailException;
import by.tr.web.service.user.exception.InvalidLoginException;
import by.tr.web.service.user.exception.InvalidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidatorImpl implements UserValidator {
    private Pattern eMailPattern = Pattern.compile(UserValidator.EMAIL_REGEXP);
    private Pattern loginPattern = Pattern.compile(UserValidator.LOGIN_REGEXP);

    @Override
    public boolean checkLogin(String login) throws RequestParameterNotFound, InvalidLoginException {
        if (login == null) {
            throw new RequestParameterNotFound("Login is empty");
        }

        Matcher matcher = loginPattern.matcher(login);
        if (!matcher.matches()) {
            throw new InvalidLoginException("Login " + login + " is invalid");
        }
        return true;
    }

    @Override
    public boolean checkPassword(String password) throws RequestParameterNotFound, InvalidPasswordException {
        if (password == null) {
            throw new RequestParameterNotFound("Password is empty");
        }
        boolean isPasswordValid = password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH;
        if (!isPasswordValid) {
            throw new InvalidPasswordException("Password " + password + " is invalid");
        }
        return true;
    }

    @Override
    public boolean checkEmail(String email) throws RequestParameterNotFound, InvalidEmailException {
        if (email == null) {
            throw new RequestParameterNotFound("Email is empty");
        }
        Matcher matcher = eMailPattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("Email doesn't match pattern");
        }
        return true;
    }

    @Override
    public boolean checkUserStatus(String inputUserStatus) throws ValidationException {
        if (inputUserStatus == null) {
            throw new InvalidReviewStatusException("Input user status is empty");
        }
        for (User.UserStatus userStatus : User.UserStatus.values()) {
            String statusName = userStatus.name();
            if (statusName.equalsIgnoreCase(inputUserStatus)) {
                return true;
            }
        }
        throw new InvalidReviewStatusException("Invalid user status constant");
    }
}
