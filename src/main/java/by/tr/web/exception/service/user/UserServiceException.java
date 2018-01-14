package by.tr.web.exception.service.user;

import by.tr.web.exception.service.common.ServiceException;

public class UserServiceException extends ServiceException {
    private static final long serialVersionUID = -911875504649229665L;

    public UserServiceException() {
        super();
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }
}
