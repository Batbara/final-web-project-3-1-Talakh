package by.tr.web.service.tv_show;

import by.tr.web.service.input_validator.ValidationException;

public class InvalidTvShowStatusException extends ValidationException {
    private static final long serialVersionUID = 1388382317723735039L;

    public InvalidTvShowStatusException() {
        super();
    }

    public InvalidTvShowStatusException(String message) {
        super(message);
    }

    public InvalidTvShowStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTvShowStatusException(Throwable cause) {
        super(cause);
    }
}
