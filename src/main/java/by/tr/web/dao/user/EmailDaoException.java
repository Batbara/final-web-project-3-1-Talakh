package by.tr.web.dao.user;

import by.tr.web.dao.exception.DAOException;

public class EmailDaoException extends DAOException {
    private static final long serialVersionUID = 6326351633477413517L;

    public EmailDaoException() {
        super();
    }

    public EmailDaoException(String message) {
        super(message);
    }

    public EmailDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDaoException(Throwable cause) {
        super(cause);
    }
}
