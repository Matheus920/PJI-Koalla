
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/user_database", "root", "");
        }catch(ClassNotFoundException e) {
            System.out.println("Driver not found");
            return null;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
