package banking.model;

/**
 * Current account — extends BankAccount.
 *
 * Demonstrates:
 * - Inheritance (extends abstract BankAccount)
 * - Method overriding (@Override getMinimumBalance, displayAccountDetails, toString)
 * - Polymorphism (when used as BankAccount reference)
 *
 * Business Rule (BR-CUR-001):
 *   Current accounts permit an overdraft up to ₹5,000.
 *   Minimum permitted balance is -₹5,000.
 */
public class CurrentAccount extends BankAccount {

    // Overdraft limit for current accounts (BR-CUR-001)
    private static final double OVERDRAFT_LIMIT = 5000.00;

    // Minimum balance = negative overdraft limit
    private static final double MINIMUM_BALANCE = -OVERDRAFT_LIMIT;

    // ── No-arg constructor ──────────────────────────────────────────
    public CurrentAccount() {
        setAccountType("CURRENT");
    }

    // ── Parameterized constructor ───────────────────────────────────
    public CurrentAccount(String accountNumber, long customerId, double balance) {
        super(accountNumber, customerId, "CURRENT", balance);
    }

    // ── Override: account-specific minimum balance ──────────────────

    /**
     * Current account minimum balance is -₹5,000 (overdraft allowed).
     * Used by canWithdraw() in BankAccount to enforce the rule.
     *
     * Polymorphism example:
     *   BankAccount acct = new CurrentAccount(...);
     *   acct.getMinimumBalance(); // returns -5000.0 at runtime
     */
    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    // ── Convenience method ──────────────────────────────────────────

    /**
     * Returns how much overdraft is still available.
     */
    public double getAvailableOverdraft() {
        return getBalance() - MINIMUM_BALANCE;
    }

    // ── Override: display with current-account-specific info ────────

    @Override
    public String displayAccountDetails() {
        return super.displayAccountDetails() +
                " | Overdraft Limit: ₹" + String.format("%.2f", OVERDRAFT_LIMIT);
    }

    // ── Override: toString ──────────────────────────────────────────

    @Override
    public String toString() {
        return "CurrentAccount{" +
                "accountNumber='" + getAccountNumber() + '\'' +
                ", balance=₹" + String.format("%.2f", getBalance()) +
                ", overdraftLimit=₹" + String.format("%.2f", OVERDRAFT_LIMIT) +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
