package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ServiceException;

public class CountingUserException extends ServiceException {
    private static final long serialVersionUID = -4255759050543739701L;

    public CountingUserException() {
        super();
    }

    public CountingUserException(String message) {
        super(message);
    }

    public CountingUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CountingUserException(Throwable cause) {
        super(cause);
    }
}
