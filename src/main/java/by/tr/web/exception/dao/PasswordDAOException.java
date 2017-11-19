package by.tr.web.exception.dao;

public class PasswordDAOException extends UserDAOException {
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
