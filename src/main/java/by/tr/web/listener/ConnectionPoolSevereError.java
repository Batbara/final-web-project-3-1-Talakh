package by.tr.web.listener;

public class ConnectionPoolSevereError extends RuntimeException {
    public ConnectionPoolSevereError() {
        super();
    }

    public ConnectionPoolSevereError(String message) {
        super(message);
    }

    public ConnectionPoolSevereError(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolSevereError(Throwable cause) {
        super(cause);
    }
}
