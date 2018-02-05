package by.tr.web.exception.service.common;

public class EmptyParameterException extends ValidationException {
    private static final long serialVersionUID = 8556195117155008798L;

    public EmptyParameterException() {
        super();
    }

    public EmptyParameterException(String message) {
        super(message);
    }

    public EmptyParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyParameterException(Throwable cause) {
        super(cause);
    }
}
