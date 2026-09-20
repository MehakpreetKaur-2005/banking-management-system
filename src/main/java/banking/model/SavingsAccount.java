package banking.model;

/**
 * Savings account — extends BankAccount.
 *
 * Demonstrates:
 * - Inheritance (extends abstract BankAccount)
 * - Method overriding (@Override getMinimumBalance, displayAccountDetails, toString)
 * - Polymorphism (when used as BankAccount reference)
 *
 * Business Rule (BR-SAV-001):
 *   Savings accounts must maintain a minimum balance of ₹1,000.
 */
public class SavingsAccount extends BankAccount {

    // Minimum balance for savings accounts (BR-SAV-001)
    private static final double MINIMUM_BALANCE = 1000.00;

    // ── No-arg constructor ──────────────────────────────────────────
    public SavingsAccount() {
        setAccountType("SAVINGS");
    }

    // ── Parameterized constructor ───────────────────────────────────
    public SavingsAccount(String accountNumber, long customerId, double balance) {
        super(accountNumber, customerId, "SAVINGS", balance);
    }

    // ── Override: account-specific minimum balance ──────────────────

    /**
     * Savings account minimum balance is ₹1,000.
     * Used by canWithdraw() in BankAccount to enforce the rule.
     *
     * Polymorphism example:
     *   BankAccount acct = new SavingsAccount(...);
     *   acct.getMinimumBalance(); // returns 1000.0 at runtime
     */
    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    // ── Override: display with savings-specific info ─────────────────

    @Override
    public String displayAccountDetails() {
        return super.displayAccountDetails() +
                " | Min Balance: ₹" + String.format("%.2f", MINIMUM_BALANCE);
    }

    // ── Override: toString ──────────────────────────────────────────

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "accountNumber='" + getAccountNumber() + '\'' +
                ", balance=₹" + String.format("%.2f", getBalance()) +
                ", minBalance=₹" + String.format("%.2f", MINIMUM_BALANCE) +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
