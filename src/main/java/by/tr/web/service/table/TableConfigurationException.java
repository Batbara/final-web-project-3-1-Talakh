package by.tr.web.service.table;

import by.tr.web.service.ServiceException;

public class TableConfigurationException extends ServiceException {
    private static final long serialVersionUID = -5714481765134007735L;

    public TableConfigurationException() {
        super();
    }

    public TableConfigurationException(String message) {
        super(message);
    }

    public TableConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableConfigurationException(Throwable cause) {
        super(cause);
    }
}
