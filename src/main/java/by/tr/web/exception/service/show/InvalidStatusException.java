package by.tr.web.exception.service.show;

import by.tr.web.exception.service.common.ValidationException;

public class InvalidStatusException extends ValidationException {
    private static final long serialVersionUID = 7433721382009154434L;

    public InvalidStatusException() {
        super();
    }

    public InvalidStatusException(String message) {
        super(message);
    }

    public InvalidStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStatusException(Throwable cause) {
        super(cause);
    }
}
