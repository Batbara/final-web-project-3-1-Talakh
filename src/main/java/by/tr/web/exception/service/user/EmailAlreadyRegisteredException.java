package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ServiceException;

public class EmailAlreadyRegisteredException extends ServiceException {
    private static final long serialVersionUID = 8925138408397571077L;

    public EmailAlreadyRegisteredException() {
        super();
    }

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }

    public EmailAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
