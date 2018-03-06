package app.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionMySQL {
        
    public static Connection openConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/pji", "root", "311001");
        }catch(SQLException | ClassNotFoundException e) {
            new RuntimeException(e.getMessage());
        }
        return null;
    }
    
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            new RuntimeException(ex.getMessage());
        }
    }
}
