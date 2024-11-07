package leucine_webproject2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3307/luecine?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "root"; 

    private Connection connectToDatabase() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String redirectPage = "login.jsp";  // Default redirect page if login fails

        System.out.println("Attempting login with username: " + username); // Log the entered username

        try (Connection conn = connectToDatabase()) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password"); // Retrieve stored hashed password
                    String role = rs.getString("role");

                    // Log the stored password for debugging purposes
                    System.out.println("Stored Hashed Password: " + storedHashedPassword);
                    System.out.println("Entered Password (Plain): " + password); // Log the plain entered password

                    // Verify the password by hashing the input and comparing it with the stored hash
                    if (verifyPassword(password, storedHashedPassword)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        session.setAttribute("role", role);

                        // Redirect user based on role
                        switch (role) {
                            case "Employee":
                                redirectPage = "requestAccess.jsp";
                                break;
                            case "Manager":
                                redirectPage = "pendingRequests.jsp";
                                break;
                            case "Admin":
                                redirectPage = "createSoftware.jsp";
                                break;
                            default:
                                redirectPage = "login.jsp?error=Unknown role"; // Handle unexpected roles
                        }
                    } else {
                        redirectPage = "login.jsp?error=Invalid password";  // Invalid password
                    }
                } else {
                    redirectPage = "login.jsp?error=Invalid username";  // Invalid username
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            redirectPage = "login.jsp?error=Database error: " + e.getMessage();
        }

        response.sendRedirect(redirectPage);
    }

    // Method to verify password using the stored hashed password
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            // Hash the incoming password and compare it with the stored hash
            String hashedInputPassword = hashPassword(plainPassword);
            System.out.println("Hashed Input Password: " + hashedInputPassword); // Log hashed input password for comparison
            return hashedPassword.equals(hashedInputPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hashing the password
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return password;  // If hashing fails, return the plain password (shouldn't happen)
        }
    }
}
