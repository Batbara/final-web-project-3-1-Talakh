package by.tr.web.service.input_validator;

public class RequestParameterNotFound extends ValidationException {
    private static final long serialVersionUID = 8556195117155008798L;

    public RequestParameterNotFound() {
        super();
    }

    public RequestParameterNotFound(String message) {
        super(message);
    }

    public RequestParameterNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParameterNotFound(Throwable cause) {
        super(cause);
    }
}
