package app.view;

import app.Main;
import app.control.interfaces.CRUDCategoryInterface;
import app.control.interfaces.CRUDCriteriaInterface;
import app.control.interfaces.CRUDEvaluatorInterface;
import app.control.interfaces.CRUDEventInterface;
import app.model.Category;
import app.model.Criteria;
import app.model.Evaluator;
import app.model.Event;
import app.view.viewcontrollers.MaskField;
import app.view.viewcontrollers.autocompletecheckcombobox.CheckComboBox;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
    private int[] oldCriteriaLength = new int[1];
    private int[] newCriteriaLength = new int[1];
    private int[] oldEvaluatorLength = new int[1];
    private int[] newEvaluatorLength = new int[1];
    private int[] oldCategoriesLength = new int[1];
    private int[] newCategoriesLength = new int[1];
    private final CRUDEvaluatorInterface evaluators;
    private final CRUDCriteriaInterface criteria;
    private final CRUDCategoryInterface categories;
    private final CRUDEventInterface events;
    private final Event event = new Event();
    private final Collection<Integer> criteriaIndexes = FXCollections.observableArrayList();
    private final Collection<Integer> evaluatorIndexes = FXCollections.observableArrayList();
    private final Collection<Integer> categoriesIndexes = FXCollections.observableArrayList();
    private final List<String> invitedSpeakersNames = FXCollections.observableArrayList();
    private CheckComboBox<String> tempCriteria;
    private CheckComboBox<String> tempEvaluators;
    private CheckComboBox<String> tempCategories;
    private final FlowPane flowPaneCriteria = new FlowPane();
   
    
    public AddEventView(CRUDEvaluatorInterface evaluators, CRUDCriteriaInterface criteria, CRUDCategoryInterface categories,
            CRUDEventInterface events) {
        stage = new Stage();
        this.evaluators = evaluators;
        this.criteria = criteria;
        this.categories = categories;
        this.events = events;
        eventPageOne();
    }
    
    private void eventPageOne() {
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        stage.setTitle("Inserir dados do evento");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
        Label lblOk = new Label("");
        Label lblTitle = new Label("Título:");
        TextField title = new TextField(event.getTitulo());
        Label lblDescription = new Label("Descrição:");
        TextArea description = new TextArea(event.getDescricao());
        Label lblOverview = new Label("Resumo:");
        TextArea overview = new TextArea(event.getResumo());
        Label lblStartTime = new Label("Hora início:");
        lblOk.setStyle("-fx-text-fill: red;");
        DatePicker date;
        TextField startTime;
        
        if(event.getDataInicio() != null){
            date = new DatePicker(event.getDataInicio().toLocalDate());
            startTime = new TextField(event.getDataInicio().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else{
            date = new DatePicker();
            startTime = new TextField();
        }
        Label lblDuration = new Label("Duração:");
        TextField duration = new TextField(Integer.toString(event.getDuracao()));
        Button btnNext = new Button("Avançar");

        MaskField.maxLength(title, 255);
        MaskField.maxLength(description, 1024);
        MaskField.maxLength(overview, 2048);
        
        
        
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
        
        
        
        gridPane.add(lblOk, 0, 0);
        gridPane.add(lblTitle, 0, 1);
        gridPane.add(title, 1, 1);
        gridPane.add(lblDescription, 0, 2);
        gridPane.add(description, 1, 2);
        gridPane.add(lblOverview, 0, 3);
        gridPane.add(overview, 1, 3);
        gridPane.add(hbox1, 1, 4);
        gridPane.add(hbox2, 1, 5);
        
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        GridPane.setColumnSpan(lblOk, GridPane.REMAINING);
        GridPane.setHalignment(lblOk, HPos.CENTER);
        gridPane.setPadding(new Insets(10));
        
        scrollPane.setContent(gridPane);
        
    
        btnNext.setOnAction(e -> {
            if(title.getText() == null || title.getText().equals("") ){
                lblOk.setText("O título está vazio");
            } else if(description.getText() == null || description.getText().equals("")){
                lblOk.setText("A descrição está vazia");
            }else if(overview.getText() == null || overview.getText().equals("")){
                lblOk.setText("O resumo está vazio");
            }else if((((TextField)date.getEditor()).getText() == null || ((TextField)date.getEditor()).getText().equals(""))){
                lblOk.setText("A data está vazia");
            }else if(startTime.getText() == null || startTime.getText().equals("") || startTime.getText().length() != 5){
                lblOk.setText("A hora é inválida");
            } else if(duration.getText() == null || duration.getText().equals("") || Integer.parseInt(duration.getText()) <= 0){
                lblOk.setText("A duração é inválida");
            }else if(date.getValue().isBefore(LocalDate.now())){
                lblOk.setText("A data é inválida");
            }else{
                eventPageTwo();
                event.setTitulo(title.getText());
                event.setResumo(overview.getText());
                event.setDuracao(Integer.parseInt(duration.getText()));
                event.setDataInicio(LocalDateTime.of(date.getValue(), LocalTime.parse(startTime.getText(), DateTimeFormatter.ofPattern("HH:mm"))));
                event.setDescricao(description.getText());
            }
        });
        
        
        Main.setBackgroundWhite(gridPane, hbox1, scrollPane);
        Scene scene = new Scene(scrollPane, 730, 600);
        stage.setScene(scene);
    }

    private Evaluator addEvaluatorView() {
        StackPane stackPane = new StackPane();
        
        ObservableList<Evaluator> teachersData = FXCollections.observableArrayList(evaluators.getOnlyTeachers());
        ObservableList<String> teachersDataString = FXCollections.observableArrayList();
        Evaluator[] result = {null};
        
        for(Evaluator a : teachersData){
            teachersDataString.add(a.getNome());
        }
        
        ListView<String> list = new ListView<String>(teachersDataString);
        list.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        stackPane.getChildren().addAll(list);

        Scene scene = new Scene(stackPane, 400, 500);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setResizable(false);

        list.setOnMouseClicked(e -> {
           int index = list.getSelectionModel().getSelectedIndex();
           result[0] = evaluators.getEvaluatorById(teachersData.get(index).getId());
           
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
        Button back = new Button("Voltar");

        next.setCursor(Cursor.HAND);
        back.setCursor(Cursor.HAND);

        Label lblEvaluators = new Label("Lista de avaliadores:");
        
        ObservableList<Evaluator> evaluatorsData = FXCollections.observableArrayList(evaluators.getAllEvaluators());
        ObservableList<String> evaluatorsDataString = FXCollections.observableArrayList();
        
        for(Evaluator a : evaluatorsData){
            evaluatorsDataString.add(a.getNome());
        }
        
        CheckComboBox<String> ccbEvaluators = new CheckComboBox(evaluatorsDataString, "Avaliadores");
        
        Label lblSelectedEvaluators = new Label("Avaliadores selecionados:");
        FlowPane flowPaneEvaluators = new FlowPane();
        
        if(!evaluatorIndexes.isEmpty()){
            ccbEvaluators.setSelectedIndexes(evaluatorIndexes);
            
            for(CheckBox selectedEvaluator : ccbEvaluators.getItems()){
                if(selectedEvaluator.isSelected()){
                    Label a = new Label((char)0x2022 + selectedEvaluator.getText());
                    flowPaneEvaluators.getChildren().add(a);
                }
            }
        }
        
        ccbEvaluators.setPrefWidth(lengthBiggestString(ccbEvaluators.getCheckItems()) * 7);


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
        
        Label lblSelectedCriteria = new Label("Critérios selecionados:");
        
        
        ObservableList<Criteria> criteriaData = FXCollections.observableArrayList(criteria.getAllCriteria());
        ObservableList<String> criteriaDataString = FXCollections.observableArrayList();
        
        for(Criteria a : criteriaData){
            criteriaDataString.add(a.getNome());
        }
        
        CheckComboBox<String> ccbCriteria = new CheckComboBox(criteriaDataString, "Critérios");
        
        if(!criteriaIndexes.isEmpty()){
            ccbCriteria.setSelectedIndexes(criteriaIndexes);
        }
        Label lblOk = new Label("");
        
        lblOk.setStyle("-fx-text-fill: red;");
        
        
         next.setOnAction(e-> {
            
            int soma = 0;
            
            for(int i = 0; i < flowPaneCriteria.getChildren().size(); i++){
                HBox hboxtemp = ((HBox)flowPaneCriteria.getChildren().get(i));
                TextField textField = ((TextField)hboxtemp.getChildren().get(2));
                if(textField.getText() == null || textField.getText().equals("")){
                    soma = 100000;
                }else{
                    soma += Integer.parseInt(textField.getText());
                }
            }  
            
            if(oldEvaluatorLength[0] == newEvaluatorLength[0] || newEvaluatorLength[0] == 0){
                if(ccbEvaluators.getSelectedIndexes().isEmpty()){
                    lblOk.setText("Selecione ao menos um avaliador.");
                } else if(soma != 100){
                    lblOk.setText("A soma dos pesos dos critérios deve ser 100.");
                } else{
                     if(oldEvaluatorLength[0] == newEvaluatorLength[0] || newEvaluatorLength[0] == 0){
                            evaluatorIndexes.clear();
                            evaluatorIndexes.addAll(ccbEvaluators.getSelectedIndexes());
                        }else{
                            evaluatorIndexes.clear();
                            evaluatorIndexes.addAll(tempEvaluators.getSelectedIndexes());
                        }

                        if(oldCriteriaLength[0] == newCriteriaLength[0] || newCriteriaLength[0] == 0){
                            criteriaIndexes.clear();
                            criteriaIndexes.addAll(ccbCriteria.getSelectedIndexes());
                        }else{
                            criteriaIndexes.clear();
                            criteriaIndexes.addAll(tempCriteria.getSelectedIndexes());
                        }
                        eventPageThree();
                }
            }else if(tempEvaluators.getSelectedIndexes().isEmpty()){
                lblOk.setText("Selecione ao menos um avaliador.");
            } else if(soma != 100){
                lblOk.setText("A soma dos pesos dos critérios deve ser 100.");
            } else{
                if(oldEvaluatorLength[0] == newEvaluatorLength[0] || newEvaluatorLength[0] == 0){
                            evaluatorIndexes.clear();
                            evaluatorIndexes.addAll(ccbEvaluators.getSelectedIndexes());
                        }else{
                            evaluatorIndexes.clear();
                            evaluatorIndexes.addAll(tempEvaluators.getSelectedIndexes());
                        }

                        if(oldCriteriaLength[0] == newCriteriaLength[0] || newCriteriaLength[0] == 0){
                            criteriaIndexes.clear();
                            criteriaIndexes.addAll(ccbCriteria.getSelectedIndexes());
                        }else{
                            criteriaIndexes.clear();
                            criteriaIndexes.addAll(tempCriteria.getSelectedIndexes());
                        }
                        eventPageThree();
                    }
        });
        
      
        
        for(CheckBox selectedCategory : ccbCriteria.getItems()){
            selectedCategory.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    Label a = new Label((char)0x2022 + selectedCategory.getText());
                    Label lblValue = new Label("Peso:");
                    TextField tfValue = new TextField();
                    tfValue.setPrefSize(50, 20);
                    MaskField.numericField(tfValue);
                    MaskField.maxField(tfValue, 100);
                    MaskField.ignoreKeys(tfValue);
                    HBox hBoxTempCriteria = new HBox();
                    hBoxTempCriteria.getChildren().addAll(a, lblValue, tfValue);

                    hBoxTempCriteria.setSpacing(15);

                    flowPaneCriteria.getChildren().add(hBoxTempCriteria);
                }else {
                    for(int i = 0; i < flowPaneCriteria.getChildren().size(); i++) {
                        HBox hboxTemp = ((HBox)flowPaneCriteria.getChildren().get(i));
                        Label lblTemp = ((Label)hboxTemp.getChildren().get(0));
                        if(lblTemp.getText().contains(selectedCategory.getText())) {
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

        oldEvaluatorLength[0] = evaluators.getAllEvaluators().size();
        
        
        addEvaluator.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                evaluators.turnEvaluator(addEvaluatorView());
                ObservableList<Evaluator> evaluatorsData1 = FXCollections.observableArrayList(evaluators.getAllEvaluators());
                ObservableList<String> evaluatorsDataString1 = FXCollections.observableArrayList();
        
                 for(Evaluator a : evaluatorsData1){
                    evaluatorsDataString1.add(a.getNome());
                }
                
                tempEvaluators = new CheckComboBox(evaluatorsDataString1, "Avaliadores");
                tempEvaluators.setPrefWidth(lengthBiggestString(tempEvaluators.getCheckItems()) * 15);
                hbox1.getChildren().set(1, tempEvaluators);
                flowPaneEvaluators.getChildren().clear();
                
                for(CheckBox selectedEvaluator : tempEvaluators.getItems()){
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
                newEvaluatorLength[0] = evaluators.getAllEvaluators().size();
                }
            }
            
            
        });
        
        hbox1.setMargin(lblEvaluators, new Insets(0, 0, 0, 5));
        hbox1.setMargin(ccbEvaluators, new Insets(0, 0, 0, 0));

        ccbEvaluators.setPrefWidth(130);

        HBox hbox2 = new HBox(lblCriteria, ccbCriteria, addCriteria);
        ccbCriteria.setPrefWidth(lengthBiggestString(ccbCriteria.getCheckItems()) * 11);
        oldCriteriaLength[0] = criteria.getAllCriteria().size();
        
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
            
            confirm.setCursor(Cursor.HAND);
            cancel1.setCursor(Cursor.HAND);
            
            tfNewCriteria.setOnAction(e1->{
                criteria.addCriteria(new Criteria(tfNewCriteria.getText()));
                
                ObservableList<Criteria> criteriaData1 = FXCollections.observableArrayList(criteria.getAllCriteria());
                ObservableList<String> criteriaDataString1 = FXCollections.observableArrayList();
        
                for(Criteria a : criteriaData1){
                    criteriaDataString1.add(a.getNome());
                }
                
                tempCriteria = new CheckComboBox(criteriaDataString1, "Critérios");
                tempCriteria.setPrefWidth(lengthBiggestString(tempCriteria.getCheckItems()) * 11);
                hbox2.getChildren().set(1,tempCriteria);
                flowPaneCriteria.getChildren().clear();
                
                for(CheckBox selectedCriteria : tempCriteria.getItems()){
                    selectedCriteria.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if(newValue){
                                Label a = new Label((char)0x2022 + selectedCriteria.getText());
                                Label lblValue = new Label("Peso:");
                                TextField tfValue = new TextField();
                                tfValue.setPrefSize(50, 20);
                                
                                
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
                newCriteriaLength[0] = criteria.getAllCriteria().size();
                
                stage.close();
            });
            

            

            
            confirm.setOnAction(e1->{
                criteria.addCriteria(new Criteria(tfNewCriteria.getText()));
                
                ObservableList<Criteria> criteriaData1 = FXCollections.observableArrayList(criteria.getAllCriteria());
                ObservableList<String> criteriaDataString1 = FXCollections.observableArrayList();
        
                for(Criteria a : criteriaData1){
                    criteriaDataString1.add(a.getNome());
                }
                
                tempCriteria = new CheckComboBox(criteriaDataString1, "Critérios");
                tempCriteria.setPrefWidth(lengthBiggestString(tempCriteria.getCheckItems()) * 11);
                hbox2.getChildren().set(1,tempCriteria);
                flowPaneCriteria.getChildren().clear();
                
                for(CheckBox selectedCriteria : tempCriteria.getItems()){
                    selectedCriteria.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if(newValue){
                                Label a = new Label((char)0x2022 + selectedCriteria.getText());
                                Label lblValue = new Label("Peso:");
                                TextField tfValue = new TextField();
                                tfValue.setPrefSize(50, 20);
                                
                                
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
                newCriteriaLength[0] = criteria.getAllCriteria().size();
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
        
        
        
       
        
        back.setOnAction(e->{
            if(oldEvaluatorLength[0] == newEvaluatorLength[0] || newEvaluatorLength[0] == 0){
                    evaluatorIndexes.clear();
                    evaluatorIndexes.addAll(ccbEvaluators.getSelectedIndexes());
                }else{
                    evaluatorIndexes.clear();
                    evaluatorIndexes.addAll(tempEvaluators.getSelectedIndexes());
                }
            eventPageOne();
        });
        
        HBox hbox3 = new HBox(back, next);
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

        VBox vbox1 = new VBox(lblOk, hbox1, lblSelectedEvaluators, flowPaneEvaluators, hbox2, lblSelectedCriteria,
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
        Button back = new Button("Voltar");
        
        save.setCursor(Cursor.HAND);
        back.setCursor(Cursor.HAND);
        
        
        
        HBox hbox5 = new HBox();
        
        hbox5.getChildren().addAll(back, save);
        
        hbox5.setSpacing(10);
        hbox5.setAlignment(Pos.TOP_RIGHT);
        hbox5.setPadding(new Insets(50, 10, 0, 0));
        
        
        stage.setTitle("Defina informações sobre local e palestrantes");
        
        Label lblPlace = new Label("Local: ");
        TextField tfPlace = new TextField(event.getLocal());
        Label lblVacancies = new Label("Capacidade: ");
        TextField tfVacancies = new TextField(Integer.toString(event.getCapacidade()));
        Label lblSpeakersVacancies = new Label("Quantidade de palestrantes: ");
        TextField tfSpeakersVacancies = new TextField(Integer.toString(event.getQuantidadePalestrantes()));
        
        MaskField.maxLength(tfPlace, 255);
        
        MaskField.ignoreKeys(tfVacancies);
        MaskField.numericField(tfVacancies);
        MaskField.ignoreKeys(tfSpeakersVacancies);
        MaskField.numericField(tfSpeakersVacancies);
        
        

        
        Label lblSpeakerName = new Label("Nome do palestrante: ");
        
        TextField tfSpeakerName = new TextField();
        
        if(invitedSpeakersNames.size() > 0){
            tfSpeakerName.setText(invitedSpeakersNames.get(0));
        }
        
        VBox vbox = new VBox();
        VBox vbox1 = new VBox();
        HBox hbox2 = new HBox(lblPlace, tfPlace, lblVacancies, tfVacancies, lblSpeakersVacancies, tfSpeakersVacancies);
        VBox vbox5 = new VBox();
        
        Label lblOk = new Label("");
        lblOk.setStyle("-fx-text-fill: red;");
        
        vbox5.setSpacing(10);
        
        vbox5.getChildren().addAll(lblOk, hbox2);
        
        hbox2.setSpacing(10);
        vbox1.setSpacing(10);
        vbox.setSpacing(10);
        
        
        
        vbox.setPadding(new Insets(10, 0, 0, 10));
       
   
        
        
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
                    hbox1.setVisible(true);
                    vbox.getChildren().add(vbox1);
                    
                }
                else{
                    hbox1.setVisible(false);
                    vbox.getChildren().remove(vbox1);
                    ((TextField)hbox1.getChildren().get(1)).setText(null);
                    invitedSpeakersNames.clear();
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
        
        ObservableList<Category> categoriesData = FXCollections.observableArrayList(categories.getAllCategories());
        ObservableList<String> categoriesDataString = FXCollections.observableArrayList();
        for(Category a : categoriesData){
            categoriesDataString.add(a.getNome());
        }
        Label lblCategories = new Label("Defina as categorias: ");
        
        
        CheckComboBox<String> ccbCategories = new CheckComboBox(categoriesDataString, "Categorias");
        FlowPane fpCategories = new FlowPane();
        
        if(!categoriesIndexes.isEmpty()){
            ccbCategories.setSelectedIndexes(categoriesIndexes);
            
            for(CheckBox selectedCategory : ccbCategories.getItems()){
                if(selectedCategory.isSelected()){
                    Label a = new Label((char)0x2022 + selectedCategory.getText());
                    fpCategories.getChildren().add(a);
                }
            }
        }
        
        
        Label lblSelectedCategories = new Label("Categorias selecionadas: ");
        
        ccbCategories.setPrefWidth(lengthBiggestString(ccbCategories.getCheckItems()) * 11);
        
        HBox hbox3 = new HBox();
        Button addCategory = new Button("*");
        hbox3.setSpacing(5);
        hbox3.getChildren().addAll(lblCategories, ccbCategories, addCategory);
        
        vbox2.setSpacing(10);
        vbox2.setPadding(new Insets(10, 0, 0, 0));
        
        oldCategoriesLength[0] = categories.getAllCategories().size();
        
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
        
        vbox3.getChildren().addAll(vbox5, vbox, vbox2, hbox5);
        
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
            confirm.setCursor(Cursor.HAND);
            cancel1.setCursor(Cursor.HAND);
            
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
            
            tfNewCategory.setOnAction(e1->{
                categories.addCategory(new Category(tfNewCategory.getText()));
                ObservableList<Category> categoriesData1 = FXCollections.observableArrayList(categories.getAllCategories());
                ObservableList<String> categoriesDataString1 = FXCollections.observableArrayList();
                for(Category a : categoriesData1){
                    categoriesDataString1.add(a.getNome());
                }
                tempCategories = new CheckComboBox(categoriesDataString1, "Categorias");
                tempCategories.setPrefWidth(lengthBiggestString(tempCategories.getCheckItems()) * 11);
                hbox3.getChildren().set(1,tempCategories);
                fpCategories.getChildren().clear();
                
                for(CheckBox selectedCriteria : tempCategories.getItems()){
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
                    newCategoriesLength[0] = categories.getAllCategories().size();
                }
                stage.close();
            });
            
            confirm.setOnAction(e1->{
                categories.addCategory(new Category(tfNewCategory.getText()));
                ObservableList<Category> categoriesData1 = FXCollections.observableArrayList(categories.getAllCategories());
                ObservableList<String> categoriesDataString1 = FXCollections.observableArrayList();
                for(Category a : categoriesData1){
                    categoriesDataString1.add(a.getNome());
                }
                tempCategories = new CheckComboBox(categoriesDataString1, "Categorias");
                tempCategories.setPrefWidth(lengthBiggestString(tempCategories.getCheckItems()) * 11);
                hbox3.getChildren().set(1,tempCategories);
                fpCategories.getChildren().clear();
                
                for(CheckBox selectedCriteria : tempCategories.getItems()){
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
                    newCategoriesLength[0] = categories.getAllCategories().size();
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
        
        
        
        save.setOnAction(e->{
            int resultado = 0;
            for(String a : invitedSpeakersNames){
                    if(a == null || a.equals("")){
                        resultado++;
                    }
            }
      
            if(tfPlace.getText() == null || tfPlace.getText().equals("")){
                lblOk.setText("O campo local está vazio");
            }else if(tfVacancies.getText() == null || tfVacancies.getText().equals("") || Integer.parseInt(tfVacancies.getText()) <= 0){
                lblOk.setText("O campo de vagas está vazio");
            }else if(tfSpeakersVacancies.getText() == null || tfSpeakersVacancies.getText().equals("") || Integer.parseInt(tfSpeakersVacancies.getText()) <= 0){
                lblOk.setText("O campo de vagas para palestrantes está vazio");
            }else if(!invitedSpeakersNames.isEmpty() && resultado > 0){
                lblOk.setText("Preencha o campo de palestrante convidado");
            }else if(oldCategoriesLength[0] == newCategoriesLength[0] || newCategoriesLength[0] == 0){
                if(ccbCategories.getSelectedIndexes().isEmpty()){
                    lblOk.setText("Selecione ao menos uma categoria");
                } else { 
                    if(oldCategoriesLength[0] == newCategoriesLength[0] || newCategoriesLength[0] == 0){
                        categoriesIndexes.clear();
                        categoriesIndexes.addAll(ccbCategories.getSelectedIndexes());
                    }else{
                        categoriesIndexes.clear();
                        categoriesIndexes.addAll(tempCategories.getSelectedIndexes());
                    }

                    if(((TextField)hbox1.getChildren().get(1)).getText() != null && !((TextField)hbox1.getChildren().get(1)).getText().equals("")){
                        invitedSpeakersNames.add(((TextField)hbox1.getChildren().get(1)).getText());
                    }

                    for(int i = 0; i < vbox1.getChildren().size(); i++){
                        HBox hbox = ((HBox)vbox1.getChildren().get(i));
                        if(((TextField)hbox.getChildren().get(1)).getText() != null && !((TextField)hbox.getChildren().get(1)).getText().equals("")){
                            invitedSpeakersNames.add(((TextField)hbox.getChildren().get(1)).getText());
                        }
                    }

                    event.setLocal(tfPlace.getText());
                    event.setCapacidade(Integer.parseInt(tfVacancies.getText()));
                    event.setQuantidadePalestrantes(Integer.parseInt(tfSpeakersVacancies.getText()));
                    event.setStatus(true);
                    event.setComite(Main.getBoard());


                    Event eventWithId = events.addEvent(event);

                    List<Category> categoriesDataUpdated = categories.getAllCategories();

                    for(Integer a : categoriesIndexes){
                        categories.addCategoryInAnEvent(categories.getCategoryById(categoriesDataUpdated.get(a).getId()), eventWithId);
                    }

                    List<Evaluator> evaluatorsDataUpdated = evaluators.getAllEvaluators();

                    for(Integer a : evaluatorIndexes){
                        evaluators.addAnEvaluatorInAnEvent(eventWithId, evaluators.getEvaluatorById(evaluatorsDataUpdated.get(a).getId()));
                    }

                    for(String a : invitedSpeakersNames){
                        events.addATemporarySpeaker(a, eventWithId);
                    }

                    List<Criteria> criteriaDataUpdate = criteria.getAllCriteria();

                    List<Integer> values = FXCollections.observableArrayList();

                    for(int i = 0; i < flowPaneCriteria.getChildren().size(); i++){
                        HBox hboxTemp = ((HBox)flowPaneCriteria.getChildren().get(i));
                        TextField tfTemp = ((TextField)hboxTemp.getChildren().get(2));
                        values.add(Integer.parseInt(tfTemp.getText()));
                    }

                    int i = 0;

                    for(Integer a : criteriaIndexes){
                        criteria.addACriteriaInAnEvent(eventWithId, criteria.getCriteriaById(criteriaDataUpdate.get(a).getId()), values.get(i));
                        i++;
                    }

                    stage.close();
                    Main.eventShow();
                }
                
            }else{
                if(tempCategories.getSelectedIndexes().isEmpty()){
                    lblOk.setText("Selecione ao menos uma categoria");
                }
                else{
                     if(oldCategoriesLength[0] == newCategoriesLength[0] || newCategoriesLength[0] == 0){
                        categoriesIndexes.clear();
                        categoriesIndexes.addAll(ccbCategories.getSelectedIndexes());
                    }else{
                        categoriesIndexes.clear();
                        categoriesIndexes.addAll(tempCategories.getSelectedIndexes());
                    }

                    if(((TextField)hbox1.getChildren().get(1)).getText() != null || !((TextField)hbox1.getChildren().get(1)).getText().equals("")){
                        invitedSpeakersNames.add(((TextField)hbox1.getChildren().get(1)).getText());
                    }

                    for(int i = 0; i < vbox1.getChildren().size(); i++){
                        HBox hbox = ((HBox)vbox1.getChildren().get(i));
                        invitedSpeakersNames.add(((TextField)hbox.getChildren().get(1)).getText());
                    }

                    event.setLocal(tfPlace.getText());
                    event.setCapacidade(Integer.parseInt(tfVacancies.getText()));
                    event.setQuantidadePalestrantes(Integer.parseInt(tfSpeakersVacancies.getText()));
                    event.setStatus(true);
                    event.setComite(Main.getBoard());


                    Event eventWithId = events.addEvent(event);

                    List<Category> categoriesDataUpdated = categories.getAllCategories();

                    for(Integer a : categoriesIndexes){
                        categories.addCategoryInAnEvent(categories.getCategoryById(categoriesDataUpdated.get(a).getId()), eventWithId);
                    }

                    List<Evaluator> evaluatorsDataUpdated = evaluators.getAllEvaluators();

                    for(Integer a : evaluatorIndexes){
                        evaluators.addAnEvaluatorInAnEvent(eventWithId, evaluators.getEvaluatorById(evaluatorsDataUpdated.get(a).getId()));
                    }

                    for(String a : invitedSpeakersNames){
                        events.addATemporarySpeaker(a, eventWithId);
                    }

                    List<Criteria> criteriaDataUpdate = criteria.getAllCriteria();

                    List<Integer> values = FXCollections.observableArrayList();

                    for(int i = 0; i < flowPaneCriteria.getChildren().size(); i++){
                        HBox hboxTemp = ((HBox)flowPaneCriteria.getChildren().get(i));
                        TextField tfTemp = ((TextField)hboxTemp.getChildren().get(2));
                        values.add(Integer.parseInt(tfTemp.getText()));
                    }

                    int i = 0;

                    for(Integer a : criteriaIndexes){
                        criteria.addACriteriaInAnEvent(eventWithId, criteria.getCriteriaById(criteriaDataUpdate.get(a).getId()), values.get(i));
                        i++;
                    }

                    stage.close();
                    Main.eventShow();
                }
            }
            
        });
        
        back.setOnAction(e->{
            event.setLocal(tfPlace.getText());
            event.setCapacidade(Integer.parseInt(tfVacancies.getText()));
            event.setQuantidadePalestrantes(Integer.parseInt(tfSpeakersVacancies.getText()));
            
            
            if(oldCategoriesLength[0] == newCategoriesLength[0] || newCategoriesLength[0] == 0){
                categoriesIndexes.clear();
                categoriesIndexes.addAll(ccbCategories.getSelectedIndexes());
            }else{
                categoriesIndexes.clear();
                categoriesIndexes.addAll(tempCategories.getSelectedIndexes());
            }
            
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Ao voltar você perderá as informações sobre palestrantes convidados. Deseja confirmar?", 
                    ButtonType.YES, ButtonType.NO);
            
            alerta.setTitle("Atenção");
            alerta.setHeaderText(null);
            Stage stage = (Stage)alerta.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
            
            
            
            
            Optional<ButtonType> result = alerta.showAndWait();
            
            if(result.get() == ButtonType.YES){
                eventPageTwo();
            } else {
                stage.close();
            }
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

    public void editEventShow(Event event) {
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        Stage stage = new Stage();
        
        stage.setTitle("Atualizar dados do evento");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
        
        Label lblOk = new Label("");
        Label lblTitle = new Label("Título:");
        TextField title = new TextField(event.getTitulo());
        Label lblDescription = new Label("Descrição:");
        TextArea description = new TextArea(event.getDescricao());
        Label lblOverview = new Label("Resumo:");
        TextArea overview = new TextArea(event.getResumo());
        Label lblStartTime = new Label("Hora início:");
        lblOk.setStyle("-fx-text-fill: red;");
        DatePicker date;
        TextField startTime;
        
        if(event.getDataInicio() != null){
            date = new DatePicker(event.getDataInicio().toLocalDate());
            startTime = new TextField(event.getDataInicio().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else{
            date = new DatePicker();
            startTime = new TextField();
        }
        
        Label lblDuration = new Label("Duração:");
        TextField duration = new TextField(Integer.toString(event.getDuracao()));
        Button btnSave = new Button("Salvar");

        MaskField.maxLength(title, 255);
        MaskField.maxLength(description, 1024);
        MaskField.maxLength(overview, 2048);
        
        startTime.setDisable(true);
        date.setDisable(true);
        duration.setDisable(true);
        
        Button btnCancel = new Button("Cancelar");
        
        btnSave.setCursor(Cursor.HAND);
        btnCancel.setCursor(Cursor.HAND);
        
        Main.setStyleTextField(title, description, overview);
        
        Main.setStyleLabeled(lblTitle, lblDescription, lblDuration, lblStartTime, lblOverview, btnSave, btnCancel);
        
        startTime.setFont(Font.font("Segoe UI", 12));
        duration.setFont(Font.font("Segoe UI", 12));
        date.setStyle("-fx-font-size:12;-fx-font-family:Segoe UI");
        overview.setPrefWidth(300);
        description.setPrefWidth(300);
       
        btnSave.setOnAction(e->{
           if(title.getText() == null || title.getText().equals("")){
               lblOk.setText("O campo título está vazio");
           }else if(description.getText() == null || description.getText().equals("")){
               lblOk.setText("O campo descrição está vazio");
           }else if(overview.getText() == null || overview.getText().equals("")){
               lblOk.setText("o campo resumo está vazio");
           }else{
               event.setTitulo(title.getText());
               event.setResumo(overview.getText());
               event.setDescricao(description.getText());
               events.updateEvent(event);
               stage.close();
               Main.eventShow();
           }
        });
       
        btnCancel.setOnAction(e->{
            stage.close();
        });
        
        MaskField.ignoreKeys(duration);
        MaskField.ignoreKeys(startTime);
        MaskField.numericField(duration);
        MaskField.timeField(startTime);
     
        
        HBox hbox1 = new HBox(5);
        HBox hbox2 = new HBox(10);
        
        hbox1.getChildren().addAll(date, lblStartTime, startTime, lblDuration, duration);
        hbox2.getChildren().addAll(btnCancel, btnSave);
        
        hbox2.setAlignment(Pos.TOP_RIGHT);
        hbox2.setPadding(new Insets(20, 0, 0, 0));
        
        
        
        gridPane.add(lblOk, 0, 0);
        gridPane.add(lblTitle, 0, 1);
        gridPane.add(title, 1, 1);
        gridPane.add(lblDescription, 0, 2);
        gridPane.add(description, 1, 2);
        gridPane.add(lblOverview, 0, 3);
        gridPane.add(overview, 1, 3);
        gridPane.add(hbox1, 1, 4);
        gridPane.add(hbox2, 1, 5);
        
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        GridPane.setColumnSpan(lblOk, GridPane.REMAINING);
        GridPane.setHalignment(lblOk, HPos.CENTER);
        gridPane.setPadding(new Insets(10));
        
        scrollPane.setContent(gridPane);
        
        Main.setBackgroundWhite(gridPane, hbox1, scrollPane);
        Scene scene = new Scene(scrollPane, 730, 600);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
