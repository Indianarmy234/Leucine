package leucine_webproject2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // Import ResultSet here
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3307/luecine?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "root"; 

    // Method to connect to the database
    private Connection connectToDatabase() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    // Method to handle POST request for user sign-up
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = "Employee"; // Default role is "Employee"

        // Validate input
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("signup.jsp?error=Username or password cannot be empty");
            return;
        }

        // Hash the password using the hashPassword method
        String hashedPassword = hashPassword(password);

        try (Connection conn = connectToDatabase()) {
            // Check if the username already exists
            String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();  // Make sure ResultSet is imported

                if (rs.next() && rs.getInt(1) > 0) {
                    // Username already exists
                    response.sendRedirect("signup.jsp?error=Username already exists");
                    return;
                }
            }

            // Insert the new user into the users table
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);  // Store the hashed password
                stmt.setString(3, role);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User created successfully.");
                } else {
                    System.out.println("Error: No rows affected. User not created.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Logs the stack trace to the console
            response.sendRedirect("signup.jsp?error=Database error: " + e.getMessage()); // Display detailed error to user
            return;
        }

        // Redirect to login page after successful sign-up
        response.sendRedirect("login.jsp");
    }

    // Method to hash the password securely using SHA-256
    private String hashPassword(String password) {
        try {
            // Generate a random salt
            java.security.SecureRandom random = new java.security.SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Convert the salt to a hex string
            String saltHex = bytesToHex(salt);

            // Hash the password with the salt using SHA-256
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            md.update(salt);  // Add the salt to the password
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert the hashed bytes to a hex string
            String hashedPassword = bytesToHex(hashedBytes);

            // Return the salt and hashed password together (separated by ":")
            return saltHex + ":" + hashedPassword;
        } catch (Exception e) {
            e.printStackTrace();
            return password;  // In case of error, just return the password as-is (not secure, but avoids crash)
        }
    }

    // Helper method to convert a byte array to a hex string
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}







