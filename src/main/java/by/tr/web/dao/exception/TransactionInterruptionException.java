package by.tr.web.dao.exception;

public class TransactionInterruptionException extends Exception {
    private static final long serialVersionUID = 497245169638554719L;

    public TransactionInterruptionException() {
        super();
    }

    public TransactionInterruptionException(String message) {
        super(message);
    }

    public TransactionInterruptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionInterruptionException(Throwable cause) {
        super(cause);
    }
}
