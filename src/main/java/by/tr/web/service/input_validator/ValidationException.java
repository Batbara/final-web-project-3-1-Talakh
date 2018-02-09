package by.tr.web.service.input_validator;

import by.tr.web.service.ServiceException;

public class ValidationException extends ServiceException {
    private static final long serialVersionUID = -8483644296133984129L;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
