package by.tr.web.controller.command.command_provider;

public class CommandProviderError extends RuntimeException {
    public CommandProviderError() {
        super();
    }

    public CommandProviderError(String message) {
        super(message);
    }

    public CommandProviderError(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandProviderError(Throwable cause) {
        super(cause);
    }
}
