package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ValidationException;

public class IncorrectPasswordException extends ValidationException {
    private static final long serialVersionUID = 5159029242095774367L;

    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }
}
