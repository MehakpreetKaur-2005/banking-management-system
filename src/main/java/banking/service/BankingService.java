package banking.service;

import banking.dao.AccountDAO;
import banking.dao.CustomerDAO;
import banking.dao.DatabaseConnection;
import banking.dao.TransactionDAO;
import banking.exception.*;
import banking.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central business logic / service layer.
 *
 * Implements TransactionOperations interface.
 *
 * Demonstrates:
 * - Interface implementation
 * - Polymorphism (BankAccount → SavingsAccount / CurrentAccount)
 * - Method overloading (deposit with/without description)
 * - Collections (List, Map)
 * - Exception handling (custom exceptions)
 * - Synchronized methods for thread safety (multithreading demo)
 *
 * Flow: Swing UI → BankingService → DAO → MySQL
 */
public class BankingService implements TransactionOperations {

    private final CustomerDAO customerDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    // Map<accountNumber, BankAccount> — demonstrates Map collection usage.
    // Used as an in-memory cache for recently accessed accounts.
    private final Map<String, BankAccount> accountCache = new HashMap<>();

    public BankingService() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // ════════════════════════════════════════════════════════════════
    // AUTHENTICATION
    // ════════════════════════════════════════════════════════════════

    /**
     * Authenticates an employee by username and password.
     * Returns the employee_id on success, or -1 on failure.
     *
     * Uses plain-text comparison for this college demo.
     */
    public long authenticateEmployee(String username, String password) throws SQLException {
        String sql = "SELECT employee_id FROM employees " +
                "WHERE username = ? AND password_hash = ? AND status = 'ACTIVE'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("employee_id");
                }
            }
        }
        return -1; // authentication failed
    }

    // ════════════════════════════════════════════════════════════════
    // CUSTOMER MANAGEMENT
    // ════════════════════════════════════════════════════════════════

    public void addCustomer(Customer customer) throws SQLException {
        // Generate customer code
        String code = customerDAO.generateCustomerCode();
        customer.setCustomerCode(code);
        customer.setStatus("ACTIVE");
        customerDAO.addCustomer(customer);
    }

    public Customer findCustomerByCode(String customerCode) throws SQLException {
        return customerDAO.findCustomerByCode(customerCode);
    }

    /**
     * Search customers by keyword.
     * Returns List<Customer> — demonstrates Collections usage.
     */
    public List<Customer> searchCustomers(String keyword) throws SQLException {
        return customerDAO.searchCustomers(keyword);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.updateCustomer(customer);
    }

    // ════════════════════════════════════════════════════════════════
    // ACCOUNT MANAGEMENT
    // ════════════════════════════════════════════════════════════════

    /**
     * Creates a new bank account.
     *
     * Demonstrates polymorphism:
     *   BankAccount account = new SavingsAccount(...); // or CurrentAccount
     *   accountDAO.addAccount(account); // works for both types
     */
    public BankAccount createAccount(long customerId, String accountType, double initialDeposit)
            throws SQLException, InvalidAmountException {

        // Validate initial deposit meets minimum balance
        if ("SAVINGS".equals(accountType) && initialDeposit < 1000.00) {
            throw new InvalidAmountException(
                    "Savings account requires minimum initial deposit of ₹1,000.");
        }
        if ("CURRENT".equals(accountType) && initialDeposit < 0) {
            throw new InvalidAmountException(
                    "Current account initial deposit cannot be negative.");
        }

        String accountNumber = accountDAO.generateAccountNumber();

        // Polymorphism: create the appropriate subclass
        BankAccount account;
        if ("SAVINGS".equals(accountType)) {
            account = new SavingsAccount(accountNumber, customerId, initialDeposit);
        } else {
            account = new CurrentAccount(accountNumber, customerId, initialDeposit);
        }

        accountDAO.addAccount(account);

        // Cache the account — Map<String, BankAccount>
        accountCache.put(accountNumber, account);

        return account;
    }

    /**
     * Finds an account by account number.
     * Returns SavingsAccount or CurrentAccount (polymorphism).
     */
    public BankAccount findAccount(String accountNumber) throws SQLException {
        return accountDAO.findAccountByNumber(accountNumber);
    }

    /**
     * Gets all accounts for a customer.
     * Returns List<BankAccount> — each element is SavingsAccount or CurrentAccount.
     */
    public List<BankAccount> getAccountsByCustomerId(long customerId) throws SQLException {
        return accountDAO.getAccountsByCustomerId(customerId);
    }

    public void closeAccount(String accountNumber)
            throws SQLException, InvalidAccountException {

        BankAccount account = accountDAO.findAccountByNumber(accountNumber);
        if (account == null) {
            throw new InvalidAccountException("Account not found: " + accountNumber);
        }
        if ("CLOSED".equals(account.getStatus())) {
            throw new InvalidAccountException("Account is already closed: " + accountNumber);
        }

        accountDAO.closeAccount(accountNumber);
        accountCache.remove(accountNumber);
    }

    // ════════════════════════════════════════════════════════════════
    // TRANSACTION OPERATIONS (Interface Implementation)
    // ════════════════════════════════════════════════════════════════

    /**
     * Deposit — Method Overloading Demo (without description).
     * Calls the main deposit method with an empty description.
     */
    public void deposit(String accountNumber, double amount, long employeeId)
            throws InvalidAccountException, InvalidAmountException, TransactionFailedException {
        deposit(accountNumber, amount, "", employeeId);
    }

    /**
     * Deposit money into an account.
     * Synchronized on the account number to prevent race conditions
     * when multiple threads access the same account (multithreading demo).
     */
    @Override
    public synchronized void deposit(String accountNumber, double amount,
                                     String description, long employeeId)
            throws InvalidAccountException, InvalidAmountException, TransactionFailedException {

        // Validate amount
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Find account (validates existence)
            BankAccount account = accountDAO.findAccountByNumber(accountNumber, conn);
            if (account == null) {
                throw new InvalidAccountException("Account not found: " + accountNumber);
            }
            if (!"ACTIVE".equals(account.getStatus())) {
                throw new InvalidAccountException("Account is not active: " + accountNumber);
            }

            // Update balance
            double newBalance = account.getBalance() + amount;
            accountDAO.updateBalance(accountNumber, newBalance, conn);

            // Record transaction
            String ref = transactionDAO.generateTransactionReference();
            Transaction txn = new Transaction(ref, account.getAccountId(), null,
                    employeeId, "DEPOSIT", amount, "SUCCESS",
                    description != null && !description.isEmpty() ? description : "Deposit");
            transactionDAO.saveTransaction(txn, conn);

            conn.commit();

        } catch (InvalidAccountException e) {
            rollback(conn);
            throw e;
        } catch (SQLException e) {
            rollback(conn);
            throw new TransactionFailedException("Deposit failed: " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Withdraw money from an account.
     * Checks the account-specific minimum balance rule using polymorphism:
     *   account.canWithdraw(amount) — calls getMinimumBalance() on the
     *   actual runtime type (SavingsAccount or CurrentAccount).
     */
    @Override
    public synchronized void withdraw(String accountNumber, double amount,
                                      String description, long employeeId)
            throws InvalidAccountException, InvalidAmountException,
                   InsufficientBalanceException, TransactionFailedException {

        // Validate amount
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Find account
            BankAccount account = accountDAO.findAccountByNumber(accountNumber, conn);
            if (account == null) {
                throw new InvalidAccountException("Account not found: " + accountNumber);
            }
            if (!"ACTIVE".equals(account.getStatus())) {
                throw new InvalidAccountException("Account is not active: " + accountNumber);
            }

            // Check balance — polymorphism: canWithdraw uses getMinimumBalance()
            // which returns 1000 for SavingsAccount or -5000 for CurrentAccount
            if (!account.canWithdraw(amount)) {
                // Record failed transaction
                String ref = transactionDAO.generateTransactionReference();
                Transaction failedTxn = new Transaction(ref, account.getAccountId(), null,
                        employeeId, "WITHDRAWAL", amount, "FAILED", "Insufficient balance");
                transactionDAO.saveTransaction(failedTxn, conn);
                conn.commit();

                throw new InsufficientBalanceException(
                        "Insufficient balance. Current: ₹" + String.format("%.2f", account.getBalance()) +
                        ", Withdrawal: ₹" + String.format("%.2f", amount) +
                        ", Minimum required: ₹" + String.format("%.2f", account.getMinimumBalance()));
            }

            // Update balance
            double newBalance = account.getBalance() - amount;
            accountDAO.updateBalance(accountNumber, newBalance, conn);

            // Record transaction
            String ref = transactionDAO.generateTransactionReference();
            Transaction txn = new Transaction(ref, account.getAccountId(), null,
                    employeeId, "WITHDRAWAL", amount, "SUCCESS",
                    description != null && !description.isEmpty() ? description : "Withdrawal");
            transactionDAO.saveTransaction(txn, conn);

            conn.commit();

        } catch (InvalidAccountException | InsufficientBalanceException e) {
            // These are already committed or don't need rollback
            closeConnection(conn);
            throw e;
        } catch (SQLException e) {
            rollback(conn);
            throw new TransactionFailedException("Withdrawal failed: " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Transfer money between two accounts (atomic operation).
     *
     * Uses JDBC transaction management:
     *   conn.setAutoCommit(false) → debit → credit → record → commit
     *   On failure: rollback (no partial transfer)
     *
     * Demonstrates:
     * - Database transaction control
     * - Polymorphism (source account balance check)
     * - Exception handling
     */
    @Override
    public synchronized void transfer(String fromAccountNumber, String toAccountNumber,
                                      double amount, long employeeId)
            throws InvalidAccountException, InvalidAmountException,
                   InsufficientBalanceException, TransactionFailedException {

        // Validate amount
        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be greater than zero.");
        }

        // Validate different accounts
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new InvalidAccountException("Cannot transfer to the same account.");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // ── Step 1: Validate source account ──────────────────────
            BankAccount sourceAccount = accountDAO.findAccountByNumber(fromAccountNumber, conn);
            if (sourceAccount == null) {
                throw new InvalidAccountException("Source account not found: " + fromAccountNumber);
            }
            if (!"ACTIVE".equals(sourceAccount.getStatus())) {
                throw new InvalidAccountException("Source account is not active: " + fromAccountNumber);
            }

            // ── Step 2: Validate destination account ─────────────────
            BankAccount destAccount = accountDAO.findAccountByNumber(toAccountNumber, conn);
            if (destAccount == null) {
                throw new InvalidAccountException("Destination account not found: " + toAccountNumber);
            }
            if (!"ACTIVE".equals(destAccount.getStatus())) {
                throw new InvalidAccountException("Destination account is not active: " + toAccountNumber);
            }

            // ── Step 3: Check source balance (polymorphism) ──────────
            if (!sourceAccount.canWithdraw(amount)) {
                // Record failed transaction
                String ref = transactionDAO.generateTransactionReference();
                Transaction failedTxn = new Transaction(ref, sourceAccount.getAccountId(),
                        destAccount.getAccountId(), employeeId, "TRANSFER", amount,
                        "FAILED", "Insufficient balance for transfer");
                transactionDAO.saveTransaction(failedTxn, conn);
                conn.commit();

                throw new InsufficientBalanceException(
                        "Insufficient balance for transfer. Current: ₹" +
                        String.format("%.2f", sourceAccount.getBalance()) +
                        ", Transfer: ₹" + String.format("%.2f", amount) +
                        ", Minimum required: ₹" + String.format("%.2f", sourceAccount.getMinimumBalance()));
            }

            // ── Step 4: Debit source ─────────────────────────────────
            double newSourceBalance = sourceAccount.getBalance() - amount;
            accountDAO.updateBalance(fromAccountNumber, newSourceBalance, conn);

            // ── Step 5: Credit destination ───────────────────────────
            double newDestBalance = destAccount.getBalance() + amount;
            accountDAO.updateBalance(toAccountNumber, newDestBalance, conn);

            // ── Step 6: Record transaction ───────────────────────────
            String ref = transactionDAO.generateTransactionReference();
            Transaction txn = new Transaction(ref, sourceAccount.getAccountId(),
                    destAccount.getAccountId(), employeeId, "TRANSFER", amount,
                    "SUCCESS", "Transfer from " + fromAccountNumber + " to " + toAccountNumber);
            transactionDAO.saveTransaction(txn, conn);

            // ── Step 7: Commit ───────────────────────────────────────
            conn.commit();

        } catch (InvalidAccountException | InsufficientBalanceException e) {
            closeConnection(conn);
            throw e;
        } catch (SQLException e) {
            rollback(conn);
            throw new TransactionFailedException("Transfer failed: " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    // ════════════════════════════════════════════════════════════════
    // TRANSACTION HISTORY
    // ════════════════════════════════════════════════════════════════

    /**
     * Gets transaction history for an account.
     * Returns List<Transaction> — demonstrates Collections usage.
     */
    public List<Transaction> getTransactionHistory(String accountNumber) throws SQLException {
        return transactionDAO.getTransactionsByAccountNumber(accountNumber);
    }

    // ════════════════════════════════════════════════════════════════
    // DASHBOARD STATISTICS
    // ════════════════════════════════════════════════════════════════

    /**
     * Returns dashboard statistics as a Map<String, Integer>.
     * Demonstrates Map collection usage.
     */
    public Map<String, Integer> getDashboardStats() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalCustomers", customerDAO.getCustomerCount());
        stats.put("totalAccounts", accountDAO.getAccountCount());
        stats.put("savingsAccounts", accountDAO.getAccountCountByType("SAVINGS"));
        stats.put("currentAccounts", accountDAO.getAccountCountByType("CURRENT"));
        stats.put("activeAccounts", accountDAO.getAccountCountByStatus("ACTIVE"));
        stats.put("closedAccounts", accountDAO.getAccountCountByStatus("CLOSED"));
        stats.put("totalTransactions", transactionDAO.getTransactionCount());
        stats.put("successTransactions", transactionDAO.getTransactionCountByStatus("SUCCESS"));
        stats.put("failedTransactions", transactionDAO.getTransactionCountByStatus("FAILED"));
        return stats;
    }

    // ════════════════════════════════════════════════════════════════
    // COLLECTIONS DEMO HELPER
    // ════════════════════════════════════════════════════════════════

    /**
     * Loads all accounts into the in-memory cache.
     * Demonstrates Map<String, BankAccount> usage.
     */
    public Map<String, BankAccount> loadAccountCache(long customerId) throws SQLException {
        List<BankAccount> accounts = accountDAO.getAccountsByCustomerId(customerId);
        accountCache.clear();
        for (BankAccount acct : accounts) {
            accountCache.put(acct.getAccountNumber(), acct);
        }
        return new HashMap<>(accountCache);
    }

    // ════════════════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ════════════════════════════════════════════════════════════════

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
