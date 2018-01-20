package by.tr.web.exception.dao.user;

import by.tr.web.exception.dao.common.DAOException;

public class RegistrationDAOException extends DAOException {
    private static final long serialVersionUID = 5596591592489986894L;

    public RegistrationDAOException() {
        super();
    }

    public RegistrationDAOException(String message) {
        super(message);
    }

    public RegistrationDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationDAOException(Throwable cause) {
        super(cause);
    }
}
