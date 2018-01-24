package by.tr.web.exception.dao.common;

public class UnsupportedOperationException extends DAOException {
    private static final long serialVersionUID = 8951818321868424492L;

    public UnsupportedOperationException() {
        super();
    }

    public UnsupportedOperationException(String message) {
        super(message);
    }

    public UnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedOperationException(Throwable cause) {
        super(cause);
    }
}
