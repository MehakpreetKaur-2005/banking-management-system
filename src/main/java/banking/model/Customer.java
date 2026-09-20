package banking.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a bank customer.
 *
 * Demonstrates:
 * - Constructors (no-arg + parameterized)
 * - toString() override for readable output
 * - equals() based on customerCode (stable business identifier)
 * - hashCode() consistent with equals()
 */
public class Customer {

    private long customerId;
    private String customerCode;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ── No-arg constructor ──────────────────────────────────────────
    public Customer() {
    }

    // ── Parameterized constructor (for new customer registration) ──
    public Customer(String customerCode, String firstName, String lastName,
                    LocalDate dateOfBirth, String gender, String email,
                    String phone, String address, String city,
                    String state, String postalCode) {
        this.customerCode = customerCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.status = "ACTIVE";
    }

    // ── Getters and Setters ─────────────────────────────────────────

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ── Convenience method ──────────────────────────────────────────

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ── Object class method overrides ───────────────────────────────

    /**
     * Returns a human-readable string representation of this customer.
     * Useful for logging, debugging, and display.
     */
    @Override
    public String toString() {
        return "Customer{" +
                "customerCode='" + customerCode + '\'' +
                ", name='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * Two customers are considered equal if they have the same customerCode.
     * customerCode is a stable business identifier (CUST-XXXXXX).
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer other = (Customer) obj;
        return Objects.equals(customerCode, other.customerCode);
    }

    /**
     * hashCode is consistent with equals — based on customerCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerCode);
    }
}
