package by.tr.web.exception.service;

public class InvalidLoginException extends UserServiceException {

    private static final long serialVersionUID = 5312994197872900803L;

    public InvalidLoginException() {
        super();
    }

    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLoginException(Throwable cause) {
        super(cause);
    }
}
