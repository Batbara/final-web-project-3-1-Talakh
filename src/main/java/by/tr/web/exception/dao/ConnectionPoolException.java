package by.tr.web.exception.dao;

public class ConnectionPoolException extends UserDAOException {
    private static final long serialVersionUID = 6549626552288340993L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
