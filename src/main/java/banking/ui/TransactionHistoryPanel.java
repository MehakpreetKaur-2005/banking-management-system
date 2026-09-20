package banking.ui;

import banking.model.Transaction;
import banking.service.BankingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Transaction history panel.
 *
 * Shows transactions for a given account number in a JTable.
 * Columns: Reference, Type, Amount, Status, Date/Time, Description
 */
public class TransactionHistoryPanel extends JPanel {

    private final BankingService bankingService;
    private JTextField accountField;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionHistoryPanel(BankingService bankingService) {
        this.bankingService = bankingService;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ── Top: Search bar ─────────────────────────────────────────
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.add(new JLabel("Account Number:"));
        accountField = new JTextField(20);
        searchPanel.add(accountField);

        JButton searchButton = new JButton("View History");
        searchButton.addActionListener(e -> handleSearch());
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // ── Center: Transaction table ───────────────────────────────
        String[] columns = {"Reference", "Type", "Amount (₹)", "Status", "Date/Time", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set column widths
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(160); // Reference
        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Type
        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Amount
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(70);  // Status
        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Date/Time
        transactionTable.getColumnModel().getColumn(5).setPreferredWidth(200); // Description

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void handleSearch() {
        String accountNumber = accountField.getText().trim();
        if (accountNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an account number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Transaction> transactions = bankingService.getTransactionHistory(accountNumber);

            tableModel.setRowCount(0);

            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No transactions found for account: " + accountNumber,
                        "No Results", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Transaction txn : transactions) {
                tableModel.addRow(new Object[]{
                        txn.getTransactionReference(),
                        txn.getTransactionType(),
                        String.format("%.2f", txn.getAmount()),
                        txn.getStatus(),
                        txn.getTransactionDate() != null
                                ? txn.getTransactionDate().format(formatter) : "",
                        txn.getDescription() != null ? txn.getDescription() : ""
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading transactions: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
