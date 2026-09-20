package banking.ui;

import banking.service.BankingService;

import javax.swing.*;
import java.awt.*;

/**
 * Deposit panel.
 *
 * Form: Account Number, Amount, Description (optional)
 * Validates input and shows success/failure messages.
 */
public class DepositPanel extends JPanel {

    private final BankingService bankingService;
    private final long employeeId;
    private final DashboardFrame dashboard;

    private JTextField accountField;
    private JTextField amountField;
    private JTextField descriptionField;

    public DepositPanel(BankingService bankingService, long employeeId, DashboardFrame dashboard) {
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
        JLabel title = new JLabel("Deposit Money", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(title, gbc);

        gbc.gridwidth = 1;

        // Account Number
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        add(new JLabel("Account Number:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        accountField = new JTextField(20);
        add(accountField, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        add(new JLabel("Amount (₹):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        amountField = new JTextField(20);
        add(amountField, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        descriptionField = new JTextField(20);
        add(descriptionField, gbc);

        // Deposit Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(new Dimension(150, 35));
        depositButton.addActionListener(e -> handleDeposit());
        add(depositButton, gbc);
    }

    private void handleDeposit() {
        String accountNumber = accountField.getText().trim();
        String amountStr = amountField.getText().trim();
        String description = descriptionField.getText().trim();

        // Validate
        if (accountNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an account number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an amount.",
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
            bankingService.deposit(accountNumber, amount, description, employeeId);
            JOptionPane.showMessageDialog(this,
                    "Deposit successful!\n" +
                    "Account: " + accountNumber + "\n" +
                    "Amount: ₹" + String.format("%.2f", amount),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear form and refresh dashboard
            amountField.setText("");
            descriptionField.setText("");
            dashboard.refreshDashboard();

        } catch (Exception e) {
            // Catches InvalidAccountException, InvalidAmountException,
            // TransactionFailedException — shows user-friendly message
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Deposit Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
