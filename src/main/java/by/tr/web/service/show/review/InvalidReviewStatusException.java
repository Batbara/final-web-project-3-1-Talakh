package by.tr.web.service.show.review;

import by.tr.web.service.input_validator.ValidationException;

public class InvalidReviewStatusException extends ValidationException {
    private static final long serialVersionUID = 7433721382009154434L;

    public InvalidReviewStatusException() {
        super();
    }

    public InvalidReviewStatusException(String message) {
        super(message);
    }

    public InvalidReviewStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReviewStatusException(Throwable cause) {
        super(cause);
    }
}
