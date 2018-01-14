package by.tr.web.exception.dao.movie;

public class MovieInitializationException extends MovieDAOException {
    private static final long serialVersionUID = 6135724393141831935L;

    public MovieInitializationException() {
        super();
    }

    public MovieInitializationException(String message) {
        super(message);
    }

    public MovieInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieInitializationException(Throwable cause) {
        super(cause);
    }
}
