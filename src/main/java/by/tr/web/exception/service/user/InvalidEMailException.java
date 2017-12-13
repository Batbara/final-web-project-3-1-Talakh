package by.tr.web.exception.service.user;

public class InvalidEMailException extends UserServiceException {
    public InvalidEMailException() {
        super();
    }

    public InvalidEMailException(String message) {
        super(message);
    }

    public InvalidEMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEMailException(Throwable cause) {
        super(cause);
    }
}
