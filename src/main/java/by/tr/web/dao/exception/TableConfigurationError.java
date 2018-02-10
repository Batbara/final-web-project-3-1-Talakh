package by.tr.web.dao.exception;

public class TableConfigurationError extends RuntimeException {
    private static final long serialVersionUID = -5575581723411152948L;

    public TableConfigurationError() {
        super();
    }

    public TableConfigurationError(String message) {
        super(message);
    }

    public TableConfigurationError(String message, Throwable cause) {
        super(message, cause);
    }

    public TableConfigurationError(Throwable cause) {
        super(cause);
    }
}
