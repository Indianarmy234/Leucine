package leucine_webproject2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SoftwareServlet")
public class SoftwareServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/luecine?useSSL=false&serverTimezone=UTC";
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
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String accessLevels = request.getParameter("accessLevels");

        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            response.sendRedirect("createSoftware.jsp?error=Name or description cannot be empty");
            return;
        }

        try (Connection conn = connectToDatabase()) {
            String query = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, description);
                stmt.setString(3, accessLevels);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("createSoftware.jsp?error=Database error");
            return;
        }

        response.sendRedirect("createSoftware.jsp?success=Software added successfully");
    }
}


