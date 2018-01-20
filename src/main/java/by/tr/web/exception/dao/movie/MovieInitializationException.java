package by.tr.web.exception.dao.movie;

import by.tr.web.exception.dao.common.DAOException;

public class MovieInitializationException extends DAOException {
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
