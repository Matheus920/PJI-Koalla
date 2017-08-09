package app.view;

import app.Main;
import javafx.beans.property.DoublePropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Event {
    private final Label lblSearch = new Label("Procurar: ");
    private final Label lblCategory = new Label("Categoria: ");
    private final Label lblDate = new Label("Data: ");
    private final TextField tfSearch = new TextField();
    private final ObservableList<String> dataCategory = FXCollections.observableArrayList(new Category().getDataCategory());
    private final ComboBox cbCategory = new ComboBox(dataCategory);
    private final DatePicker dpDate = new DatePicker(); 
    private final RadioButton rbOpened = new RadioButton("Abertos");
    private final RadioButton rbClosed = new RadioButton("Encerrados");
    private final BorderPane borderPane = new BorderPane();
    private final Button btnConfirm = new Button("Pesquisar");
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox vbox = new VBox(2);
    private final HBox hbox = new HBox(2);
    
    public Event() {
        
        setPositions();
        setLabels();
        setFields();
        setComboBoxes();
        setRadioButton();
        setButtons();
        
        borderPane.setTop(hbox);
        
        setCenter();
        
        borderPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.WHITE, Color.WHITE, Color.WHITE, BorderStrokeStyle.SOLID,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
    }
    
    public BorderPane getBorderPane() {
        return borderPane;
    }
    
    
    private void setLabels() {
        lblSearch.setFont(Font.font("Segoe UI", 12));
        
        
        lblCategory.setFont(Font.font("Segoe UI", 12));
        lblCategory.setPadding(new Insets(0, 0, 0, 10));
        
        lblDate.setFont(Font.font("Segoe UI", 12));
        lblDate.setPadding(new Insets(0, 0, 0, 10));
    }
    
    private void setComboBoxes() {
        dataCategory.add("");
    }
    
    private void setButtons() {
        btnConfirm.setFont(Font.font("Segoe UI", 15));
        btnConfirm.setCursor(Cursor.HAND);
    }
    
    private void setRadioButton() {
        ToggleGroup tg = new ToggleGroup();
        rbClosed.setToggleGroup(tg);
        rbOpened.setToggleGroup(tg);
    }
    
    private void setFields() { 
        tfSearch.setFont(Font.font("Segoe UI", 12));
        cbCategory.setPrefWidth(150);
        
    }
    
    private void setPositions(){
        
        vbox.getChildren().addAll(rbOpened, rbClosed);
        
        hbox.getChildren().addAll(lblSearch, tfSearch, lblCategory, cbCategory, lblDate,
                dpDate, vbox, btnConfirm);
        
        HBox.setMargin(lblSearch, new Insets(20, 0, 0, 20));
        HBox.setMargin(tfSearch, new Insets(15, 0, 0, 0));
        HBox.setMargin(lblCategory, new Insets(20, 0, 0, 0));
        HBox.setMargin(cbCategory, new Insets(15, 0, 0, 0));
        HBox.setMargin(lblDate, new Insets(20, 0, 0, 0));
        HBox.setMargin(dpDate, new Insets(15, 0, 0, 0));
        HBox.setMargin(vbox, new Insets(10, 0, 0 ,0));
        HBox.setMargin(btnConfirm, new Insets(15, 0, 0, 20));
        
        vbox.setPadding(new Insets(0, 0, 0 , 15));
    }
    
    private AnchorPane getAnchorPane() {
        AnchorPane anchorPane = new AnchorPane();
        Text txtTitulo = new Text("Dia especial das Abelhas");
        txtTitulo.setFont(Font.font("Segoe UI", 20));
        anchorPane.getChildren().add(txtTitulo);
        
        txtTitulo.layoutXProperty().bind(Main.getRoot().widthProperty().divide(2).subtract(100));
        txtTitulo.setLayoutY(40);
        return anchorPane;
    }
    
    private void setCenter() {
        ObservableList<AnchorPane> center = FXCollections.observableArrayList();
        center.add(getAnchorPane());
        VBox vbox1 = new VBox();
        vbox1.getChildren().add(center.get(0));
        borderPane.setCenter(vbox1);
    }
}
