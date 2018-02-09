package by.tr.web.service.user.exception;

import by.tr.web.service.ServiceException;

public class NoSuchUserException extends ServiceException {

    private static final long serialVersionUID = -1256908481940706977L;

    public NoSuchUserException() {
        super();
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserException(Throwable cause) {
        super(cause);
    }
}
