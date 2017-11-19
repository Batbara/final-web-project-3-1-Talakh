package by.tr.web.exception.service;

public class ValidationException extends Exception{
    private static final long serialVersionUID = -4504108697238460573L;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
