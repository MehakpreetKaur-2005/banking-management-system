package banking.dao;

import banking.model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Transaction operations.
 *
 * Uses plain JDBC with PreparedStatement.
 * Demonstrates Collections — returns List<Transaction>.
 */
public class TransactionDAO {

    // ── Save a transaction ──────────────────────────────────────────

    /**
     * Inserts a transaction record into the database.
     * Accepts a Connection so it can participate in a database transaction
     * (used during transfers where debit + credit + record must be atomic).
     */
    public void saveTransaction(Transaction transaction, Connection conn) throws SQLException {
        String sql = "INSERT INTO transactions (transaction_reference, account_id, " +
                "related_account_id, employee_id, transaction_type, amount, status, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, transaction.getTransactionReference());
            stmt.setLong(2, transaction.getAccountId());

            if (transaction.getRelatedAccountId() != null) {
                stmt.setLong(3, transaction.getRelatedAccountId());
            } else {
                stmt.setNull(3, Types.BIGINT);
            }

            stmt.setLong(4, transaction.getEmployeeId());
            stmt.setString(5, transaction.getTransactionType());
            stmt.setDouble(6, transaction.getAmount());
            stmt.setString(7, transaction.getStatus());
            stmt.setString(8, transaction.getDescription());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    transaction.setTransactionId(keys.getLong(1));
                }
            }
        }
    }

    // ── Get transactions by account number ──────────────────────────

    /**
     * Retrieves all transactions for a given account number.
     * Joins with accounts table to resolve account_number → account_id.
     * Returns List<Transaction> — demonstrates Collections usage.
     */
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.* FROM transactions t " +
                "JOIN accounts a ON t.account_id = a.account_id " +
                "WHERE a.account_number = ? " +
                "ORDER BY t.transaction_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }
        return transactions;
    }

    // ── Get transactions by account ID ──────────────────────────────

    public List<Transaction> getTransactionsByAccountId(long accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? " +
                "ORDER BY transaction_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, accountId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }
        return transactions;
    }

    // ── Generate transaction reference ──────────────────────────────

    /**
     * Generates a unique transaction reference in the format:
     * TXN-YYYYMMDD-NNNNNN
     *
     * Example: TXN-20260920-000003
     */
    public String generateTransactionReference() throws SQLException {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "TXN-" + today + "-";

        String sql = "SELECT MAX(transaction_reference) FROM transactions " +
                "WHERE transaction_reference LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prefix + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getString(1) != null) {
                    String lastRef = rs.getString(1);
                    // Extract the sequence number after the last hyphen
                    String seqStr = lastRef.substring(lastRef.lastIndexOf('-') + 1);
                    int nextSeq = Integer.parseInt(seqStr) + 1;
                    return prefix + String.format("%06d", nextSeq);
                }
            }
        }
        return prefix + "000001"; // first transaction of the day
    }

    // ── Dashboard statistics ────────────────────────────────────────

    public int getTransactionCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getTransactionCountByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM transactions WHERE status = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    // ── Helper: map ResultSet to Transaction ────────────────────────

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction txn = new Transaction();
        txn.setTransactionId(rs.getLong("transaction_id"));
        txn.setTransactionReference(rs.getString("transaction_reference"));
        txn.setAccountId(rs.getLong("account_id"));

        long relatedId = rs.getLong("related_account_id");
        txn.setRelatedAccountId(rs.wasNull() ? null : relatedId);

        txn.setEmployeeId(rs.getLong("employee_id"));
        txn.setTransactionType(rs.getString("transaction_type"));
        txn.setAmount(rs.getDouble("amount"));
        txn.setStatus(rs.getString("status"));
        txn.setDescription(rs.getString("description"));
        txn.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
        return txn;
    }
}
