package by.tr.web.service.user.exception;

import by.tr.web.service.input_validator.ValidationException;

public class InvalidPasswordException extends ValidationException {
    private static final long serialVersionUID = 5159029242095774367L;

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(Throwable cause) {
        super(cause);
    }
}
