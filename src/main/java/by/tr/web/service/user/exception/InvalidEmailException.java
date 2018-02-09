package by.tr.web.service.user.exception;

import by.tr.web.service.input_validator.ValidationException;

public class InvalidEmailException extends ValidationException {
    private static final long serialVersionUID = 3402242625476892674L;

    public InvalidEmailException() {
        super();
    }

    public InvalidEmailException(String message) {
        super(message);
    }

    public InvalidEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailException(Throwable cause) {
        super(cause);
    }
}
