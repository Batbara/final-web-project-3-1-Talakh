package by.tr.web.dao.exception;

public class NoSuchEntityException extends DAOException {
    private static final long serialVersionUID = 1451180118220838630L;

    public NoSuchEntityException() {
        super();
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }
}
