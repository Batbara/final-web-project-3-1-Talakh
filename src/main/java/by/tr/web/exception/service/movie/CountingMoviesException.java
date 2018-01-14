package by.tr.web.exception.service.movie;

import by.tr.web.exception.service.common.ServiceException;

public class CountingMoviesException extends ServiceException {
    private static final long serialVersionUID = 659235161159424442L;

    public CountingMoviesException() {
        super();
    }

    public CountingMoviesException(String message) {
        super(message);
    }

    public CountingMoviesException(String message, Throwable cause) {
        super(message, cause);
    }

    public CountingMoviesException(Throwable cause) {
        super(cause);
    }
}
