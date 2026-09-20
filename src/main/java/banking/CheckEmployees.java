package banking;

import banking.dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckEmployees {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT username, password_hash, status FROM employees")) {
            
            System.out.println("--- EMPLOYEES IN DATABASE ---");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("User: " + rs.getString("username") + 
                                   " | Pass: " + rs.getString("password_hash") + 
                                   " | Status: " + rs.getString("status"));
            }
            if (!found) {
                System.out.println("No employees found in the database. The table is empty!");
            }
            System.out.println("-----------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
