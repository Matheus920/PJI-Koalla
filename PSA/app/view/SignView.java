package app.view;

import app.Main;
import app.control.CRUDUser;
import app.control.interfaces.PrivilegeTypeInterface;
import app.model.User;
import java.io.File;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SignView {
    private final TextField tfName =         new TextField();
    private final TextField tfEmail =        new TextField();
    private final TextField tfInstitution =  new TextField();
    private final TextField tfFile =         new TextField();
    private final PasswordField pfPassword = new PasswordField();
    private final PasswordField pfConfirm =  new PasswordField();
    private final DatePicker dob =           new DatePicker();
    private final FileChooser fileChooser =  new FileChooser();
    private final Label lblName =            new Label("Nome:");
    private final Label lblEmail =           new Label("Email:");
    private final Label lblInstitution =     new Label("Instituição:");
    private final Label lblPassword =        new Label("Senha:");
    private final Label lblConfirm =         new Label("Confirmar\n   senha:");
    private final Button sign =              new Button("Confirmar");
    private final GridPane gridPane =        new GridPane();
    private final Scene scene =              new Scene(gridPane, 600, 600);
    private final Button btnFile =           new Button("...");
    private final Label lblFile =            new Label("Imagem de perfil:");
    private final Label lblDob =             new Label("Data de nascimento:");
    private final Label lblOk =              new Label("");
    
    private final String fields[] = {"Nome", "Email", "Instituição", "Data de nascimento", "Senha", "Confirmar Senha", "Imagem de perfil"};
    private final Stage stage;
    public SignView(Stage stage) {
        this.stage = stage;
        this.stage.centerOnScreen();
        setFields();
        setLabels();  
        setButtons();
        
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(tfFile, btnFile);
        HBox hbox1 = new HBox(5);
        hbox1.setAlignment(Pos.BOTTOM_RIGHT);
        hbox1.getChildren().addAll(lblOk, sign);
        
        gridPane.add(lblName, 0, 0);
        gridPane.add(tfName, 1, 0);
        gridPane.add(lblEmail, 0, 1);
        gridPane.add(tfEmail, 1, 1);
        gridPane.add(lblInstitution, 0, 2);
        gridPane.add(tfInstitution, 1, 2);
        gridPane.add(lblDob, 0, 3);
        gridPane.add(dob, 1, 3);
        gridPane.add(lblPassword, 0, 4);
        gridPane.add(pfPassword, 1, 4);
        gridPane.add(lblConfirm, 0, 5);
        gridPane.add(pfConfirm, 1, 5);
        gridPane.add(lblFile, 0, 6);
        gridPane.add(hbox, 1, 6);
        gridPane.add(hbox1, 1, 7);
        
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        gridPane.setAlignment(Pos.TOP_CENTER);

        GridPane.setHalignment(lblName, HPos.RIGHT);
        GridPane.setHalignment(lblEmail, HPos.RIGHT);
        GridPane.setHalignment(lblInstitution, HPos.RIGHT);
        GridPane.setHalignment(lblPassword, HPos.RIGHT);
        GridPane.setHalignment(lblConfirm, HPos.RIGHT);
        GridPane.setHalignment(lblDob, HPos.RIGHT);
        GridPane.setHalignment(lblFile, HPos.RIGHT);
        
        GridPane.setMargin(lblName, new Insets(30, 0, 0, 0));
        GridPane.setMargin(tfName, new Insets(30, 0, 0, 0));
        stage.setScene(scene);
    }
    
    private void setFields() {
        tfName.setFont(Font.font("Segoe UI", 18));
        tfEmail.setFont(Font.font("Segoe UI", 18));
        tfFile.setFont(Font.font("Segoe UI", 18));
        tfInstitution.setFont(Font.font("Segoe UI", 18));
        pfPassword.setFont(Font.font("Segoe UI", 18));
        pfConfirm.setFont(Font.font("Segoe UI", 18));
        dob.setStyle("-fx-font-size:18;-fx-font-family:Segoe UI");
        
        tfName.setMinWidth(100);
        tfEmail.setMinWidth(100);
        tfFile.setMinWidth(100);
        tfInstitution.setMinWidth(80);
        pfPassword.setMinWidth(80);
        pfConfirm.setMinWidth(80);
        dob.setMinWidth(80); 
       
        dob.setEditable(false);
    }
    
    private void setLabels() {
        lblName.setFont(Font.font("Segoe UI", 18));
        lblConfirm.setFont(Font.font("Segoe UI", 18));
        lblEmail.setFont(Font.font("Segoe UI", 18));
        lblInstitution.setFont(Font.font("Segoe UI", 18));
        lblPassword.setFont(Font.font("Segoe UI", 18));
        lblFile.setFont(Font.font("Segoe UI", 18));
        lblDob.setFont(Font.font("Segoe UI", 17));
        lblOk.setFont(Font.font("Segoe UI", 18));
        lblOk.setStyle("-fx-text-fill: #ff0000");
        lblOk.setAlignment(Pos.BASELINE_RIGHT);
    }
    
    private void setButtons() {
        btnFile.setOnAction(e ->{
            fileChooser.setTitle("Escolha sua imagem de perfil");
            fileChooser.setInitialDirectory(new File("C:/Users/" + System.getProperty("user.name") + "/documents"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem [*.png, *.jpg, *.bmp, *.jpeg]", "*.png", "*.jpg", "*.bmp", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if(selectedFile != null) {
                tfFile.setText(selectedFile.getAbsolutePath());
            } else {
                tfFile.setText(null);
            }
        });

        sign.setFont(Font.font("Segoe UI", 15));
        sign.setCursor(Cursor.HAND);
        sign.setOnAction(e->{
            lblOk.setText(invalidField());
            if(lblOk.getText().equals("")) {
                User user = new User(tfName.getText(), tfEmail.getText(), pfPassword.getText(), tfInstitution.getText(), dob.getValue(), tfFile.getText(), false);
                new CRUDUser().addUser(user);
                Main.getPrivilege().setPrivilegeType(PrivilegeTypeInterface.USER);
                Main.refresh();
                stage.close();
            }
        });
    }

    private String invalidField() {
        if(tfName.getText() == null || tfName.getText().equals("")) {
            return "* Campo " + fields[0] + " está vazio"; 
        } else if(tfEmail.getText() == null || tfEmail.getText().equals("")){
            return "* Campo " + fields[1] + " está vazio"; 
        } else if(tfInstitution.getText() == null || tfInstitution.getText().equals("")) {
            return "* Campo " + fields[2] + " está vazio"; 
        } else if(((TextField)dob.getEditor()).getText() == null || ((TextField)dob.getEditor()).getText().equals("")) {
            return "* Campo " + fields[3] + " está vazio"; 
        } else if(pfPassword.getText() == null || pfPassword.getText().equals("")) {
            return "* Campo " + fields[4] + " está vazio"; 
        } else if(pfConfirm.getText() == null || pfConfirm.getText().equals("")) {
            return "* Campo " + fields[5] + " está vazio"; 
        } else if(tfFile.getText() == null || tfFile.getText().equals("")) {
            return "* Campo " + fields[6] + " está vazio"; 
        } else if(!pfPassword.getText().equals(pfConfirm.getText())) {
            return "Senhas não coincidem";
        } else if((tfEmail.getText().contains("@") == false) || (tfEmail.getText().contains(".") == false)) {
            return "Email inválido";
        }
        return "";
    }
    
}
