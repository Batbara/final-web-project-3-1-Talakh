package by.tr.web.exception.dao;

public class UserDAOException extends Exception {
    private static final long serialVersionUID = 487585126590173697L;

    public UserDAOException() {
        super();
    }

    public UserDAOException(String message) {
        super(message);
    }

    public UserDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDAOException(Throwable cause) {
        super(cause);
    }
}
