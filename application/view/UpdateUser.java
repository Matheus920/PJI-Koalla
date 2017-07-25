
package application.view;

import application.Main;
import application.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateUser {
    private final AnchorPane anchorPane = new AnchorPane();
    private final Scene scene = new Scene(anchorPane, 400, 500);
    private final TextField fn = new TextField();
    private final TextField ln = new TextField();
    private final TextField un = new TextField();
    private final PasswordField pw = new PasswordField();
    private final DatePicker dob = new DatePicker();
    private final RadioButton male = new RadioButton("Male");
    private final RadioButton female = new RadioButton("Female");
    private final Label label = new Label("Edit User");
    private final Button edit = new Button("Edit");
    private final VBox vbox = new VBox(10);
    private final Stage stage = new Stage();
    private final HBox hbox = new HBox(10);
    private final Button cancel = new Button("Calcel");
            
    private User user;
    private String gender;
    
    public void updateUserShow(User user) {
        this.user = user;
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit user");
        setFields();
        setRadioButtons();
        setLabels();
        setButtons();
        setPositions();
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    private void setButtons() {
        edit.setFont(Font.font("SanSerif", 15));
        edit.setOnAction(e->{
            updateUserConfirmation();
        });
        cancel.setFont(Font.font("SanSerif", 15));
        cancel.setOnAction(e->{
            stage.close();
        });
    }
    
    private void setLabels() {
        label.setFont(Font.font("SanSerif", 20));
    }
    
    private void setFields() {
        fn.setFont(Font.font("SanSerif", 20));
        fn.setPromptText("First Name");
        fn.setText(user.getFirstName());
        fn.setMaxWidth(300);
        fn.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                updateUserAction();
            }
        });
        
        ln.setFont(Font.font("SanSerif", 20));
        ln.setPromptText("Last Name");
        ln.setText(user.getLastName());
        ln.setMaxWidth(300);
        ln.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                updateUserAction();
            }
        });
        
        un.setFont(Font.font("SanSerif", 20));
        un.setPromptText("User Name");
        un.setText(user.getUserName());
        un.setMaxWidth(300);
        un.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                updateUserAction();
            }
        });
        
        pw.setFont(Font.font("SanSerif", 20));
        pw.setPromptText("Password");
        pw.setText(user.getPassword());
        pw.setMaxWidth(300);
        pw.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                updateUserConfirmation();
            }
        });
        
        dob.setStyle("-fx-font-size:20");
        dob.setPromptText("Date of Birth");
        dob.getEditor().setText(user.getDob());
        dob.setMaxWidth(300);
        dob.setEditable(false);
    }
    
    private void updateUserConfirmation() {
        if(validateFields()){
            Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really update user?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Update User");
            alert.setHeaderText(null);
            alert.showAndWait();
            if(alert.getResult().equals(ButtonType.YES)) {
                updateUserAction();
                stage.close();
            }
            else {
                alert.close();
            }
        }
    }
    
   
    private boolean validateFields() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validate Fields");
        alert.setHeaderText(null);
        
        if(fn.getText().isEmpty() ||
           ln.getText().isEmpty() ||
           un.getText().isEmpty() ||
           pw.getText().isEmpty() ||
           dob.getEditor().getText().isEmpty()) {
            
            alert.setContentText("Please enter into the fields");
            alert.showAndWait();
           
            return false;
        } 
        
        if(!(male.isSelected() || female.isSelected())) {
            alert.setHeaderText(null);
            alert.setContentText("Please select the gender");
            alert.showAndWait();
            return false;
            
        }
        
        return true;
    }
    
    private void updateUserAction() {
        try{
            String sql = "UPDATE user "
                    + "SET first_name=?, last_name=?, email=?, passwd=?, dob=?, gender=? "
                    + "WHERE id=" + user.getId();
            PreparedStatement pst = Main.getConnection().prepareStatement(sql);
            pst.setString(1, fn.getText());
            pst.setString(2, ln.getText());
            pst.setString(3, un.getText());
            pst.setString(4, pw.getText());
            pst.setString(5, ((TextField)dob.getEditor()).getText());
            pst.setString(6, gender);
            
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setRadioButtons() {
        ToggleGroup gender = new ToggleGroup();
        
        male.setToggleGroup(gender);
        female.setToggleGroup(gender);
       
        if("Male".equals(user.getGender())) {
            male.setSelected(true);
        } else if("Female".equals(user.getGender())) {
            female.setSelected(true);
        } else {
            male.setSelected(false);
            female.setSelected(false);
        }
        
        male.setOnAction(e -> {
            this.gender = male.getText();
        });
        female.setOnAction(e -> {
            this.gender = female.getText();
        });
    }
    
    private void setPositions() {
        hbox.getChildren().addAll(edit, cancel);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(label, fn, ln, un, pw, dob, female, male, hbox);
        anchorPane.getChildren().add(vbox);
    }
}
