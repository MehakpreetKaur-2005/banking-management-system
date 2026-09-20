package banking.ui;

import banking.service.BankingService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Login screen — the entry point of the application.
 *
 * Employees enter username and password to authenticate.
 * On success, the Dashboard is shown.
 */
public class LoginFrame extends JFrame {

    private final BankingService bankingService;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame(BankingService bankingService) {
        this.bankingService = bankingService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Banking Management System — Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Main panel ──────────────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // ── Title ───────────────────────────────────────────────────
        JLabel titleLabel = new JLabel("Banking Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ── Form panel ──────────────────────────────────────────────
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ── Button panel ────────────────────────────────────────────
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 35));
        loginButton.addActionListener(e -> handleLogin());
        buttonPanel.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(120, 35));
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Allow Enter key to trigger login
        getRootPane().setDefaultButton(loginButton);

        add(mainPanel);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            long employeeId = bankingService.authenticateEmployee(username, password);
            if (employeeId > 0) {
                // Login successful — open dashboard
                dispose();
                new DashboardFrame(bankingService, employeeId, username).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
