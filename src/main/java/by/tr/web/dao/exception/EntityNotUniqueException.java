package by.tr.web.dao.exception;

public class EntityNotUniqueException extends DAOException {
    private static final long serialVersionUID = 2890261508700791849L;

    public EntityNotUniqueException() {
        super();
    }

    public EntityNotUniqueException(String message) {
        super(message);
    }

    public EntityNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotUniqueException(Throwable cause) {
        super(cause);
    }
}
