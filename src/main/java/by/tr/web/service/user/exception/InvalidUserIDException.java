package by.tr.web.service.user.exception;

import by.tr.web.service.ServiceException;

public class InvalidUserIDException extends ServiceException {
    private static final long serialVersionUID = -6735165752322119315L;

    public InvalidUserIDException() {
        super();
    }

    public InvalidUserIDException(String message) {
        super(message);
    }

    public InvalidUserIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserIDException(Throwable cause) {
        super(cause);
    }
}
