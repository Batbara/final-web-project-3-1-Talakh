package by.tr.web.dao.user;

import by.tr.web.dao.exception.DAOException;

public class UserNotRegisteredException extends DAOException {
    private static final long serialVersionUID = -6911729177784956606L;

    public UserNotRegisteredException() {
        super();
    }

    public UserNotRegisteredException(String message) {
        super(message);
    }

    public UserNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
