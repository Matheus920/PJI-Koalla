package app;

import app.view.Header;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
    private static Stage primaryStage = new Stage();
    private static BorderPane root = new BorderPane();
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static BorderPane getRoot() {
        return root;
    }
    
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Portal de Simpósio Acadêmico");
        Scene scene = new Scene(root, 900, 700);
        
        root.setTop(new Header().headerShow("Evento", "Aqui vai a descrição do evento"));
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}