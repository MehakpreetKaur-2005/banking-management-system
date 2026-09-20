package banking.exception;

/**
 * Thrown when a transaction amount is invalid.
 * For example: zero, negative, or non-numeric input.
 */
public class InvalidAmountException extends Exception {

    public InvalidAmountException(String message) {
        super(message);
    }
}
