package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ValidationException;

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
