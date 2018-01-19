package by.tr.web.exception.controller;

public class CustomTagLibException extends RuntimeException {
    private static final long serialVersionUID = 2755333037717126003L;

    public CustomTagLibException() {
        super();
    }

    public CustomTagLibException(String message) {
        super(message);
    }

    public CustomTagLibException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomTagLibException(Throwable cause) {
        super(cause);
    }
}
