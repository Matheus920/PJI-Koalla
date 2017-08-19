package app;

import app.control.CRUDCategoryTest;
import app.control.CRUDListSymposiumsTest;
import app.control.CRUDSymposiumTest;
import app.control.PrivilegeType;
import app.control.PrivilegiesTest;
import app.view.CategoryView;
import app.view.EventView;
import app.view.HeaderView;
import app.view.MenuView;
import app.view.SymposiumView;
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
    private static HeaderView header;
    private static Scene scene = new Scene(root, 1200, 750);
    
    private static PrivilegiesTest test = new PrivilegiesTest(PrivilegeType.ADMIN);
    private static CRUDCategoryTest test1 = new CRUDCategoryTest();
    private static CRUDListSymposiumsTest test2 = new CRUDListSymposiumsTest();
    
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
        primaryStage.setMinWidth(800);
        setup();
        setTop();
        setRight();
        setLeft();
        setBottom();
        primaryStage.show();
    }
    
    public static void categoryShow(){
        CategoryView category = new CategoryView(test1);
        header.setTitle(category);
        ListView center = category.getCategoryList();
        root.setCenter(center);
    }
    
    public static void eventShow() {
        if(test.getPrivilegeType() == PrivilegeType.ADMIN) {
            header.refresh();
        }
        header.setTitleAndSubTitle("Evento", "Veja os eventos que estão acontecendo ou que já foram encerrados.");
        root.setCenter(new EventView(test1, test2, test).eventShow());
    }
    
    public static void symposiumShow(String[] id) {
        CRUDSymposiumTest test3 = new CRUDSymposiumTest(id);
        header.setTitleAndSubTitle(id[0], null);
        root.setCenter(new SymposiumView(test3).symposiumShow());
    }
    
    private static void setTop() {
        header = new HeaderView(test);
        root.setTop(header.headerShow());
    }
    
    private static void setRight() {
        
    }
    
    private static void setLeft() {
        root.setLeft(new MenuView(test).menuShow());
    }
    
    private static void setBottom(){
        
    }
    
    private static void setup() {
        primaryStage.setTitle("Portal de Simpósio Acadêmico");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("koala1.png")));
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(scene);
    }
}
