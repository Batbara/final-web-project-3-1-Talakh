package by.tr.web.exception.service.movie;

import by.tr.web.exception.service.common.ServiceException;

public class MovieServiceException extends ServiceException {
    private static final long serialVersionUID = -8358041243221981494L;

    public MovieServiceException() {
        super();
    }

    public MovieServiceException(String message) {
        super(message);
    }

    public MovieServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieServiceException(Throwable cause) {
        super(cause);
    }
}
