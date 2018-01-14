package by.tr.web.exception.service.movie;

import by.tr.web.exception.service.common.ServiceException;

public class InvalidOrderTypeException extends ServiceException {
    private static final long serialVersionUID = 2674385413537163161L;

    public InvalidOrderTypeException() {
        super();
    }

    public InvalidOrderTypeException(String message) {
        super(message);
    }

    public InvalidOrderTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOrderTypeException(Throwable cause) {
        super(cause);
    }
}
