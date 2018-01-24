package by.tr.web.cookie;

public class NoSuchCookieInRequest extends Exception {
    private static final long serialVersionUID = -2361037195497980551L;

    public NoSuchCookieInRequest() {
        super();
    }

    public NoSuchCookieInRequest(String message) {
        super(message);
    }

    public NoSuchCookieInRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCookieInRequest(Throwable cause) {
        super(cause);
    }
}
