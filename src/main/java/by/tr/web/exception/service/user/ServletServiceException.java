package by.tr.web.exception.service.user;

public class ServletServiceException extends Exception {
    private static final long serialVersionUID = -1435654680351679182L;

    public ServletServiceException() {
        super();
    }

    public ServletServiceException(String message) {
        super(message);
    }

    public ServletServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServletServiceException(Throwable cause) {
        super(cause);
    }
}
