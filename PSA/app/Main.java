package app;

import app.control.CRUDCategoryTest;
import app.control.CRUDCriteria;
import app.control.CRUDEvaluator;
import app.control.CRUDListSymposiumsTest;
import app.control.CRUDSymposiumTest;
import app.control.PrivilegiesTest;
import app.control.interfaces.PrivilegeTypeInterface;
import app.view.BoardView;
import app.view.CategoryView;
import app.view.CriteriaView;
import app.view.EvaluationView;
import app.view.EvaluatorView;
import app.view.EventView;
import app.view.HeaderView;
import app.view.MenuView;
import app.view.SpeakerView;
import app.view.SymposiumView;
import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
    private static Stage primaryStage = new Stage();
    private static BorderPane root = new BorderPane();
    private static HeaderView header;
    private static Scene scene;
    // TODO: tirar botão do comitê
    private static PrivilegiesTest test = new PrivilegiesTest(PrivilegeTypeInterface.BOARD);
    private static CRUDCategoryTest test1 = new CRUDCategoryTest();
    private static CRUDListSymposiumsTest test2 = new CRUDListSymposiumsTest();
    private static CRUDEvaluator test3 = new CRUDEvaluator();
    private static CRUDCriteria test4 = new CRUDCriteria();
    
    private static int width;
    private static int height;
    
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
        setup();
        setTop();
        setRight();
        setLeft();
        setBottom();
        primaryStage.show();
    }
    
    public static void categoryShow(){
        header.refresh();
        CategoryView category = new CategoryView(test1);
        header.setTitle(category);
        ListView center = category.getCategoryList();
        root.setTop(header.headerShow());
        root.setCenter(center);
    }
    
    public static void eventShow() {
        header.refresh();
        header.setTitleAndSubTitle("Evento", "Veja os eventos que estão acontecendo ou que já foram encerrados.");
        root.setTop(header.headerShow());
        root.setCenter(new EventView(test1, test2, test).eventShow());
    }
    
    public static void symposiumShow(String[] id) {
        CRUDSymposiumTest test3 = new CRUDSymposiumTest(id);
        SymposiumView sv = new SymposiumView(test3, test);
        HeaderView header1 = new HeaderView(test, id[0], null);
        root.setTop(header1.headerShow());
        root.setCenter(sv.symposiumShow());
        root.setBottom(sv.getBottom());
    }
    
    public static void boardShow() {
        header.refresh();
        header.setTitleAndSubTitle("Comitê", "Lista de membros do comitê acadêmico do IFSP");
        root.setTop(header.headerShow());
        root.setCenter(new BoardView(test).boardShow());
    }
    
    public static void speakerShow() {
        header.refresh();
        header.setTitleAndSubTitle("Palestrante", "Lista de palestrantes registrados");
        root.setTop(header.headerShow());
        root.setCenter(new SpeakerView(test).speakerShow());
    }
    
    public static void evaluationShow() {
        header.refresh();
        header.setTitleAndSubTitle("Avaliação", "Área para avaliar artigos pendentes");
        root.setTop(header.headerShow());
        root.setCenter(new EvaluationView().evaluationShow());
    }
    
    public static void evaluatorShow() {
        header.refresh();
        header.setTitleAndSubTitle("Avaliadores", "Avaliadores do IFSP");
        root.setTop(header.headerShow());
        root.setCenter(new EvaluatorView(test, test3, test1).evaluatorShow());
    }
    
    public static void criteriaShow(){
        CriteriaView criteria = new CriteriaView(test4);
        ListView criteria1 = criteria.getCriteriaList();
        header.setTitle(criteria);
        
        root.setTop(header.headerShow());
        root.setCenter(criteria1);
        
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
    
    public static int getScreenWidth() {
        return width;
    }
    
    public static int getScreenHeight() {
        return height;
    }
    
    private static void setup() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        width = d.width;
        height = d.height;
        
        scene = new Scene(root, width*0.8, height*0.8);
        primaryStage.setMinWidth(d.width*0.8-400);
        primaryStage.setMinHeight(400);
        primaryStage.setTitle("Portal de Simpósio Acadêmico");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("koala1.png")));
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(scene);
    }
    
    public static void setBackgroundWhite(Region... args) {
        for(Region a : args) {
            a.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
    
    public static void setStyleTextField(TextInputControl... args) {
        for(TextInputControl a : args) {
            a.setFont(Font.font("Segoe UI", 18));
        }
    }
    
    public static void setStyleLabeled(Labeled... args) {
        for(Labeled a : args) {
            a.setFont(Font.font("Segoe UI", 15));
        }
    }
}
