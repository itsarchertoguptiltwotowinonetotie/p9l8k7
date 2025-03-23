import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dba {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:oracle:thin:@Naren:1521:XE"; // Adjust as necessary
        String username = "system"; // Your DB username
        String password = "humaira146"; // Your DB password

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
