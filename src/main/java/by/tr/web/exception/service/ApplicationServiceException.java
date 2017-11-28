package by.tr.web.exception.service;

public class ApplicationServiceException extends Exception {
    private static final long serialVersionUID = -1435654680351679182L;

    public ApplicationServiceException() {
        super();
    }

    public ApplicationServiceException(String message) {
        super(message);
    }

    public ApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationServiceException(Throwable cause) {
        super(cause);
    }
}
