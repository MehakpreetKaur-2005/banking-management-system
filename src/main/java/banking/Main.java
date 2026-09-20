package banking;

import banking.service.BankingService;
import banking.ui.LoginFrame;

import javax.swing.*;

/**
 * Entry point for the Banking Management System.
 *
 * Initializes the BankingService (which wires up the DAOs)
 * and launches the Swing GUI starting with the LoginFrame.
 */
public class Main {

    public static void main(String[] args) {
        // Run UI creation on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for a better native appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Ignore and use default look and feel if system one fails
                System.out.println("Could not set System LookAndFeel. Using default.");
            }

            // Initialize the main service which holds the business logic
            BankingService bankingService = new BankingService();

            // Launch the login screen
            LoginFrame loginFrame = new LoginFrame(bankingService);
            loginFrame.setVisible(true);
        });
    }
}
