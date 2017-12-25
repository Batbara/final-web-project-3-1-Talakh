package by.tr.web.exception.dao.movie;

public class MovieDAOException extends Exception {
    private static final long serialVersionUID = -3228655725688694944L;

    public MovieDAOException() {
        super();
    }

    public MovieDAOException(String message) {
        super(message);
    }

    public MovieDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieDAOException(Throwable cause) {
        super(cause);
    }
}
