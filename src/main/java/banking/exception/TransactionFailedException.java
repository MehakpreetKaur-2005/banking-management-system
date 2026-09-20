package banking.exception;

/**
 * Thrown when a transaction cannot be completed due to
 * a system error (e.g., database failure, connection issue).
 */
public class TransactionFailedException extends Exception {

    public TransactionFailedException(String message) {
        super(message);
    }

    public TransactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
