package by.tr.web.exception.service.movie;

public class MovieServiceException extends Exception {
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
