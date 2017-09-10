package app.view;

import app.view.viewcontrollers.MaskField;
import app.Main;
import app.view.viewcontrollers.autocompletecheckcombobox.AutoCompleteComboBox;
import app.view.viewcontrollers.autocompletecheckcombobox.ComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEventView {
    private Stage stage;
    private final ScrollPane scrollPane = new ScrollPane();
    private final GridPane gridPane = new GridPane();
    
    public AddEventView() {
        stage = new Stage();
        eventPageOne();
    }
    
    private void eventPageOne() {
        stage.setTitle("Inserir dados do evento");
        Label lblTitle = new Label("Título:");
        TextField title = new TextField();
        Label lblDescription = new Label("Descrição:");
        TextArea description = new TextArea();
        Label lblOverview = new Label("Resumo:");
        TextArea overview = new TextArea();
        Label lblStartTime = new Label("Hora início:");
        DatePicker date = new DatePicker();
        TextField startTime = new TextField();
        Label lblDuration = new Label("Duração:");
        TextField duration = new TextField();
        Button btnNext = new Button("Avançar");
        btnNext.setOnAction(e -> {
            eventPageTwo();
        });
        Button btnCancel = new Button("Cancelar");
        
        btnNext.setCursor(Cursor.HAND);
        btnCancel.setCursor(Cursor.HAND);
        
        Main.setStyleTextField(title, description, overview);
        
        Main.setStyleLabeled(lblTitle, lblDescription, lblDuration, lblStartTime, lblOverview, btnNext, btnCancel);
        
        startTime.setFont(Font.font("Segoe UI", 12));
        duration.setFont(Font.font("Segoe UI", 12));
        date.setStyle("-fx-font-size:12;-fx-font-family:Segoe UI");
        overview.setPrefWidth(300);
        description.setPrefWidth(300);
        
        date.setPromptText("Data");
        duration.setPromptText("(em minutos)");
        startTime.setPromptText("00:00");
        
        startTime.setOnMouseClicked(e->{
            if(e.isPopupTrigger()) {
                startTime.setEditable(false);
                e.consume();
            }else {
                startTime.setEditable(true);
                e.consume();
            }
        });
        
        
        MaskField.numericField(duration);
        MaskField.timeField(startTime);
        MaskField.ignoreKeys(duration);
        MaskField.ignoreKeys(startTime);
        
        HBox hbox1 = new HBox(5);
        HBox hbox2 = new HBox(10);
        
        hbox1.getChildren().addAll(date, lblStartTime, startTime, lblDuration, duration);
        hbox2.getChildren().addAll(btnCancel, btnNext);
        
        hbox2.setAlignment(Pos.TOP_RIGHT);
        hbox2.setPadding(new Insets(20, 0, 0, 0));
        
        gridPane.add(lblTitle, 0, 0);
        gridPane.add(title, 1, 0);
        gridPane.add(lblDescription, 0, 1);
        gridPane.add(description, 1, 1);
        gridPane.add(lblOverview, 0, 2);
        gridPane.add(overview, 1, 2);
        gridPane.add(hbox1, 1, 3);
        gridPane.add(hbox2, 1, 4);
        
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        
        gridPane.setPadding(new Insets(10));
        
        scrollPane.setContent(gridPane);
        
        Main.setBackgroundWhite(gridPane, hbox1, scrollPane);
    }
    
    private void eventPageTwo(){
        stage.setTitle("Defina os avaliadores do projeto");
        
        
        ObservableList<CheckBox> evaluatorsNames = FXCollections.observableArrayList(new CheckBox("Matheus"),
                new CheckBox("Glayson"), new CheckBox("Hernando") , new CheckBox("Maria"),
                new CheckBox("Edimar"), new CheckBox("Xavier"));
        
        ComboBox<CheckBox> evaluators = new ComboBox<>(evaluatorsNames);
        evaluators.setPromptText("Avaliadores");
        AutoCompleteComboBox<CheckBox> teste = new AutoCompleteComboBox<>();
        
        FlowPane flowPane = new FlowPane();
        
        
        for(CheckBox avaliador : evaluators.getItems()){
            avaliador.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                Label a = new Label("*" + avaliador.getText());
                flowPane.getChildren().add(a);
            }else {
                for(int i = 0; i < flowPane.getChildren().size(); i++) {
                    if(((Label)flowPane.getChildren().get(i)).getText().contains(avaliador.getText())) {
                        flowPane.getChildren().remove(i);
                    }
                }
            }
    
            }
        });
        }
     
        Label lblEvaluators = new Label("Avaliadores selecionados:");
   
        AnchorPane anchorPane = new AnchorPane(); 
        teste.bindAutoComplete(evaluators);
        flowPane.setHgap(50);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setPrefWrapLength(150);
        AnchorPane.setTopAnchor(flowPane, Double.valueOf(30));
        
        
        anchorPane.getChildren().addAll(evaluators, flowPane);
        scrollPane.setContent(anchorPane);
    }
    
    public void addEventShow() {
        Scene scene = new Scene(scrollPane, 730, 600);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }
    
   
}
