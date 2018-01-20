package by.tr.web.exception.dao.user;

import by.tr.web.exception.dao.common.DAOException;

public class UserInitializaionException extends DAOException {
    private static final long serialVersionUID = 238886560311527843L;

    public UserInitializaionException() {
        super();
    }

    public UserInitializaionException(String message) {
        super(message);
    }

    public UserInitializaionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInitializaionException(Throwable cause) {
        super(cause);
    }
}
