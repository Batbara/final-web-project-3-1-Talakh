package by.tr.web.service.show;

import by.tr.web.service.ServiceException;

public class ShowAlreadyExistsException extends ServiceException {
    private static final long serialVersionUID = 2964757066832427583L;

    public ShowAlreadyExistsException() {
        super();
    }

    public ShowAlreadyExistsException(String message) {
        super(message);
    }

    public ShowAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShowAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
