package by.tr.web.dao.movie;

import by.tr.web.dao.exception.DAOException;

public class NoSuchMovieException extends DAOException {
    private static final long serialVersionUID = -6221170658256289568L;

    public NoSuchMovieException() {
        super();
    }

    public NoSuchMovieException(String message) {
        super(message);
    }

    public NoSuchMovieException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMovieException(Throwable cause) {
        super(cause);
    }
}
