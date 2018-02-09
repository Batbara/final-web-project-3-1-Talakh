package by.tr.web.dao.user;

import by.tr.web.dao.exception.DAOException;

public class BanUserException extends DAOException {
    private static final long serialVersionUID = 7433345465168538989L;

    public BanUserException() {
        super();
    }

    public BanUserException(String message) {
        super(message);
    }

    public BanUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BanUserException(Throwable cause) {
        super(cause);
    }
}
