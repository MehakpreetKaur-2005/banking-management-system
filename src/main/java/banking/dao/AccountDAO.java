package banking.dao;

import banking.model.BankAccount;
import banking.model.CurrentAccount;
import banking.model.SavingsAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Account operations.
 *
 * Uses plain JDBC with PreparedStatement.
 *
 * Demonstrates polymorphism in findAccountByNumber() and getAccountsByCustomerId():
 *   Depending on account_type in the database, the method returns either a
 *   SavingsAccount or CurrentAccount object — both referenced as BankAccount.
 */
public class AccountDAO {

    // ── Add a new account ───────────────────────────────────────────

    public void addAccount(BankAccount account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, customer_id, account_type, balance, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, account.getAccountNumber());
            stmt.setLong(2, account.getCustomerId());
            stmt.setString(3, account.getAccountType());
            stmt.setDouble(4, account.getBalance());
            stmt.setString(5, account.getStatus());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    account.setAccountId(keys.getLong(1));
                }
            }
        }
    }

    // ── Find account by number ──────────────────────────────────────

    /**
     * Finds an account by its account number.
     *
     * Returns a SavingsAccount or CurrentAccount based on the account_type
     * column — this is a key demonstration of POLYMORPHISM.
     *
     * Usage:
     *   BankAccount acct = accountDAO.findAccountByNumber("1000000001");
     *   // acct could be SavingsAccount or CurrentAccount at runtime
     *   acct.getMinimumBalance(); // polymorphic call
     */
    public BankAccount findAccountByNumber(String accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccount(rs);
                }
            }
        }
        return null;
    }

    // ── Find account by number using an existing connection ────────
    // (Used during transfers to keep everything in one transaction)

    public BankAccount findAccountByNumber(String accountNumber, Connection conn) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccount(rs);
                }
            }
        }
        return null;
    }

    // ── Get accounts by customer ID ─────────────────────────────────

    /**
     * Returns all accounts for a customer as List<BankAccount>.
     * Each element is either a SavingsAccount or CurrentAccount — polymorphism.
     * Demonstrates Collections usage.
     */
    public List<BankAccount> getAccountsByCustomerId(long customerId) throws SQLException {
        List<BankAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ? ORDER BY account_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapResultSetToAccount(rs));
                }
            }
        }
        return accounts;
    }

    // ── Update balance ──────────────────────────────────────────────

    /**
     * Updates the account balance.
     * Accepts a Connection parameter so it can participate in a
     * database transaction (used during transfers).
     */
    public void updateBalance(String accountNumber, double newBalance, Connection conn)
            throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
        }
    }

    // ── Close account ───────────────────────────────────────────────

    public void closeAccount(String accountNumber) throws SQLException {
        String sql = "UPDATE accounts SET status = 'CLOSED', closed_at = NOW() " +
                "WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            stmt.executeUpdate();
        }
    }

    // ── Generate next account number ────────────────────────────────

    /**
     * Generates the next 10-digit account number.
     * Starting from 1000000001, matching the seed data pattern.
     */
    public String generateAccountNumber() throws SQLException {
        String sql = "SELECT MAX(account_number) FROM accounts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next() && rs.getString(1) != null) {
                long current = Long.parseLong(rs.getString(1));
                return String.valueOf(current + 1);
            }
        }
        return "1000000001"; // first account number
    }

    // ── Dashboard statistics ────────────────────────────────────────

    public int getAccountCount() throws SQLException {
        return getCount("SELECT COUNT(*) FROM accounts");
    }

    public int getAccountCountByType(String accountType) throws SQLException {
        String sql = "SELECT COUNT(*) FROM accounts WHERE account_type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getAccountCountByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM accounts WHERE status = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    // ── Helper: simple count query ──────────────────────────────────

    private int getCount(String sql) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    // ── Helper: map ResultSet to BankAccount (polymorphism) ─────────

    /**
     * Creates a SavingsAccount or CurrentAccount based on the account_type
     * column. The returned object is typed as BankAccount — polymorphism.
     */
    private BankAccount mapResultSetToAccount(ResultSet rs) throws SQLException {
        String accountType = rs.getString("account_type");
        BankAccount account;

        // Polymorphism: create the correct subclass based on account_type
        if ("SAVINGS".equals(accountType)) {
            account = new SavingsAccount();
        } else {
            account = new CurrentAccount();
        }

        account.setAccountId(rs.getLong("account_id"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setCustomerId(rs.getLong("customer_id"));
        account.setAccountType(accountType);
        account.setBalance(rs.getDouble("balance"));
        account.setStatus(rs.getString("status"));
        account.setOpenedAt(rs.getTimestamp("opened_at").toLocalDateTime());

        Timestamp closedAt = rs.getTimestamp("closed_at");
        if (closedAt != null) {
            account.setClosedAt(closedAt.toLocalDateTime());
        }

        account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return account;
    }
}
