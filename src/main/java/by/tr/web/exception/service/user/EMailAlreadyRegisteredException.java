package by.tr.web.exception.service.user;

public class EMailAlreadyRegisteredException extends UserServiceException{
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
