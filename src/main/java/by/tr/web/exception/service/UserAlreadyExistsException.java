package by.tr.web.exception.service;

public class UserAlreadyExistsException extends UserServiceException {
    private static final long serialVersionUID = 6811922042048164788L;

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
