package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ServiceException;

public class InvalidEMailException extends ServiceException {
    private static final long serialVersionUID = 3402242625476892674L;

    public InvalidEMailException() {
        super();
    }

    public InvalidEMailException(String message) {
        super(message);
    }

    public InvalidEMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEMailException(Throwable cause) {
        super(cause);
    }
}
