package banking.exception;

/**
 * Thrown when an account number does not exist in the system
 * or the account is in a closed/inactive state.
 */
public class InvalidAccountException extends Exception {

    public InvalidAccountException(String message) {
        super(message);
    }
}
