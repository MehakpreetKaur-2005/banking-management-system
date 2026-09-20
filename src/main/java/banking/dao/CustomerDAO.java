package banking.dao;

import banking.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Customer operations.
 *
 * Uses plain JDBC with PreparedStatement.
 * Demonstrates Collections — returns List<Customer> for search results.
 */
public class CustomerDAO {

    // ── Add a new customer ──────────────────────────────────────────

    /**
     * Inserts a new customer into the database.
     * The generated customer_id is set back onto the Customer object.
     */
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (customer_code, first_name, last_name, " +
                "date_of_birth, gender, email, phone, address, city, state, postal_code, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, customer.getCustomerCode());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setDate(4, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(5, customer.getGender());
            stmt.setString(6, customer.getEmail());
            stmt.setString(7, customer.getPhone());
            stmt.setString(8, customer.getAddress());
            stmt.setString(9, customer.getCity());
            stmt.setString(10, customer.getState());
            stmt.setString(11, customer.getPostalCode());
            stmt.setString(12, customer.getStatus());

            stmt.executeUpdate();

            // Retrieve the auto-generated customer_id
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    customer.setCustomerId(keys.getLong(1));
                }
            }
        }
    }

    // ── Find by ID ──────────────────────────────────────────────────

    public Customer findCustomerById(long customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }
        }
        return null;
    }

    // ── Find by customer code ───────────────────────────────────────

    public Customer findCustomerByCode(String customerCode) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_code = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerCode);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }
        }
        return null;
    }

    // ── Search customers by name, phone, or email ───────────────────

    /**
     * Searches customers where name, phone, or email contains the keyword.
     * Returns a List<Customer> — demonstrates Java Collections usage.
     */
    public List<Customer> searchCustomers(String keyword) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE " +
                "first_name LIKE ? OR last_name LIKE ? OR phone LIKE ? OR email LIKE ? " +
                "ORDER BY customer_id";

        String pattern = "%" + keyword + "%";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            stmt.setString(4, pattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapResultSetToCustomer(rs));
                }
            }
        }
        return customers;
    }

    // ── Get all customers ───────────────────────────────────────────

    /**
     * Returns all customers as a List — demonstrates Collections usage.
     */
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY customer_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        }
        return customers;
    }

    // ── Update customer ─────────────────────────────────────────────

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, " +
                "date_of_birth = ?, gender = ?, email = ?, phone = ?, " +
                "address = ?, city = ?, state = ?, postal_code = ?, status = ? " +
                "WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getPhone());
            stmt.setString(7, customer.getAddress());
            stmt.setString(8, customer.getCity());
            stmt.setString(9, customer.getState());
            stmt.setString(10, customer.getPostalCode());
            stmt.setString(11, customer.getStatus());
            stmt.setLong(12, customer.getCustomerId());

            stmt.executeUpdate();
        }
    }

    // ── Generate next customer code ─────────────────────────────────

    /**
     * Generates the next customer code in the format CUST-XXXXXX.
     * Example: CUST-000001, CUST-000002, etc.
     */
    public String generateCustomerCode() throws SQLException {
        String sql = "SELECT MAX(customer_id) FROM customers";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            long nextId = 1;
            if (rs.next()) {
                nextId = rs.getLong(1) + 1;
            }
            return String.format("CUST-%06d", nextId);
        }
    }

    // ── Get customer count (for dashboard) ──────────────────────────

    public int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // ── Helper: map ResultSet row to Customer object ────────────────

    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getLong("customer_id"));
        customer.setCustomerCode(rs.getString("customer_code"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        customer.setGender(rs.getString("gender"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setState(rs.getString("state"));
        customer.setPostalCode(rs.getString("postal_code"));
        customer.setStatus(rs.getString("status"));
        customer.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        customer.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return customer;
    }
}
