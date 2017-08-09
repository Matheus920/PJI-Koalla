package app;

import app.view.Category;
import app.view.Event;
import app.view.Header;
import app.view.Menu;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
    private static Stage primaryStage = new Stage();
    private static BorderPane root = new BorderPane();
    private static final Header header = new Header("Evento", "Aqui vai a descrição do evento");
    
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
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
        
        Scene scene = new Scene(root, 1200, 750);
        
        root.setTop(header.headerShow());
        root.setLeft(new Menu().menuShow());
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void categoryShow(){
        header.setTitle("Categoria");
        header.setSubtitle("Lista de categorias que englobam os eventos");
        ListView center = new Category().showCategory();
        root.setCenter(center);
    }
    
    public static void eventShow() {
        root.setCenter(new Event().getBorderPane());
    } 
}
