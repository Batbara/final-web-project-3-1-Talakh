package by.tr.web.exception.dao.movie;

public class MovieCounterDAOException extends MovieDAOException {
    private static final long serialVersionUID = 2732172368294469994L;

    public MovieCounterDAOException() {
        super();
    }

    public MovieCounterDAOException(String message) {
        super(message);
    }

    public MovieCounterDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
