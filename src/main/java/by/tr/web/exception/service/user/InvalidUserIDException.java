package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ServiceException;

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
