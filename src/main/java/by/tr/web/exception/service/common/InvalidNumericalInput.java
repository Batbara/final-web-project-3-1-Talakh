package by.tr.web.exception.service.common;

public class InvalidNumericalInput extends ValidationException {
    private static final long serialVersionUID = -7703534771764676640L;

    public InvalidNumericalInput() {
        super();
    }

    public InvalidNumericalInput(String message) {
        super(message);
    }

    public InvalidNumericalInput(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNumericalInput(Throwable cause) {
        super(cause);
    }
}
