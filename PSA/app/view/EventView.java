package app.view;

import app.control.PrivilegeType;
import app.control.interfaces.CRUDCategoryInterface;
import app.control.interfaces.PrivilegeTypeInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import app.control.interfaces.CRUDListSymposiumsInterface;

public class EventView {
    private final Label lblSearch = new Label("Procurar: ");
    private final Label lblCategory = new Label("Categoria: ");
    private final Label lblDate = new Label("Data: ");
    private final TextField tfSearch = new TextField();
    private final ObservableList<String> dataCategory = FXCollections.observableArrayList();
    private final ComboBox cbCategory = new ComboBox(dataCategory);
    private final DatePicker dpDate = new DatePicker(); 
    private final RadioButton rbOpened = new RadioButton("Abertos");
    private final RadioButton rbClosed = new RadioButton("Encerrados");
    private final BorderPane borderPane = new BorderPane();
    private final Button btnConfirm = new Button("Pesquisar");
    private final VBox vbox = new VBox(2);
    private final VBox vbox1 = new VBox(10);
    private final HBox hbox = new HBox(2);
    private final TextField pgCurrent = new TextField();
    private final Button prev = new Button("<<");
    private final Button next = new Button(">>");
    
    private int pg = 1;
    
    private CRUDCategoryInterface categories;
    private CRUDListSymposiumsInterface symposiums;
    private PrivilegeTypeInterface privilege;
    
    public EventView(CRUDCategoryInterface categories, CRUDListSymposiumsInterface symposiums, PrivilegeTypeInterface privilege) {
        this.categories = categories;
        this.symposiums = symposiums;
        this.privilege = privilege;
        setPositions();
        setLabels();
        setFields();
        setComboBoxes();
        setRadioButton();
        setButtons();
        
        borderPane.setTop(hbox);
        
        setCenter();
        setBottom();
        
        borderPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, BorderStrokeStyle.SOLID,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
    
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
        dataCategory.addAll(categories.getAllCategories());
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
        
        vbox.setPadding(new Insets(0, 0, 20, 15));
        
        hbox.setBorder(new Border(new BorderStroke(Color.WHITE, Color.WHITE, Color.BLACK, Color.WHITE, 
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        
        if(privilege.getPrivilegeType() == PrivilegeType.BOARD) {
            Button btnNew = new Button("Novo...");
            btnNew.setFont(Font.font("Segoe UI", 15));
            btnNew.setCursor(Cursor.HAND);
            hbox.getChildren().add(btnNew);
            HBox.setMargin(btnNew, new Insets(15, 0, 0, 10));
        }
    }
    
    private void setCenter() {
        ScrollPane scrollPane = new ScrollPane();
        vbox1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        setPage();
        
        scrollPane.setContent(vbox1);
        
        vbox1.prefWidthProperty().bind(scrollPane.widthProperty().subtract(15));
        
        borderPane.setCenter(scrollPane);
        
        BorderPane.setMargin(vbox1, new Insets(10, 0, 0, 0));
    }
    
    private void setPage() {
        vbox1.getChildren().clear();
        for(String[] a : symposiums.listSymposium(pg)){
            StackPane stackPane = new StackPane();
            Label txtTitulo = new Label(a[0]);
            Label txtTeste = new Label(a[1]);
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            
            vbox.getChildren().add(txtTitulo);
            
            if(privilege.getPrivilegeType() == PrivilegeType.BOARD) {
                HBox hbox1 = new HBox(10);
                Button edit = new Button("Editar");
                Button del = new Button("X");

                edit.setCursor(Cursor.HAND);
                del.setCursor(Cursor.HAND);

                hbox1.getChildren().addAll(edit, del);
                hbox1.setVisible(false);

                stackPane.setOnMouseEntered(e -> {
                    hbox1.setVisible(true);
                });

                stackPane.setOnMouseExited(e -> {
                  hbox1.setVisible(false);
                });
                  
                hbox1.setAlignment(Pos.CENTER_RIGHT);
                vbox.getChildren().add(hbox1);
            }

            txtTitulo.setCursor(Cursor.HAND);
            txtTitulo.setOnMouseClicked(e -> {
                // TODO: chamar tela de simposio
                System.out.println("Simpósio");
            });

            txtTitulo.setFont(Font.font("Segoe UI", 20));
            txtTeste.setFont(Font.font("Segoe UI", 15));

            hbox.getChildren().add(txtTeste);
            vbox.getChildren().add(hbox);

            hbox.setAlignment(Pos.CENTER_LEFT);
            vbox.setAlignment(Pos.CENTER);
            VBox.setMargin(hbox, new Insets(0, 0, 0, 10));
            stackPane.getChildren().add(vbox);

            stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.WHITE, Color.WHITE, Color.WHITE, 
                    BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

            stackPane.setPadding(new Insets(10, 0, 0, 0));
            stackPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            
          
            
            vbox1.getChildren().add(stackPane);
        }
            
    }
    
    private boolean isNumeric(String str){
        for(char a: str.toCharArray()) {
            if(!Character.isDigit(a)) return false;
        }
        return true;
    }
    
    private void setBottom() {
        HBox hbox1 = new HBox(5);
        pgCurrent.setText(Integer.toString(pg));
        pgCurrent.prefWidthProperty().set(30);
        prev.prefHeightProperty().set(20);
        next.prefHeightProperty().set(20);
        
        pgCurrent.setOnAction(e->{
            if(isNumeric(pgCurrent.getText())) {
                int temp = Integer.parseInt(pgCurrent.getText());
                if(temp > 0 && temp <= symposiums.numberPages()) {
                    pg = temp;
                    setPage();
                } else {
                    pgCurrent.setText(Integer.toString(pg));
                }
            } else {
                pgCurrent.setText(Integer.toString(pg));
            }
        });
        
        next.setOnAction(e->{
            if(pg < symposiums.numberPages()) {
                pg++;
                setPage();
                pgCurrent.setText(Integer.toString(pg));
            }
        });
        
        prev.setOnAction(e->{
            if(pg > 1) {
                pg--;
                setPage();
                pgCurrent.setText(Integer.toString(pg));
            }
        });
        
        hbox1.getChildren().addAll(prev, pgCurrent, new Label("/ " + symposiums.numberPages()), next);
        hbox1.setAlignment(Pos.CENTER);
        borderPane.setBottom(hbox1);
    }
}
