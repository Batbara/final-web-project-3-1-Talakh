package by.tr.web.exception.service;

public class UserServiceException extends Exception {
    private static final long serialVersionUID = -911875504649229665L;

    public UserServiceException() {
        super();
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }
}
