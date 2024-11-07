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

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3307/luecine?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";  // Replace with your MySQL username
    private static final String DB_PASSWORD = "root";  // Replace with your MySQL password

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
        // Retrieve software_id from request parameters
        String softwareIdParam = request.getParameter("software_id");
        
        // Check if the software_id is null or empty
        if (softwareIdParam == null || softwareIdParam.isEmpty()) {
            response.sendRedirect("requestAccess.jsp?error=Software selection is required");
            return;  // Exit the method to prevent further processing
        }

        // Parse the software_id to integer
        int softwareId;
        try {
            softwareId = Integer.parseInt(softwareIdParam);
        } catch (NumberFormatException e) {
            // Handle invalid format error
            response.sendRedirect("requestAccess.jsp?error=Invalid software ID");
            return;
        }

        // Retrieve other parameters (access_type, reason)
        String accessType = request.getParameter("access_type");
        String reason = request.getParameter("reason");

        // Get user ID from session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int userId = 0;

        try (Connection conn = connectToDatabase()) {
            // Fetch user ID from username
            String query = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }

            // Insert access request into the database
            String insertQuery = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, softwareId);
                stmt.setString(3, accessType);
                stmt.setString(4, reason);
                stmt.executeUpdate();
            }

            response.sendRedirect("requestAccess.jsp?message=Request submitted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("requestAccess.jsp?error=Database error: " + e.getMessage());
        }
    }
}
