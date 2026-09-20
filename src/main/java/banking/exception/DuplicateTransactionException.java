package banking.exception;

/**
 * Thrown when a duplicate transaction reference is detected.
 */
public class DuplicateTransactionException extends Exception {

    public DuplicateTransactionException(String message) {
        super(message);
    }
}
