package by.tr.web.dao.exception;

public class CounterDAOException extends DAOException {
    private static final long serialVersionUID = 2732172368294469994L;

    public CounterDAOException() {
        super();
    }

    public CounterDAOException(String message) {
        super(message);
    }

    public CounterDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
