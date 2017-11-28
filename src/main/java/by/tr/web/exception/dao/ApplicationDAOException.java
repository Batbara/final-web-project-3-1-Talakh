package by.tr.web.exception.dao;

public class ApplicationDAOException extends Exception {

    private static final long serialVersionUID = 7682323191087407291L;

    public ApplicationDAOException() {
        super();
    }

    public ApplicationDAOException(String message) {
        super(message);
    }

    public ApplicationDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationDAOException(Throwable cause) {
        super(cause);
    }
}
