
package software_employee_office_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/office_employee_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Kichuhannu14#"; // <-- TYPE YOUR REAL MYSQL PASSWORD HERE

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL,USER, PASSWORD);
    }
}