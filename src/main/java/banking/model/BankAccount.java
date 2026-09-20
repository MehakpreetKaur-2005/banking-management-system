package banking.model;

import java.time.LocalDateTime;

/**
 * Abstract base class for all bank accounts.
 *
 * Demonstrates:
 * - Abstraction (abstract class with abstract method)
 * - Inheritance (SavingsAccount and CurrentAccount extend this)
 * - Method overloading (deposit with and without description)
 * - Polymorphism (getMinimumBalance() is resolved at runtime)
 * - Constructors
 * - toString() override
 */
public abstract class BankAccount {

    private long accountId;
    private String accountNumber;
    private long customerId;
    private String accountType;
    private double balance;
    private String status;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private LocalDateTime updatedAt;

    // ── No-arg constructor ──────────────────────────────────────────
    public BankAccount() {
    }

    // ── Parameterized constructor ───────────────────────────────────
    public BankAccount(String accountNumber, long customerId,
                       String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.status = "ACTIVE";
    }

    // ── Abstract method ─────────────────────────────────────────────
    // Each account type defines its own minimum allowed balance.
    // SavingsAccount → 1000.0 (minimum balance rule)
    // CurrentAccount → -5000.0 (overdraft limit)
    public abstract double getMinimumBalance();

    // ── Deposit — Method Overloading Demo ───────────────────────────

    /**
     * Deposit money into the account.
     * Overloaded method — basic version without description.
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Deposit money into the account with a description.
     * Overloaded method — extended version with description.
     * (Description is used when recording the transaction.)
     */
    public void deposit(double amount, String description) {
        this.balance += amount;
        // Description is used by the service layer when creating a Transaction record
    }

    // ── Withdraw ────────────────────────────────────────────────────

    /**
     * Withdraw money from the account.
     * Does NOT check minimum balance here — that validation is done
     * by the service layer using canWithdraw() before calling this.
     */
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    // ── Balance check ───────────────────────────────────────────────

    /**
     * Checks if a withdrawal of the given amount is permitted.
     * Uses getMinimumBalance() — polymorphism ensures the correct
     * minimum is checked based on the actual account type at runtime.
     *
     * Example:
     *   BankAccount acct = new SavingsAccount(...); // polymorphism
     *   acct.canWithdraw(5000); // checks against ₹1,000 minimum
     */
    public boolean canWithdraw(double amount) {
        return (this.balance - amount) >= getMinimumBalance();
    }

    // ── Display ─────────────────────────────────────────────────────

    /**
     * Displays account details — can be overridden by subclasses
     * to include type-specific information.
     */
    public String displayAccountDetails() {
        return "Account: " + accountNumber +
                " | Type: " + accountType +
                " | Balance: ₹" + String.format("%.2f", balance) +
                " | Status: " + status;
    }

    // ── Getters and Setters ─────────────────────────────────────────

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getOpenedAt() { return openedAt; }
    public void setOpenedAt(LocalDateTime openedAt) { this.openedAt = openedAt; }

    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ── toString ────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", type='" + accountType + '\'' +
                ", balance=₹" + String.format("%.2f", balance) +
                ", status='" + status + '\'' +
                '}';
    }
}
