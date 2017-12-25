package by.tr.web.exception.service.movie;

public class CountingMoviesException extends MovieServiceException {
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
