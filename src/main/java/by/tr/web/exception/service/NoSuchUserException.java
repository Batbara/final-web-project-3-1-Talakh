package by.tr.web.exception.service;

public class NoSuchUserException extends UserServiceException {

    private static final long serialVersionUID = -1256908481940706977L;

    public NoSuchUserException() {
        super();
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserException(Throwable cause) {
        super(cause);
    }
}
