package by.tr.web.dao.exception;

public class DAOException extends Exception {
    private static final long serialVersionUID = 3993652767896369245L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
