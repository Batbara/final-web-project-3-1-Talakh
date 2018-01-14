package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ServiceException;

public class InvalidLoginException extends ServiceException {

    private static final long serialVersionUID = 5312994197872900803L;

    public InvalidLoginException() {
        super();
    }

    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLoginException(Throwable cause) {
        super(cause);
    }
}
