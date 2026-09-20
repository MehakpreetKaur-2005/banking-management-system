package banking.ui;

import banking.service.BankingService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

/**
 * Main application window — shown after successful login.
 *
 * Uses JTabbedPane to organize different banking functions:
 * - Customer Management
 * - Account Management
 * - Deposit
 * - Withdraw
 * - Transfer
 * - Transaction History
 *
 * Shows dashboard statistics at the top.
 */
public class DashboardFrame extends JFrame {

    private final BankingService bankingService;
    private final long employeeId;
    private final String username;
    private JLabel statsLabel;

    public DashboardFrame(BankingService bankingService, long employeeId, String username) {
        this.bankingService = bankingService;
        this.employeeId = employeeId;
        this.username = username;
        initializeUI();
        refreshDashboard();
    }

    private void initializeUI() {
        setTitle("Banking Management System — Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ── Top panel: welcome + stats + logout ─────────────────────
        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        statsLabel = new JLabel("Loading statistics...");
        statsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(statsLabel, BorderLayout.CENTER);

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshDashboard());
        topRight.add(refreshButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> handleLogout());
        topRight.add(logoutButton);

        topPanel.add(topRight, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ── Tabbed pane: all banking functions ──────────────────────
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 13));

        tabbedPane.addTab("Customers", new CustomerPanel(bankingService));
        tabbedPane.addTab("Accounts", new AccountPanel(bankingService, employeeId));
        tabbedPane.addTab("Deposit", new DepositPanel(bankingService, employeeId, this));
        tabbedPane.addTab("Withdraw", new WithdrawPanel(bankingService, employeeId, this));
        tabbedPane.addTab("Transfer", new TransferPanel(bankingService, employeeId, this));
        tabbedPane.addTab("Transaction History", new TransactionHistoryPanel(bankingService));

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Refreshes the dashboard statistics display.
     */
    public void refreshDashboard() {
        try {
            Map<String, Integer> stats = bankingService.getDashboardStats();
            String text = "Customers: " + stats.get("totalCustomers") +
                    "  |  Accounts: " + stats.get("totalAccounts") +
                    " (Savings: " + stats.get("savingsAccounts") +
                    ", Current: " + stats.get("currentAccounts") + ")" +
                    "  |  Transactions: " + stats.get("totalTransactions") +
                    " (✓ " + stats.get("successTransactions") +
                    ", ✗ " + stats.get("failedTransactions") + ")";
            statsLabel.setText(text);
        } catch (SQLException e) {
            statsLabel.setText("Could not load statistics.");
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame(bankingService).setVisible(true);
        }
    }
}
