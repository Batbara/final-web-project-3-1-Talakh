package by.tr.web.service;

public class CountingServiceException extends ServiceException {
    private static final long serialVersionUID = 659235161159424442L;

    public CountingServiceException() {
        super();
    }

    public CountingServiceException(String message) {
        super(message);
    }

    public CountingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CountingServiceException(Throwable cause) {
        super(cause);
    }
}
