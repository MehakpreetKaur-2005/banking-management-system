package banking.model;

import java.time.LocalDateTime;

/**
 * Represents a financial transaction (deposit, withdrawal, or transfer).
 *
 * Demonstrates:
 * - Constructors
 * - toString() override
 * - Encapsulation
 */
public class Transaction {

    private long transactionId;
    private String transactionReference;
    private long accountId;
    private Long relatedAccountId;  // nullable — only used for transfers
    private long employeeId;
    private String transactionType;  // DEPOSIT, WITHDRAWAL, TRANSFER
    private double amount;
    private String status;           // SUCCESS, FAILED
    private String description;
    private LocalDateTime transactionDate;

    // ── No-arg constructor ──────────────────────────────────────────
    public Transaction() {
    }

    // ── Parameterized constructor ───────────────────────────────────
    public Transaction(String transactionReference, long accountId,
                       Long relatedAccountId, long employeeId,
                       String transactionType, double amount,
                       String status, String description) {
        this.transactionReference = transactionReference;
        this.accountId = accountId;
        this.relatedAccountId = relatedAccountId;
        this.employeeId = employeeId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.status = status;
        this.description = description;
        this.transactionDate = LocalDateTime.now();
    }

    // ── Getters and Setters ─────────────────────────────────────────

    public long getTransactionId() { return transactionId; }
    public void setTransactionId(long transactionId) { this.transactionId = transactionId; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }

    public Long getRelatedAccountId() { return relatedAccountId; }
    public void setRelatedAccountId(Long relatedAccountId) { this.relatedAccountId = relatedAccountId; }

    public long getEmployeeId() { return employeeId; }
    public void setEmployeeId(long employeeId) { this.employeeId = employeeId; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    // ── toString ────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Transaction{" +
                "ref='" + transactionReference + '\'' +
                ", type='" + transactionType + '\'' +
                ", amount=₹" + String.format("%.2f", amount) +
                ", status='" + status + '\'' +
                ", date=" + transactionDate +
                '}';
    }
}
