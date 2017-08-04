package app.view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Login {
    private final Label lblUser = new Label("Usuário:");
    private final Label lblPassword = new Label("Senha:");
    private final TextField tfUser = new TextField();
    private final PasswordField pfPassword = new PasswordField();
    private final Button sign = new Button("Registrar");
    private final Button login = new Button("Entrar");
    private final Stage stage = new Stage();
    private final GridPane gridPane = new GridPane();
    private final Scene scene = new Scene(gridPane, 400, 300);
    
    public Login() {
        setLabels();
        setButtons();
        setFields();
        setStage();
        
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.getChildren().addAll(sign, login);
        
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        
        gridPane.add(lblUser, 0, 0);
        gridPane.add(tfUser, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pfPassword, 1, 1);
        gridPane.add(hbox, 1, 2);
        
        GridPane.setHalignment(lblPassword, HPos.RIGHT);
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
    }
    
    private void setLabels() {
        lblUser.setFont(Font.font("Segoe UI", 18));
        lblPassword.setFont(Font.font("Segoe UI", 18));
    }
    
    private void setButtons() {
        sign.setFont(Font.font("Segoe UI", 15));
        sign.setCursor(Cursor.HAND);
        
        login.setFont(Font.font("Segoe UI", 15));
        login.setCursor(Cursor.HAND);
        
        sign.setOnAction(e ->{
            stage.setTitle("Registrar");
            stage.setScene(new Sign().signShow());
            stage.centerOnScreen();
        });
    }
    
    private void setFields() {
        tfUser.setFont(Font.font("Segoe UI", 18));
        pfPassword.setFont(Font.font("Segoe UI", 18));
        
        tfUser.setMinWidth(80);
        pfPassword.setMinWidth(80);
    }
    
    private void setStage() {
        stage.setTitle("Login");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
        stage.setScene(scene);
    }
}
