package app.view;

import app.Main;
import app.control.CRUDCriteria;
import app.control.CRUDEvaluator;
import app.control.CRUDEvent;
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
import app.model.Category;
import app.model.Event;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

public class EventView {
    private final Label lblSearch = new Label("Procurar: ");
    private final Label lblCategory = new Label("Categoria: ");
    private final Label lblDate = new Label("Data: ");
    private final TextField tfSearch = new TextField();
    private final ObservableList<Category> dataCategory = FXCollections.observableArrayList();
    private final ComboBox<String> cbCategory = new ComboBox();
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
    private final Button clear = new Button("Limpar");
    private final ToggleGroup tg = new ToggleGroup();
    
    private int pg = 1;
    
    private CRUDCategoryInterface categories;
    private CRUDListSymposiumsInterface symposiums;
    private PrivilegeTypeInterface privilege;
    private CRUDEvaluator evaluators = new CRUDEvaluator();
    private CRUDCriteria criteriaCRUD = new CRUDCriteria();
    private CRUDEvent events = new CRUDEvent();

    
    public EventView(CRUDCategoryInterface categories, CRUDListSymposiumsInterface symposiums, PrivilegeTypeInterface privilege, CRUDEvent events) {
        this.categories = categories;
        this.symposiums = symposiums;
        this.privilege = privilege;
        this.events = events;
        Main.refreshBottom();
        setTop();
        setLabels();
        setFields();
        setComboBoxes();
        setRadioButton();
        setButtons();
        
        borderPane.setTop(hbox);
        
        setCenter();
        if(symposiums.getEventDataList().size() > 0){
            setBottom();
        }
        borderPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, BorderStrokeStyle.SOLID,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
    
    }
    
    public BorderPane eventShow() {
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
        dataCategory.add(new Category(""));
        for(Category a : dataCategory) {
            cbCategory.getItems().add(a.getNome());
        }
    }
    
    private void setButtons() {
        btnConfirm.setFont(Font.font("Segoe UI", 15));
        btnConfirm.setCursor(Cursor.HAND);
        btnConfirm.setOnAction(e-> {
            
            
            String toSearch = "";
            long toSearchCategories = -1;
            LocalDate toSearchDate = LocalDate.of(1976, 05, 03);
            Boolean toSearchStatus = false;
            
            if(tg.getSelectedToggle().equals(rbOpened)){
                toSearchStatus = true;
            }
            
            if(tfSearch.getText() == null || tfSearch.equals("")){
                toSearchCategories = dataCategory.get(cbCategory.getSelectionModel().getSelectedIndex()).getId();
                toSearchDate = dpDate.getValue();
            } else if((tfSearch.getText() == null || tfSearch.equals("")) && (dpDate.getValue() == null)){
                toSearchCategories = dataCategory.get(cbCategory.getSelectionModel().getSelectedIndex()).getId();
            } else if(dpDate.getValue() == null && cbCategory.getSelectionModel().isEmpty()){
                toSearch = tfSearch.getText();
            } else if(cbCategory.getSelectionModel().isEmpty()){
                toSearch = tfSearch.getText();
                toSearchDate = dpDate.getValue();
            }  else if(cbCategory.getSelectionModel().isEmpty() && (tfSearch.getText() == null || tfSearch.equals(""))){
                toSearchDate = dpDate.getValue();
            } else if(dpDate.getValue() == null){
                toSearch = tfSearch.getText();
                toSearchCategories = dataCategory.get(cbCategory.getSelectionModel().getSelectedIndex()).getId();
            }else if(!toSearchStatus){
                symposiums.setEventDataList(events.getAllClosedEvents());
                setCenter();
                if(symposiums.getEventDataList().size() > 0){
                    setBottom();
                }else{
                    borderPane.setBottom(null);
                }
            }else{
                toSearchDate = dpDate.getValue();
                toSearch = tfSearch.getText();
                toSearchCategories = dataCategory.get(cbCategory.getSelectionModel().getSelectedIndex()).getId();
            }
            
            
            if(events.searchByFields(toSearch, toSearchCategories, toSearchDate, toSearchStatus).size() > 0){
                symposiums.setEventDataList(events.searchByFields(toSearch, toSearchCategories, toSearchDate, toSearchStatus));
                setCenter();
                if(symposiums.getEventDataList().size() > 0){
                    setBottom();
                }else{
                    borderPane.setBottom(null);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nenhum evento foi encontrado.", ButtonType.CLOSE);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
       
                stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
                stage.showAndWait();
            }
        });
        
        clear.setFont(Font.font("Segoe UI", 15));
        clear.setCursor(Cursor.HAND);
        clear.setOnAction(e->{
            tfSearch.setText("");
            dpDate.setValue(null);
            cbCategory.getSelectionModel().clearSelection();
            tg.selectToggle(rbOpened);
            symposiums.setEventDataList(events.getAllOpenEvents());
            setCenter();
            if(symposiums.getEventDataList().size() > 0){
                setBottom();
            }else{
                borderPane.setBottom(null);
            }
        });
    }
    
    private void setRadioButton() {
        
        rbClosed.setToggleGroup(tg);
        rbOpened.setToggleGroup(tg);
        tg.selectToggle(rbOpened);
      
    }
    
    private void setFields() { 
        tfSearch.setFont(Font.font("Segoe UI", 12));
        cbCategory.setPrefWidth(150);
        List<String> titles = new ArrayList<>();
        
        for(String[] a : symposiums.listSymposium()) {
            titles.add(a[0]);
        }
        TextFields.bindAutoCompletion(tfSearch, titles);
    }
    
    private void setTop(){
        
        vbox.getChildren().addAll(rbOpened, rbClosed);
        
        hbox.getChildren().addAll(lblSearch, tfSearch, lblCategory, cbCategory, lblDate,
                dpDate, vbox, btnConfirm, clear);
        
        HBox.setMargin(lblSearch, new Insets(20, 0, 0, 20));
        HBox.setMargin(tfSearch, new Insets(15, 0, 0, 0));
        HBox.setMargin(lblCategory, new Insets(20, 0, 0, 0));
        HBox.setMargin(cbCategory, new Insets(15, 0, 0, 0));
        HBox.setMargin(lblDate, new Insets(20, 0, 0, 0));
        HBox.setMargin(dpDate, new Insets(15, 0, 0, 0));
        HBox.setMargin(vbox, new Insets(10, 0, 0 ,0));
        HBox.setMargin(btnConfirm, new Insets(15, 0, 0, 20));
        HBox.setMargin(clear, new Insets(15, 0, 0, 20));
        
        vbox.setPadding(new Insets(0, 0, 20, 15));
        
        hbox.setBorder(new Border(new BorderStroke(Color.WHITE, Color.WHITE, Color.BLACK, Color.WHITE, 
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.BOARD) {
            Button btnNew = new Button("Novo...");
            btnNew.setOnAction(e->{
                new AddEventView(evaluators, criteriaCRUD, categories, events).addEventShow();
            });
            
            btnNew.setFont(Font.font("Segoe UI", 15));
            btnNew.setCursor(Cursor.HAND);
            hbox.getChildren().add(btnNew);
            HBox.setMargin(btnNew, new Insets(15, 0, 0, 10));
        }
    }
    
    private void setCenter() {
        ScrollPane scrollPane = new ScrollPane();
        vbox1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        
        
        if(symposiums.getEventDataList().size() > 0){
            setPage();
            
            scrollPane.setContent(vbox1);
        
            vbox1.prefWidthProperty().bind(scrollPane.widthProperty().subtract(15));
        
            borderPane.setCenter(scrollPane);
 
        }else{
            borderPane.setCenter(new Label("Ops... Parece que no momento não há nenhum evento disponível!"));
        }
        
        BorderPane.setMargin(vbox1, new Insets(10, 0, 0, 0));
    }
    
    private void setPage() {
        vbox1.getChildren().clear();
        int i = 0;
        for(String[] a : symposiums.listSymposium(pg)){
            StackPane stackPane = new StackPane();
            Label lblTitle = new Label(a[0]);
            Label txtTest = new Label(a[1]);
            Label lblCategories = new Label("Categorias: ");
            Label txtCategories = new Label(a[2]);
            HBox hbox = new HBox();
            HBox hbox2 = new HBox();
            VBox vbox = new VBox();
            
            int j = i + ((pg-1)*10);
            
            vbox.getChildren().add(lblTitle);
            
            if(privilege.getPrivilegeType() == PrivilegeTypeInterface.BOARD) {
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
                
                edit.setOnAction(e->{
                    new AddEventView(evaluators, criteriaCRUD, categories, events).editEventShow(symposiums.getEventDataList().get(j));
                });
                
                del.setOnAction(e->{
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir o evento?", ButtonType.YES, ButtonType.NO);
                    alerta.setHeaderText(null);
                    alerta.setTitle("Atenção");
                    
                    Stage stage = (Stage)alerta.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
           
                    Optional<ButtonType> result = alerta.showAndWait();
                    
                    if(result.get() == ButtonType.YES){
                        events.deleteEvent(symposiums.getEventDataList().get(j));
                        stage.close();
                        Main.eventShow();
                    }else{
                        stage.close();
                    }
                });
                  
                hbox1.setAlignment(Pos.CENTER_RIGHT);
                vbox.getChildren().add(hbox1);
            }

            lblTitle.setFont(Font.font("Segoe UI", 20));
            txtTest.setFont(Font.font("Segoe UI", 15));
            
            lblCategories.setFont(Font.font("Segoe UI", 15));
            txtCategories.setFont(Font.font("Segoe UI", 15));

            hbox.getChildren().add(txtTest);
            hbox2.getChildren().addAll(lblCategories, txtCategories);
            vbox.getChildren().addAll(hbox, hbox2);

            vbox.setSpacing(10);
            
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox2.setAlignment(Pos.CENTER_LEFT);
            vbox.setAlignment(Pos.CENTER);
            VBox.setMargin(hbox, new Insets(0, 0, 0, 10));
            VBox.setMargin(hbox2, new Insets(0, 0, 0, 10));
            stackPane.getChildren().add(vbox);

            stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.WHITE, Color.WHITE, Color.WHITE, 
                    BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

            stackPane.setPadding(new Insets(10, 0, 0, 0));
            stackPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            
            vbox1.getChildren().add(stackPane);
            
            
            
            lblTitle.setCursor(Cursor.HAND);
            lblTitle.setOnMouseClicked(e -> {
                Main.symposiumShow(symposiums.getEventDataList().get(j));
            });
            i++;
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
