package banking.exception;

/**
 * Thrown when a withdrawal or transfer would violate the
 * account's minimum balance rule.
 *
 * - Savings account: balance cannot go below ₹1,000
 * - Current account: balance cannot go below -₹5,000
 */
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
