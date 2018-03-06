package app.view;

import app.Main;
import app.control.CRUDCategoryTest;
import app.control.LoginController;
import app.control.interfaces.CRUDEvaluatorInterface;
import app.control.interfaces.PrivilegeTypeInterface;
import app.model.Category;
import app.model.Evaluator;
import app.model.Event;
import app.model.Login;
import app.view.viewcontrollers.MaskField;
import app.view.viewcontrollers.autocompletecheckcombobox.CheckComboBox;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EvaluatorView {
    private final SplitPane splitPane = new SplitPane();
    private PrivilegeTypeInterface privilege;
    private final CRUDEvaluatorInterface evaluators;
    private final CRUDCategoryTest categories;
    private ListView<String> listView;
    private final LoginController loginController = new LoginController();
    private ObservableList<Evaluator> evaluatorData;
    private final String[] fields = {"Prontuário", "Nome", "Email", "Senha", "Data de Nascimento", "Área"};
    private final TextField id = new TextField();
    private final TextField name = new TextField();
    private final PasswordField password = new PasswordField();
    private final TextField email = new TextField();
    private final  TextField area = new TextField();
    private final DatePicker dob = new DatePicker();
    private final Label lblOk = new Label("");
    private CheckBox cbEvaluator;
    private CheckComboBox<String> specialization;
    
    
    public EvaluatorView(PrivilegeTypeInterface privilege, CRUDEvaluatorInterface evaluators, CRUDCategoryTest categories) {
        Main.refreshBottom();
        this.privilege = privilege;
        this.categories = categories;
        this.evaluators = evaluators;
        evaluatorData = FXCollections.observableArrayList(evaluators.getAllTeachers());
        setLeft();
        splitPane.setDividerPositions(0.20f);
    }
    
    public SplitPane evaluatorShow() {
        return splitPane;
    }
    
    private void setLeft() {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        Label lblMembers = new Label("Lista de professores");
        
        vbox2.getChildren().addAll(lblMembers);
        
        ObservableList<String> evaluatorDataString = FXCollections.observableArrayList();
        
        for(Evaluator a : evaluatorData){
            evaluatorDataString.add(a.getNome());
        }
        
        listView = new ListView<>(evaluatorDataString);
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
            
          listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obsv, String oldv, String nv) -> {
            if(nv == null) return;
            setRight(evaluators.getEvaluatorById(evaluatorData.get(listView.getSelectionModel().getSelectedIndex()).getId()));
            
            if(oldv == null) return;
        });
        
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
  
    
    private void setRight(Evaluator evaluator) {
        if(splitPane.getItems().size() > 1){
            splitPane.getItems().remove(1);
            splitPane.setDividerPositions(0.20f);
        }
        
        if(evaluator != null){
            id.setText(evaluator.getProntuario());
            name.setText(evaluator.getNome());
            area.setText(evaluator.getArea());
            dob.setValue(evaluator.getDataNascimento());
            email.setText(evaluator.getLogin().getEmail());
            password.setText(evaluator.getLogin().getSenha());
            password.setVisible(false);
        }else{
            id.setText("");
            name.setText("");
            area.setText("");
            ((TextField)dob.getEditor()).setText("");
            email.setText("");
            password.setText("");
            password.setVisible(false);
        }
        
        Label specializations = new Label("Áreas de especialização: ");
        ObservableList<Category> categoryData = FXCollections.observableArrayList(categories.getAllCategories());
        ObservableList<String> categoryDataString = FXCollections.observableArrayList();
        
        for(Category a : categoryData){
            categoryDataString.add(a.getNome());
        }
        
        specialization = new CheckComboBox(categoryDataString, "Especialização");
       
        List<Integer> indexes = new ArrayList<>();
        int index1;
        
        for(int i = 0; i < evaluators.getAllCategoriesByEvaluator(evaluator).size(); i++){
            index1 = categoryDataString.indexOf(evaluators.getAllCategoriesByEvaluator(evaluator).get(i).getNome());
            
            
            if(index1 >= 0){
                indexes.add(index1);
            }
        }
        
        specialization.setSelectedIndexes(indexes);
        
        FlowPane flowPane = new FlowPane();
        
        for(CheckBox selectedEvaluator : specialization.getItems()){
             if(selectedEvaluator.isSelected()){
                 Label a = new Label((char)0x2022 + selectedEvaluator.getText());
                 flowPane.getChildren().add(a);
             }
         }
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.BOARD) {
            password.setVisible(true);
            cbEvaluator = new CheckBox();

        } else {
            cbEvaluator = new CheckBox(){
                @Override
                public void arm() {
                    
                }
            };
            
            specialization.setVisible(false);
        }
        
        cbEvaluator.setText("Avaliador");
        
        Label lbl = new Label("Lista de eventos que avaliou/avaliando");
        lbl.setFont(Font.font("Segoe UI", 14));
       
        Main.setStyleTextField(id, name, area, email, password);
        dob.setStyle("-fx-font-size:18;-fx-font-family:Segoe UI");
        lblOk.setStyle("-fx-text-fill: red;");
        
        
        id.setMaxWidth(300);
        name.setMaxWidth(300);
        area.setMaxWidth(300);
        dob.setMaxWidth(300);
        
        id.setPromptText("Prontuário");
        name.setPromptText("Nome");
        area.setPromptText("Área");
        dob.setPromptText("Data de nascimento");
        password.setPromptText("Senha de acesso");
        email.setPromptText("Email");
        
        MaskField.maxLength(id, 255);
        MaskField.maxLength(name, 255);
        MaskField.maxLength(area, 255);
        MaskField.maxLength(password, 255);
        MaskField.maxLength(email, 255);
        
   
        
        for(CheckBox selectedEvaluator : specialization.getItems()){
            selectedEvaluator.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    Label a = new Label((char)0x2022 + selectedEvaluator.getText());
                    flowPane.getChildren().add(a);
                }else {
                    for(int i = 0; i < flowPane.getChildren().size(); i++) {
                        if(((Label)flowPane.getChildren().get(i)).getText().contains(selectedEvaluator.getText())) {
                            flowPane.getChildren().remove(i);
                        }
                    }
                }

                }
            });
        }
        
        HBox hbox = new HBox(specializations, flowPane);
        
        flowPane.setHgap(40);
        flowPane.setVgap(5);
        flowPane.setOrientation(Orientation.HORIZONTAL);
        flowPane.setPrefWrapLength(100);
        
        id.setEditable(false);
        name.setEditable(false);
        area.setEditable(false);
        dob.setEditable(false);
        password.setEditable(false);
        email.setEditable(false);
        
        VBox vbox1 = new VBox(5);
        VBox vbox2 = new VBox(5);
        VBox vbox3 = new VBox(5);
        AnchorPane anchorPane = new AnchorPane();
        
        if(evaluator.isAvaliador()){
            cbEvaluator.setSelected(true);
        } else {
            cbEvaluator.setSelected(false);        
        }
        
       
        vbox1.getChildren().addAll(lblOk, id, name, email, password, dob, area, cbEvaluator, specialization, hbox);
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.BOARD) {
            password.setEditable(true);
            Button save = new Button("Salvar");
            Main.setStyleLabeled(save);
            HBox hbox1 = new HBox();
            hbox1.setAlignment(Pos.CENTER_RIGHT);
            
            
            save.setOnAction(e->{
                lblOk.setText(invalidField());
                
                if(lblOk.getText().equals("")){
                
                    if(evaluators.exists(id.getText()) && !(id.getText().equals(evaluator.getProntuario()))){
                        lblOk.setText("* O prontuário já está cadastrado");
                    }else if (loginController.emailExists(email.getText()) && !(email.getText().equals(evaluator.getLogin().getEmail()))){
                        lblOk.setText("* O email já está cadastrado");
                    } else {
                        if(cbEvaluator.isSelected()){
                            Login login = new Login(evaluator.getLogin().getId(), email.getText(), password.getText(), 2);
                            loginController.updateLogin(login);
                            evaluator.setLogin(login);
                        } else {
                            Login login1 = new Login(evaluator.getLogin().getId(), email.getText(), password.getText(), 1);
                            loginController.updateLogin(login1);
                            evaluator.setLogin(login1);
                        }

                        evaluator.setArea(area.getText());
                        evaluator.setNome(name.getText());
                        evaluator.setAvaliador(cbEvaluator.isSelected());
                        evaluator.setDataNascimento(dob.getValue());
                        evaluator.setProntuario(id.getText());
                        int index = listView.getSelectionModel().getSelectedIndex();
                        listView.getItems().set(index, name.getText());
                        this.evaluators.updateEvaluator(evaluator);
                        
                        this.evaluators.deleteAllEvaluatorsCategories(evaluator);
                        
                        for(Integer a : specialization.getSelectedIndexes()){
                            this.evaluators.addInACategoryInAnEvaluator(categoryData.get(a), evaluator);
                        }
                        
                        setRight(evaluator);
                        
                        
                    }
                }
            });
            
            hbox1.getChildren().add(save);
            vbox1.getChildren().add(hbox1);
        }
        
        Main.setBackgroundWhite(vbox1, anchorPane);
        vbox1.setPadding(new Insets(10, 0, 0, 10));
        
        
        ObservableList<Event> eventsData = FXCollections.observableArrayList(evaluators.getAllEventsByEvaluator(evaluator));
        ObservableList<String> eventsDataString = FXCollections.observableArrayList();
        
        for(Event a : eventsData){
            eventsDataString.add(a.getTitulo());
        }
        
        ListView<String> list = new ListView<>(eventsDataString);
        list.setStyle("-fx-control-inner-background-alt: white;");
        
        list.setCursor(Cursor.HAND);
        
        list.setOnMouseClicked(e->{
            if(list.getSelectionModel().getSelectedItem() != null){
                if(e.getClickCount() == 2){
                    Main.symposiumShow(eventsData.get(list.getSelectionModel().getSelectedIndex()));
                }
            }
        });
        
        vbox2.getChildren().addAll(lbl, list);
        vbox2.setPrefWidth(400);
        anchorPane.getChildren().addAll(vbox1, vbox2);
        
        AnchorPane.setRightAnchor(vbox2, 10.00);
        AnchorPane.setTopAnchor(vbox2, 10.00);
        
        splitPane.getItems().add(anchorPane);
    }
    
    private String invalidField(){
        if(id.getText().equals("") || id.getText() == null){
            return "* Campo " + fields[0] + " está vazio";
        } else if(name.getText().equals("") || name.getText() == null){
            return "* Campo " + fields[1] + " está vazio";
        }else if(email.getText().equals("") || email.getText() == null){
            return "* Campo " + fields[2] + " está vazio";
        }else if(password.getText().equals("") || password.getText() == null){
            return "* Campo " + fields[3] + " está vazio";
        } else if((((TextField)dob.getEditor()).getText() == null || ((TextField)dob.getEditor()).getText().equals(""))){
            return "* Campo " + fields[4] + " está vazio";
        }else if(area.getText().equals("") || area.getText() == null){
            return "* Campo " + fields[5] + " está vazio";
        } 
        return "";
    }
}
