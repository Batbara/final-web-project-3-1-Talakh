package by.tr.web.exception.dao.user;

import by.tr.web.exception.dao.common.DAOException;

public class PasswordDAOException extends DAOException {
    private static final long serialVersionUID = 2156066305953020607L;

    public PasswordDAOException() {
        super();
    }

    public PasswordDAOException(String message) {
        super(message);
    }

    public PasswordDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordDAOException(Throwable cause) {
        super(cause);
    }
}
