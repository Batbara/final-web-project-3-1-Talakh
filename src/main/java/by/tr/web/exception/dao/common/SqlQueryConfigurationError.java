package by.tr.web.exception.dao.common;

public class SqlQueryConfigurationError extends DAOException {
    private static final long serialVersionUID = -1510256511573643546L;

    public SqlQueryConfigurationError() {
        super();
    }

    public SqlQueryConfigurationError(String message) {
        super(message);
    }

    public SqlQueryConfigurationError(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlQueryConfigurationError(Throwable cause) {
        super(cause);
    }
}
