package by.tr.web.service.show.review;

import by.tr.web.service.ServiceException;

public class ReviewException extends ServiceException {
    private static final long serialVersionUID = -4423511095599498078L;

    public ReviewException() {
        super();
    }

    public ReviewException(String message) {
        super(message);
    }

    public ReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewException(Throwable cause) {
        super(cause);
    }
}
