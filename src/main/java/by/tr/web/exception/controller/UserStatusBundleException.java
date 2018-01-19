package by.tr.web.exception.controller;

public class UserStatusBundleException extends RuntimeException {
    private static final long serialVersionUID = -2108817442337940808L;

    public UserStatusBundleException() {
        super();
    }

    public UserStatusBundleException(String message) {
        super(message);
    }

    public UserStatusBundleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserStatusBundleException(Throwable cause) {
        super(cause);
    }
}
