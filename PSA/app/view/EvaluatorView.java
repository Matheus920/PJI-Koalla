package app.view;

import app.Main;
import app.control.CRUDCategoryTest;
import app.control.interfaces.CRUDEvaluatorInterface;
import app.control.interfaces.PrivilegeTypeInterface;
import app.view.viewcontrollers.autocompletecheckcombobox.CheckComboBox;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EvaluatorView {
    private final SplitPane splitPane = new SplitPane();
    private PrivilegeTypeInterface privilege;
    private final CRUDEvaluatorInterface evaluators;
    private final CRUDCategoryTest categories;
    
    public EvaluatorView(PrivilegeTypeInterface privilege, CRUDEvaluatorInterface evaluators, CRUDCategoryTest categories) {
        this.privilege = privilege;
        this.categories = categories;
        this.evaluators = evaluators;
        setLeft();
        setRight();
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
        
        ListView<String> listView = new ListView<>(FXCollections.observableArrayList(evaluators.getAllEvaluators()));
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
                
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
    
    private void setRight() {
        TextField id = new TextField();
        TextField name = new TextField();
        TextField area = new TextField();
        DatePicker dob = new DatePicker();
        CheckComboBox<String> specialization = new CheckComboBox(FXCollections.observableArrayList(categories.getAllCategories()));
        CheckBox evaluator;
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.BOARD) {
            evaluator = new CheckBox();

        } else {
            evaluator = new CheckBox(){
                @Override
                public void arm() {
                    
                }
            };
            
            specialization.setEditable(false);
        }
        
        evaluator.setText("Avaliador");
        
        Label lbl = new Label("Lista de eventos que avaliou/avaliando");
        lbl.setFont(Font.font("Segoe UI", 14));
       
        Main.setStyleTextField(id, name, area);
        dob.setStyle("-fx-font-size:18;-fx-font-family:Segoe UI");
        
        id.setMaxWidth(300);
        name.setMaxWidth(300);
        area.setMaxWidth(300);
        dob.setMaxWidth(300);
        
        id.setPromptText("Prontuário");
        name.setPromptText("Nome");
        area.setPromptText("Área");
        dob.setPromptText("Data de nascimento");
        specialization.setPromptText("Especialização");
        
        id.setEditable(false);
        name.setEditable(false);
        area.setEditable(false);
        dob.setEditable(false);
        
        VBox vbox1 = new VBox(5);
        VBox vbox2 = new VBox(5);
        AnchorPane anchorPane = new AnchorPane();
        
        evaluator.setSelected(false);        
        
        vbox1.getChildren().addAll(id, name, dob, area, evaluator, specialization);
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.BOARD) {
            Button save = new Button("Salvar");
            Main.setStyleLabeled(save);
            HBox hbox1 = new HBox();
            hbox1.setAlignment(Pos.CENTER_RIGHT);
            hbox1.getChildren().add(save);
            vbox1.getChildren().add(hbox1);
        }
        
        Main.setBackgroundWhite(vbox1, anchorPane);
        vbox1.setPadding(new Insets(10, 0, 0, 10));
        
        ListView<String> list = new ListView<>(FXCollections.observableArrayList("Dia das abelhas", "Homenagem Raul Seixas", "Inglaterra antes de Deus"));
        list.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 14; "
                + "-fx-font-family: 'Segoe UI'");
        
        vbox2.getChildren().addAll(lbl, list);
        vbox2.setPrefWidth(400);
        anchorPane.getChildren().addAll(vbox1, vbox2);
        
        AnchorPane.setRightAnchor(vbox2, 10.00);
        AnchorPane.setTopAnchor(vbox2, 10.00);
        
        splitPane.getItems().add(anchorPane);
    }
    
}
