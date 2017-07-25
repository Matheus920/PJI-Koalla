package application;

import application.view.Login;
import java.sql.Connection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;
    private static Connection con;
            
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Tutorial");
       
        if(!connectionCheck())
            Platform.exit();
        
        primaryStage.setScene(new Login().loginShow());
        primaryStage.show();
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Connection getConnection() {
        return con;
    }
    
    public boolean connectionCheck() {
        con = MySQLConnection.getConnection();
        if(con == null) {
            System.err.println("Connection not successful");
            return false;
        } else {
            System.out.println("Connection successfull");
            return true;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
