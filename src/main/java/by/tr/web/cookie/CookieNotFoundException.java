package by.tr.web.cookie;

public class CookieNotFoundException extends Exception {
    private static final long serialVersionUID = -231829454816238927L;

    public CookieNotFoundException() {
        super();
    }

    public CookieNotFoundException(String message) {
        super(message);
    }

    public CookieNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieNotFoundException(Throwable cause) {
        super(cause);
    }
}
