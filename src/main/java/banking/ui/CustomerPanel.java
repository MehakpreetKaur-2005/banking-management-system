package banking.ui;

import banking.model.Customer;
import banking.service.BankingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Customer management panel.
 *
 * Provides:
 * - Add customer form
 * - Search customer by code/name/phone/email
 * - Display results in JTable
 */
public class CustomerPanel extends JPanel {

    private final BankingService bankingService;

    // Form fields
    private JTextField firstNameField, lastNameField, dobField, genderField;
    private JTextField emailField, phoneField, addressField;
    private JTextField cityField, stateField, postalCodeField;

    // Search
    private JTextField searchField;

    // Results table
    private JTable customerTable;
    private DefaultTableModel tableModel;

    public CustomerPanel(BankingService bankingService) {
        this.bankingService = bankingService;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ── Top: Add Customer Form ──────────────────────────────────
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Customer"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1
        addFormField(formPanel, gbc, 0, 0, "First Name:", firstNameField = new JTextField(12));
        addFormField(formPanel, gbc, 2, 0, "Last Name:", lastNameField = new JTextField(12));
        addFormField(formPanel, gbc, 4, 0, "DOB (YYYY-MM-DD):", dobField = new JTextField(10));

        // Row 2
        addFormField(formPanel, gbc, 0, 1, "Gender:", genderField = new JTextField(8));
        addFormField(formPanel, gbc, 2, 1, "Email:", emailField = new JTextField(15));
        addFormField(formPanel, gbc, 4, 1, "Phone:", phoneField = new JTextField(12));

        // Row 3
        addFormField(formPanel, gbc, 0, 2, "Address:", addressField = new JTextField(20));
        addFormField(formPanel, gbc, 2, 2, "City:", cityField = new JTextField(10));
        addFormField(formPanel, gbc, 4, 2, "State:", stateField = new JTextField(10));

        // Row 4
        addFormField(formPanel, gbc, 0, 3, "Postal Code:", postalCodeField = new JTextField(8));

        gbc.gridx = 4; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(e -> handleAddCustomer());
        formPanel.add(addButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // ── Middle: Search bar ──────────────────────────────────────
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> handleSearch());
        searchPanel.add(searchButton);

        JButton viewAllButton = new JButton("View All");
        viewAllButton.addActionListener(e -> handleViewAll());
        searchPanel.add(viewAllButton);

        add(searchPanel, BorderLayout.CENTER);

        // ── Bottom: Results table ───────────────────────────────────
        String[] columns = {"Code", "First Name", "Last Name", "Phone", "Email", "City", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only table
            }
        };
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setPreferredSize(new Dimension(900, 250));

        add(scrollPane, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc,
                              int x, int y, String label, JTextField field) {
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1; gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = x + 1; gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void handleAddCustomer() {
        // Validate required fields
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dobStr = dobField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String city = cityField.getText().trim();
        String state = stateField.getText().trim();
        String postalCode = postalCodeField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dobStr.isEmpty() ||
                phone.isEmpty() || address.isEmpty() || city.isEmpty() ||
                state.isEmpty() || postalCode.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate date format
        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date format. Use YYYY-MM-DD.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Customer customer = new Customer(
                    null, // code will be auto-generated
                    firstName, lastName, dob,
                    genderField.getText().trim(),
                    emailField.getText().trim(),
                    phone, address, city, state, postalCode
            );

            bankingService.addCustomer(customer);

            JOptionPane.showMessageDialog(this,
                    "Customer added successfully!\nCustomer Code: " + customer.getCustomerCode(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            clearForm();
            handleViewAll();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error adding customer: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a search keyword.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Customer> results = bankingService.searchCustomers(keyword);
            populateTable(results);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Search error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleViewAll() {
        try {
            List<Customer> customers = bankingService.getAllCustomers();
            populateTable(customers);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading customers: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateTable(List<Customer> customers) {
        tableModel.setRowCount(0);
        for (Customer c : customers) {
            tableModel.addRow(new Object[]{
                    c.getCustomerCode(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getPhone(),
                    c.getEmail(),
                    c.getCity(),
                    c.getStatus()
            });
        }
    }

    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("");
        genderField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        cityField.setText("");
        stateField.setText("");
        postalCodeField.setText("");
    }
}
