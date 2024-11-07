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

@WebServlet("/ApprovalServlet")
public class ApprovalServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/luecine?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "root";

    private Connection connectToDatabase() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestId = request.getParameter("requestId");
        String status = request.getParameter("status");

        // Check for null requestId or status
        if (requestId == null || status == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing requestId or status");
            return;
        }

        try (Connection conn = connectToDatabase()) {
            // Check if the requestId exists in the database
            String checkQuery = "SELECT COUNT(*) FROM requests WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, Integer.parseInt(requestId));
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    // Proceed with the update if requestId exists
                    String updateQuery = "UPDATE requests SET status = ? WHERE id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, status);
                        updateStmt.setInt(2, Integer.parseInt(requestId));

                        int rowsAffected = updateStmt.executeUpdate();

                        if (rowsAffected == 0) {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Request not found");
                            return;
                        }
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Request ID does not exist");
                    return;
                }
            }

        } catch (SQLException e) {
            // Log detailed exception message
            e.printStackTrace();  // Print stack trace to the server log for debugging
            System.out.println("SQL Error: " + e.getMessage());  // Detailed error message in the console
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred: " + e.getMessage());
            return;
        }

        // Redirect to the pending requests page after successful update
        response.sendRedirect("pendingRequests.jsp");
    }
}




