package by.tr.web.exception.controller;

public class CommandProviderException extends Exception {
    public CommandProviderException() {
        super();
    }

    public CommandProviderException(String message) {
        super(message);
    }

    public CommandProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandProviderException(Throwable cause) {
        super(cause);
    }
}
