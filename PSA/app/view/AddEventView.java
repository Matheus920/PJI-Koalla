package app.view;

import app.Main;
import app.control.interfaces.CRUDCategoryInterface;
import app.control.interfaces.CRUDCriteriaInterface;
import app.control.interfaces.CRUDEvaluatorInterface;
import app.view.viewcontrollers.MaskField;
import app.view.viewcontrollers.autocompletecheckcombobox.CheckComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEventView {
    private Stage stage;
    private final CRUDEvaluatorInterface evaluators;
    private final CRUDCriteriaInterface criteria;
    private final CRUDCategoryInterface categories;
    
    public AddEventView(CRUDEvaluatorInterface evaluators, CRUDCriteriaInterface criteria, CRUDCategoryInterface categories) {
        stage = new Stage();
        this.evaluators = evaluators;
        this.criteria = criteria;
        this.categories = categories;
        eventPageOne();
    }
    
    private void eventPageOne() {
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        stage.setTitle("Inserir dados do evento");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
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
        
        MaskField.ignoreKeys(duration);
        MaskField.ignoreKeys(startTime);
        MaskField.numericField(duration);
        MaskField.timeField(startTime);
     
        
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
        Scene scene = new Scene(scrollPane, 730, 600);
        stage.setScene(scene);
    }

    private String addEvaluatorView() {
        StackPane stackPane = new StackPane();
        ListView<String> list = new ListView<String>(FXCollections.observableArrayList(evaluators.getAllEvaluators()));
        stackPane.getChildren().addAll(list);
        final String[] result = {""};

        Scene scene = new Scene(stackPane, 400, 500);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setResizable(false);

        list.setOnMouseClicked(e -> {
            result[0] = list.getSelectionModel().getSelectedItem();
            newStage.close();
        });

        newStage.showAndWait();
        return result[0];
    }
    
    
    
    private void eventPageTwo(){
        StackPane stackPane = new StackPane();
        stage.setTitle("Defina os avaliadores do projeto");
        
        
        Button addEvaluator = new Button("*");
        Button addCriteria = new Button("*");

        Button next = new Button("Avançar");
        Button cancel = new Button("Cancelar");

        next.setCursor(Cursor.HAND);
        cancel.setCursor(Cursor.HAND);

        Label lblEvaluators = new Label("Lista de avaliadores:");
        CheckComboBox<String> ccbEvaluators = new CheckComboBox(FXCollections.observableArrayList(evaluators.getAllEvaluators()), "Avaliadores");
        ccbEvaluators.setPrefWidth(lengthBiggestString(ccbEvaluators.getCheckItems()) * 7);
        Label lblSelectedEvaluators = new Label("Avaliadores selecionados:");
        FlowPane flowPaneEvaluators = new FlowPane();

        for(CheckBox selectedEvaluator : ccbEvaluators.getItems()){
            selectedEvaluator.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                        Label a = new Label((char)0x2022 + selectedEvaluator.getText());
                        flowPaneEvaluators.getChildren().add(a);
                    }else {
                        for(int i = 0; i < flowPaneEvaluators.getChildren().size(); i++) {
                            if(((Label)flowPaneEvaluators.getChildren().get(i)).getText().contains(selectedEvaluator.getText())) {
                                flowPaneEvaluators.getChildren().remove(i);
                            }
                        }
                    }
                }
            });
        }

        Label lblCriteria = new Label("Lista de critérios:");
        CheckComboBox<String> ccbCriteria = new CheckComboBox(FXCollections.observableArrayList(criteria.getAllCriteria()), "Critérios");
        Label lblSelectedCriteria = new Label("Critérios selecionados:");
        FlowPane flowPaneCriteria = new FlowPane();
        
        for(CheckBox selectedCategory : ccbCriteria.getItems()){
            selectedCategory.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    Label a = new Label((char)0x2022 + selectedCategory.getText());
                    flowPaneCriteria.getChildren().add(a);
                }else {
                    for(int i = 0; i < flowPaneCriteria.getChildren().size(); i++) {
                        if(((Label)flowPaneCriteria.getChildren().get(i)).getText().contains(selectedCategory.getText())) {
                            flowPaneCriteria.getChildren().remove(i);
                        }
                    }
                }
            }
        });
        }
        
        flowPaneEvaluators.setHgap(50);
        flowPaneEvaluators.setVgap(10);
        flowPaneEvaluators.setOrientation(Orientation.VERTICAL);
        flowPaneEvaluators.setPrefWrapLength(150);
        
        flowPaneCriteria.setHgap(50);
        flowPaneCriteria.setVgap(10);
        flowPaneCriteria.setOrientation(Orientation.VERTICAL);
        flowPaneCriteria.setPrefWrapLength(150);

        HBox hbox1 = new HBox(lblEvaluators, ccbEvaluators, addEvaluator);
        hbox1.setSpacing(10);

        addEvaluator.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                evaluators.addEvaluator(addEvaluatorView());
                CheckComboBox<String> temp = new CheckComboBox(FXCollections.observableArrayList(evaluators.getAllEvaluators()), "Avaliadores");
                temp.setPrefWidth(lengthBiggestString(temp.getCheckItems()) * 15);
                hbox1.getChildren().set(1, temp);
                flowPaneEvaluators.getChildren().clear();
                
                for(CheckBox selectedEvaluator : temp.getItems()){
                    selectedEvaluator.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if(newValue){
                                HBox hboxTemp = new HBox();
                                Label a = new Label((char)0x2022 + selectedEvaluator.getText());
                                
                                
                                flowPaneEvaluators.getChildren().add(a);
                            }else {
                                for(int i = 0; i < flowPaneEvaluators.getChildren().size(); i++) {
                                    if(((Label)flowPaneEvaluators.getChildren().get(i)).getText().contains(selectedEvaluator.getText())) {
                                        flowPaneEvaluators.getChildren().remove(i);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        
        hbox1.setMargin(lblEvaluators, new Insets(0, 0, 0, 5));
        hbox1.setMargin(ccbEvaluators, new Insets(0, 0, 0, 0));

        ccbEvaluators.setPrefWidth(130);

        HBox hbox2 = new HBox(lblCriteria, ccbCriteria, addCriteria);
        ccbCriteria.setPrefWidth(lengthBiggestString(ccbCriteria.getCheckItems()) * 7);

        addCriteria.setOnAction(e->{
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
            stage.setTitle("Adicionar novo critério");
            
            GridPane gridPane = new GridPane();
            
            Label lblNewCriteria = new Label("Digite o novo critério: ");
            TextField tfNewCriteria = new TextField();
            
            Button confirm = new Button("Confirmar");
            Button cancel1 = new Button("Cancelar");
            
            HBox hbox4 = new HBox();
            
            hbox4.setSpacing(10);
            hbox4.getChildren().addAll(confirm, cancel1);
            hbox4.setAlignment(Pos.TOP_RIGHT);
            
            
            
            gridPane.add(lblNewCriteria, 0, 0);
            gridPane.add(tfNewCriteria, 1, 0);
            gridPane.add(hbox4, 1, 1);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(30);
            
            gridPane.setHgap(5);
            
            Main.setBackgroundWhite(gridPane);
            Scene scene = new Scene(gridPane, 400, 200);
            
            confirm.setOnAction(e1->{
                criteria.addCriteria(tfNewCriteria.getText());
                CheckComboBox<String> temp = new CheckComboBox(FXCollections.observableArrayList(criteria.getAllCriteria()), "Critérios");
                temp.setPrefWidth(lengthBiggestString(temp.getCheckItems()) * 7);
                hbox2.getChildren().set(1,temp);
                flowPaneCriteria.getChildren().clear();
                
                for(CheckBox selectedCriteria : temp.getItems()){
                    selectedCriteria.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if(newValue){
                                Label a = new Label((char)0x2022 + selectedCriteria.getText());
                                Label lblValue = new Label("Peso:");
                                TextField tfValue = new TextField();
                                tfValue.setPrefSize(30, 20);
                                
                                
                                MaskField.maxField(tfValue, 100);
                                MaskField.ignoreKeys(tfValue);
                                MaskField.numericField(tfValue);
                                HBox hBoxTemp = new HBox();
                                hBoxTemp.getChildren().addAll(a, lblValue, tfValue);
                                
                                hBoxTemp.setSpacing(15);
                                
                                flowPaneCriteria.getChildren().add(hBoxTemp);
                            }else {
                                for(int i = 0; i < flowPaneCriteria.getChildren().size(); i++) {
                                    if(((Label)flowPaneCriteria.getChildren().get(i)).getText().contains(selectedCriteria.getText())) {
                                        flowPaneCriteria.getChildren().remove(i);
                                    }
                                }
                            }
                        }
                    });
                }
                stage.close();
            });
            
            cancel1.setOnAction(e2->{
                stage.close();
            });
            
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            
            
            stage.showAndWait();
            
        });
        
        hbox2.setSpacing(10);
        
        next.setOnAction(e-> {
            eventPageThree();
        });
        
        HBox hbox3 = new HBox(next, cancel);
        hbox3.setSpacing(10);

        hbox1.setAlignment(Pos.TOP_CENTER);
        hbox2.setAlignment(Pos.TOP_CENTER);
        hbox3.setAlignment(Pos.BOTTOM_RIGHT);
        hbox3.setPadding(new Insets(20, 0, 0, 0));
        flowPaneCriteria.setAlignment(Pos.CENTER);
        flowPaneEvaluators.setAlignment(Pos.CENTER);


        flowPaneCriteria.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

        flowPaneEvaluators.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

        VBox vbox1 = new VBox(hbox1, lblSelectedEvaluators, flowPaneEvaluators, hbox2, lblSelectedCriteria,
        flowPaneCriteria, hbox3);

        vbox1.setSpacing(10);

        Main.setBackgroundWhite(vbox1);
        Main.setBackgroundWhite(stackPane);

        vbox1.setAlignment(Pos.TOP_CENTER);
        vbox1.setPadding(new Insets(10, 0, 0, 0));
        stackPane.getChildren().add(vbox1);
        Scene scene = new Scene(stackPane, 830, 600);
        stage.setScene(scene);
    }
    
    public void eventPageThree(){
        StackPane stackPane = new StackPane();
        
        Button save = new Button("Salvar");
        Button cancel = new Button("Voltar");
        
        HBox hbox5 = new HBox();
        
        hbox5.getChildren().addAll(save, cancel);
        
        hbox5.setSpacing(10);
        hbox5.setAlignment(Pos.TOP_RIGHT);
        hbox5.setPadding(new Insets(50, 10, 0, 0));
        
        
        stage.setTitle("Defina informações sobre local e palestrantes");
        
        Label lblPlace = new Label("Local: ");
        TextField tfPlace = new TextField();
        Label lblVacancies = new Label("Capacidade: ");
        TextField tfVacancies = new TextField();
        Label lblSpeakersVacancies = new Label("Quantidade de palestrantes: ");
        TextField tfSpeakersVacancies = new TextField();
        
        MaskField.ignoreKeys(tfVacancies);
        MaskField.numericField(tfVacancies);
        MaskField.ignoreKeys(tfSpeakersVacancies);
        MaskField.numericField(tfSpeakersVacancies);
        
        
        Label lblSpeakerName = new Label("Nome do palestrante: ");
        TextField tfSpeakerName = new TextField();
        
        VBox vbox = new VBox();
        VBox vbox1 = new VBox();
        HBox hbox2 = new HBox(lblPlace, tfPlace, lblVacancies, tfVacancies, lblSpeakersVacancies, tfSpeakersVacancies);
        
        hbox2.setSpacing(10);
        vbox1.setSpacing(10);
        vbox.setSpacing(10);
        
        vbox.setPadding(new Insets(10, 0, 0, 10));
       
        stackPane.getChildren().add(hbox2);
        
        Button newSpeaker = new Button("*");
        
        CheckBox invitedSpeaker = new CheckBox("Deseja adicionar um convidado?");
        
        HBox hbox1 = new HBox(lblSpeakerName, tfSpeakerName, newSpeaker);
        hbox1.setVisible(false);
        hbox1.setSpacing(10);
        vbox.getChildren().addAll(hbox2,invitedSpeaker, hbox1);
        
        
        invitedSpeaker.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue){
                if(newValue){
                    System.out.println(vbox1.getChildren().size());
                    hbox1.setVisible(true);
                    vbox.getChildren().add(vbox1);
           
                }
                else{
                    hbox1.setVisible(false);
                    vbox.getChildren().remove(vbox1);
                    ((TextField)hbox1.getChildren().get(1)).setText(null);
                    vbox1.getChildren().clear();
                }
                    
        }
    });
        

        
        newSpeaker.setOnAction(e-> {
            Label a = new Label("Nome do palestrante: ");
            TextField tfa = new TextField();
            HBox hboxA = new HBox(a, tfa);
            hboxA.setSpacing(10);
            
            
            if(vbox1.getChildren().size() < 4){
                vbox1.getChildren().add(hboxA);
            }
        });
        
        VBox vbox2 = new VBox();
        
        Label lblCategories = new Label("Defina as categorias: ");
        CheckComboBox<String> ccbCategories = new CheckComboBox(FXCollections.observableArrayList(categories.getAllCategories())
        , "Categorias");
        Label lblSelectedCategories = new Label("Categorias selecionadas: ");
        
        ccbCategories.setPrefWidth(lengthBiggestString(ccbCategories.getCheckItems()) * 11);
        
        HBox hbox3 = new HBox();
        Button addCategory = new Button("*");
        hbox3.setSpacing(5);
        hbox3.getChildren().addAll(lblCategories, ccbCategories, addCategory);
        
        vbox2.setSpacing(10);
        vbox2.setPadding(new Insets(10, 0, 0, 0));
        
        FlowPane fpCategories = new FlowPane();
        
        fpCategories.setAlignment(Pos.CENTER);
        fpCategories.setHgap(50);
        fpCategories.setVgap(10);
        fpCategories.setOrientation(Orientation.VERTICAL);
        fpCategories.setPrefWrapLength(150);


        fpCategories.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        
        hbox3.setAlignment(Pos.TOP_CENTER);
        vbox2.getChildren().addAll(hbox3, lblSelectedCategories, fpCategories);
        
         for(CheckBox selectedCategory : ccbCategories.getItems()){
            selectedCategory.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                        Label a = new Label((char)0x2022 + selectedCategory.getText());
                        fpCategories.getChildren().add(a);
                    }else {
                        for(int i = 0; i < fpCategories.getChildren().size(); i++) {
                            if(((Label)fpCategories.getChildren().get(i)).getText().contains(selectedCategory.getText())) {
                                fpCategories.getChildren().remove(i);
                            }
                        }
                    }
                }
            });
        }
        
        
        vbox2.setAlignment(Pos.CENTER);
        
        VBox vbox3 = new VBox();
        
        vbox3.getChildren().addAll(vbox, vbox2, hbox5);
        
        stackPane.getChildren().addAll(vbox3);
        Main.setBackgroundWhite(stackPane);

        
        addCategory.setOnAction(e->{
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
            stage.setTitle("Adicionar nova categoria");
            
            GridPane gridPane = new GridPane();
            
            Label lblNewCategory = new Label("Digite a nova categoria: ");
            TextField tfNewCategory = new TextField();
            
            Button confirm = new Button("Confirmar");
            Button cancel1 = new Button("Cancelar");
            
            HBox hbox4 = new HBox();
            
            hbox4.setSpacing(10);
            hbox4.getChildren().addAll(confirm, cancel1);
            hbox4.setAlignment(Pos.TOP_RIGHT);
            
            
            
            gridPane.add(lblNewCategory, 0, 0);
            gridPane.add(tfNewCategory, 1, 0);
            gridPane.add(hbox4, 1, 1);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(30);
            
            gridPane.setHgap(5);
            
            Main.setBackgroundWhite(gridPane);
            Scene scene = new Scene(gridPane, 400, 200);
            
            confirm.setOnAction(e1->{
                categories.addCategory(tfNewCategory.getText());
                CheckComboBox<String> temp = new CheckComboBox(FXCollections.observableArrayList(categories.getAllCategories()), "Categorias");
                temp.setPrefWidth(lengthBiggestString(temp.getCheckItems()) * 7);
                hbox3.getChildren().set(1,temp);
                fpCategories.getChildren().clear();
                
                for(CheckBox selectedCriteria : temp.getItems()){
                    selectedCriteria.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if(newValue){
                                Label a = new Label((char)0x2022 + selectedCriteria.getText());
                                fpCategories.getChildren().add(a);
                            }else {
                                for(int i = 0; i < fpCategories.getChildren().size(); i++) {
                                    if(((Label)fpCategories.getChildren().get(i)).getText().contains(selectedCriteria.getText())) {
                                        fpCategories.getChildren().remove(i);
                                    }
                                }
                            }
                        }
                    });
                }
                stage.close();
            });
            
            cancel1.setOnAction(e2->{
                stage.close();
            });
            
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            
            
            stage.showAndWait();
            
        });
        
        Scene scene = new Scene(stackPane, 830, 600);
        stage.setScene(scene);
    }
    
    public void addEventShow() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }
    
    public int lengthBiggestString(ObservableList<String> data) {
        int max = 0;
        for(String a : data) {
            if(a.length() > max) {
                max = a.length();
            }
        }
        return max;
    }
}
