package by.tr.web.exception.service.common;

public class LangNotSupportedException extends ServiceException {
    private static final long serialVersionUID = -1458543367474337743L;

    public LangNotSupportedException() {
        super();
    }

    public LangNotSupportedException(String message) {
        super(message);
    }

    public LangNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LangNotSupportedException(Throwable cause) {
        super(cause);
    }
}
