package banking.ui;

import banking.exception.InvalidAmountException;
import banking.model.BankAccount;
import banking.model.Customer;
import banking.service.BankingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Account management panel.
 *
 * Provides:
 * - Create Savings or Current account for a customer
 * - Search account by number
 * - View account details
 * - Close account
 */
public class AccountPanel extends JPanel {

    private final BankingService bankingService;
    private final long employeeId;

    // Create account form
    private JTextField customerCodeField;
    private JComboBox<String> accountTypeCombo;
    private JTextField initialDepositField;

    // Search
    private JTextField searchAccountField;

    // Results
    private JTable accountTable;
    private DefaultTableModel tableModel;

    public AccountPanel(BankingService bankingService, long employeeId) {
        this.bankingService = bankingService;
        this.employeeId = employeeId;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ── Top: Create Account Form ────────────────────────────────
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Create New Account"));

        formPanel.add(new JLabel("Customer Code:"));
        customerCodeField = new JTextField(12);
        formPanel.add(customerCodeField);

        formPanel.add(new JLabel("Account Type:"));
        accountTypeCombo = new JComboBox<>(new String[]{"SAVINGS", "CURRENT"});
        formPanel.add(accountTypeCombo);

        formPanel.add(new JLabel("Initial Deposit (₹):"));
        initialDepositField = new JTextField(10);
        formPanel.add(initialDepositField);

        JButton createButton = new JButton("Create Account");
        createButton.addActionListener(e -> handleCreateAccount());
        formPanel.add(createButton);

        add(formPanel, BorderLayout.NORTH);

        // ── Middle: Search ──────────────────────────────────────────
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Account Number:"));
        searchAccountField = new JTextField(15);
        searchPanel.add(searchAccountField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> handleSearchAccount());
        searchPanel.add(searchButton);

        JButton closeAcctButton = new JButton("Close Account");
        closeAcctButton.addActionListener(e -> handleCloseAccount());
        searchPanel.add(closeAcctButton);

        add(searchPanel, BorderLayout.CENTER);

        // ── Bottom: Results table ───────────────────────────────────
        String[] columns = {"Account Number", "Type", "Balance (₹)", "Status", "Opened At"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        accountTable = new JTable(tableModel);
        accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.setPreferredSize(new Dimension(900, 300));

        add(scrollPane, BorderLayout.SOUTH);
    }

    private void handleCreateAccount() {
        String custCode = customerCodeField.getText().trim();
        String accountType = (String) accountTypeCombo.getSelectedItem();
        String depositStr = initialDepositField.getText().trim();

        if (custCode.isEmpty() || depositStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in Customer Code and Initial Deposit.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(depositStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Initial deposit must be a valid number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Verify customer exists
            Customer customer = bankingService.findCustomerByCode(custCode);
            if (customer == null) {
                JOptionPane.showMessageDialog(this,
                        "Customer not found: " + custCode,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BankAccount account = bankingService.createAccount(
                    customer.getCustomerId(), accountType, initialDeposit);

            JOptionPane.showMessageDialog(this,
                    "Account created successfully!\n" +
                    "Account Number: " + account.getAccountNumber() + "\n" +
                    "Type: " + account.getAccountType() + "\n" +
                    "Balance: ₹" + String.format("%.2f", account.getBalance()),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Show the customer's accounts
            showCustomerAccounts(customer.getCustomerId());
            customerCodeField.setText("");
            initialDepositField.setText("");

        } catch (InvalidAmountException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error creating account: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSearchAccount() {
        String accountNumber = searchAccountField.getText().trim();
        if (accountNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an account number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BankAccount account = bankingService.findAccount(accountNumber);
            if (account == null) {
                JOptionPane.showMessageDialog(this,
                        "Account not found: " + accountNumber,
                        "Not Found", JOptionPane.WARNING_MESSAGE);
                return;
            }

            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{
                    account.getAccountNumber(),
                    account.getAccountType(),
                    String.format("%.2f", account.getBalance()),
                    account.getStatus(),
                    account.getOpenedAt() != null ? account.getOpenedAt().toString() : ""
            });

            // Show detailed info including polymorphic displayAccountDetails()
            JOptionPane.showMessageDialog(this,
                    account.displayAccountDetails(),
                    "Account Details", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error searching account: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCloseAccount() {
        String accountNumber = searchAccountField.getText().trim();
        if (accountNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter the account number to close.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to close account " + accountNumber + "?",
                "Confirm Account Closure", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            bankingService.closeAccount(accountNumber);
            JOptionPane.showMessageDialog(this,
                    "Account " + accountNumber + " has been closed.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            handleSearchAccount(); // refresh display
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCustomerAccounts(long customerId) throws SQLException {
        List<BankAccount> accounts = bankingService.getAccountsByCustomerId(customerId);
        tableModel.setRowCount(0);
        for (BankAccount acct : accounts) {
            tableModel.addRow(new Object[]{
                    acct.getAccountNumber(),
                    acct.getAccountType(),
                    String.format("%.2f", acct.getBalance()),
                    acct.getStatus(),
                    acct.getOpenedAt() != null ? acct.getOpenedAt().toString() : ""
            });
        }
    }
}
