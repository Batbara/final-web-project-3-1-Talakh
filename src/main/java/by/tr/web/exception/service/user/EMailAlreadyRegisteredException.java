package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ServiceException;

public class EMailAlreadyRegisteredException extends ServiceException {
    private static final long serialVersionUID = 8925138408397571077L;

    public EMailAlreadyRegisteredException() {
        super();
    }

    public EMailAlreadyRegisteredException(String message) {
        super(message);
    }

    public EMailAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EMailAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
