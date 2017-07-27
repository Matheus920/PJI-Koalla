package app.view;

import java.io.File;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

public class Sign {
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
    private final Button sign =              new Button("Registrar");
    private final GridPane gridPane =        new GridPane();
    private final Scene scene =              new Scene(gridPane, 600, 600);
    private final AnchorPane anchorPane =    new AnchorPane();
    private final Button btnFile =           new Button("...");
    private final Label lblFile =            new Label("Imagem de perfil:");
    private final Label lblDob =             new Label("Data de nascimento:");
    
    public Sign() {
        setFields();
        setLabels();  
        setButtons();
        
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(tfFile, btnFile);

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
        gridPane.add(sign, 1, 7);
        
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        gridPane.setAlignment(Pos.TOP_CENTER);

        GridPane.setHalignment(lblName, HPos.RIGHT);
        GridPane.setHalignment(lblEmail, HPos.RIGHT);
        GridPane.setHalignment(lblInstitution, HPos.RIGHT);
        GridPane.setHalignment(lblPassword, HPos.RIGHT);
        GridPane.setHalignment(lblConfirm, HPos.RIGHT);
        GridPane.setHalignment(sign, HPos.RIGHT);
        GridPane.setHalignment(lblDob, HPos.RIGHT);
        GridPane.setHalignment(lblFile, HPos.RIGHT);
        
        GridPane.setMargin(lblName, new Insets(30, 0, 0, 0));
        GridPane.setMargin(tfName, new Insets(30, 0, 0, 0));
    }
    
    public Scene registrarShow() {
        return scene;
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
        
    }
    
}
