/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Login {
    private final Group root = new Group();
    private final Scene scene = new Scene(root, 290, 200, Color.WHITE);
    private final Rectangle rect = new Rectangle(290, 200);
    private final VBox vbox = new VBox(5);
    private final Text user = new Text(System.getProperty("user.name"));
    private final Label label = new Label();
    private final TextField username = new TextField();
    private final PasswordField password = new PasswordField();
    private final Button login = new Button("Login");
    private final HBox hbox = new HBox(10);
    
    public Scene loginShow() {
        setRetangles();
        setFields();
        setTexts();
        setButtons();
        setLabels();
        setPositions();
        return scene;
    }

    private boolean loginCheck(String username, String password) {
        try {
            String sql = "SELECT email FROM user WHERE email=? AND passwd=?";
            PreparedStatement pst = Main.getConnection().prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                rs.close();
                pst.close();
                return true;
            }
            else {
                rs.close();
                pst.close();
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private void setRetangles() {
        rect.setX(0);
        rect.setY(0);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        rect.setFill(Color.rgb(0, 0, 0, 0.55));
        rect.setStroke(Color.rgb(255, 255, 255, 0.9));
        rect.setStrokeWidth(1.5);
    }
    
    private void setTexts() {
        user.setFill(Color.WHITE);
        user.setFont(Font.font("SanSerif", 20));
    }
    
    private void setFields() {
        username.setFont(Font.font("SanSerif", 20));
        username.setPromptText("Username");

        password.setFont(Font.font("SanSerif", 20));
        password.setPromptText("Password");

        login.setFont(Font.font("SanSerif", 15));

        username.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                if(loginCheck(username.getText(), password.getText())) {
                    Main.getPrimaryStage().setScene(new Root().rootShow());
                    Main.getPrimaryStage().centerOnScreen();
                }
                else {
                    label.setText("Login not successful");
                }
            }
        });

        password.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                if(loginCheck(username.getText(), password.getText())) {
                    Main.getPrimaryStage().setScene(new Root().rootShow());
                    Main.getPrimaryStage().centerOnScreen();
                }
                else {
                    label.setText("Login not successful");
                }
            }
        });
    }
    
    private void setLabels() {
        label.setFont(Font.font("SanSerif", 20));
        label.setTextFill(Color.RED);
    }
    
    private void setButtons() {
        login.setOnAction(e ->{
            if(loginCheck(username.getText(), password.getText())) {
                Main.getPrimaryStage().setScene(new Root().rootShow());
                Main.getPrimaryStage().centerOnScreen();
            }
            else {
                label.setText("Login not successful");
            }
        });
    }
    
    private void setPositions() {
        vbox.setPadding(new Insets(10, 0, 0, 10));

        hbox.getChildren().addAll(label, login);
        hbox.setAlignment(Pos.CENTER_RIGHT);

        vbox.getChildren().addAll(user, username, password, hbox);

        root.getChildren().addAll(rect, vbox);
    }
}
