package banking.ui;

import banking.service.BankingService;
import banking.thread.TransactionProcessor;
import banking.model.BankAccount;

import javax.swing.*;
import java.awt.*;

/**
 * Transfer panel.
 *
 * Form: From Account, To Account, Amount
 * Also includes a "Multithreading Demo" button to demonstrate
 * concurrent transactions.
 */
public class TransferPanel extends JPanel {

    private final BankingService bankingService;
    private final long employeeId;
    private final DashboardFrame dashboard;

    private JTextField fromAccountField;
    private JTextField toAccountField;
    private JTextField amountField;

    public TransferPanel(BankingService bankingService, long employeeId, DashboardFrame dashboard) {
        this.bankingService = bankingService;
        this.employeeId = employeeId;
        this.dashboard = dashboard;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel title = new JLabel("Transfer Funds", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(title, gbc);

        gbc.gridwidth = 1;

        // From Account
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        add(new JLabel("From Account:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        fromAccountField = new JTextField(20);
        add(fromAccountField, gbc);

        // To Account
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        add(new JLabel("To Account:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        toAccountField = new JTextField(20);
        add(toAccountField, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        add(new JLabel("Amount (₹):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        amountField = new JTextField(20);
        add(amountField, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));

        JButton transferButton = new JButton("Transfer");
        transferButton.setPreferredSize(new Dimension(150, 35));
        transferButton.addActionListener(e -> handleTransfer());
        buttonPanel.add(transferButton);

        JButton threadDemoButton = new JButton("Multithreading Demo");
        threadDemoButton.setPreferredSize(new Dimension(180, 35));
        threadDemoButton.addActionListener(e -> handleMultithreadingDemo());
        buttonPanel.add(threadDemoButton);

        add(buttonPanel, gbc);
    }

    private void handleTransfer() {
        String fromAccount = fromAccountField.getText().trim();
        String toAccount = toAccountField.getText().trim();
        String amountStr = amountField.getText().trim();

        if (fromAccount.isEmpty() || toAccount.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Amount must be a valid number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            bankingService.transfer(fromAccount, toAccount, amount, employeeId);
            JOptionPane.showMessageDialog(this,
                    "Transfer successful!\n" +
                    "From: " + fromAccount + "\n" +
                    "To: " + toAccount + "\n" +
                    "Amount: ₹" + String.format("%.2f", amount),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            amountField.setText("");
            dashboard.refreshDashboard();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Transfer Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Demonstrates multithreading with concurrent withdrawals.
     *
     * Creates two threads that both try to withdraw from the same account.
     * The synchronized methods in BankingService ensure the balance
     * remains consistent.
     *
     * This is the key multithreading demonstration for the project.
     */
    private void handleMultithreadingDemo() {
        String accountNumber = fromAccountField.getText().trim();
        if (accountNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an account number in the 'From Account' field for the demo.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Show initial balance
        try {
            BankAccount account = bankingService.findAccount(accountNumber);
            if (account == null) {
                JOptionPane.showMessageDialog(this,
                        "Account not found: " + accountNumber,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double initialBalance = account.getBalance();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "MULTITHREADING DEMO\n\n" +
                    "Account: " + accountNumber + "\n" +
                    "Current Balance: ₹" + String.format("%.2f", initialBalance) + "\n\n" +
                    "This will start two concurrent threads:\n" +
                    "  Thread-1: Withdraw ₹2,000\n" +
                    "  Thread-2: Withdraw ₹3,000\n\n" +
                    "The synchronized keyword ensures the balance\n" +
                    "is updated safely with no race conditions.\n\n" +
                    "Proceed?",
                    "Multithreading Demo", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            // Create two concurrent transaction processors
            TransactionProcessor p1 = new TransactionProcessor(
                    bankingService, accountNumber, 2000, "WITHDRAW", employeeId);
            TransactionProcessor p2 = new TransactionProcessor(
                    bankingService, accountNumber, 3000, "WITHDRAW", employeeId);

            Thread t1 = new Thread(p1, "Thread-1");
            Thread t2 = new Thread(p2, "Thread-2");

            // Start both threads — they execute concurrently
            t1.start();
            t2.start();

            // Wait for both to finish
            t1.join();
            t2.join();

            // Show results
            BankAccount updatedAccount = bankingService.findAccount(accountNumber);
            double finalBalance = updatedAccount != null ? updatedAccount.getBalance() : 0;

            JOptionPane.showMessageDialog(this,
                    "MULTITHREADING DEMO — RESULTS\n\n" +
                    "Initial Balance: ₹" + String.format("%.2f", initialBalance) + "\n\n" +
                    p1.getResult() + "\n" +
                    p2.getResult() + "\n\n" +
                    "Final Balance: ₹" + String.format("%.2f", finalBalance) + "\n\n" +
                    "The synchronized methods ensured thread-safe access\n" +
                    "to the shared account balance.",
                    "Demo Results", JOptionPane.INFORMATION_MESSAGE);

            dashboard.refreshDashboard();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Demo error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
