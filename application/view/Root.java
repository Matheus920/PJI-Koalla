package application.view;

import application.Main;
import application.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Root {
    private final BorderPane layout = new BorderPane();
    private final Scene scene = new Scene(layout, 1400, 650, Color.rgb(0,0,0,0));
    private final Button logout = new Button("Logout");
    private final VBox fields = new VBox(10);
    private final Label label = new Label("Create New User");
    private final TextField fn = new TextField();
    private final TextField ln = new TextField();
    private final TextField un = new TextField();
    private final PasswordField pw = new PasswordField();
    private final DatePicker dob = new DatePicker();
    private final Button save = new Button("Save");
    private final TableView<User> table = new TableView<>();
    private final ObservableList<User> data = FXCollections.observableArrayList();
    private final TableColumn<User, String> column1 = new TableColumn<>("ID");
    private final TableColumn<User, String> column2 = new TableColumn<>("First Name");
    private final TableColumn<User, String> column3 = new TableColumn<>("Last Name");
    private final TableColumn<User, String> column4 = new TableColumn<>("User Name");
    private final TableColumn<User, String> column5 = new TableColumn<>("Password");
    private final TableColumn<User, String> column6 = new TableColumn<>("DOB");
    private final TableColumn<User, String> column7 = new TableColumn<>("Gender");
    private final Button load = new Button("Load Table");
    private final HBox hbox = new HBox(10);
    private final ObservableList options = FXCollections.observableArrayList();
    private final ComboBox comboBox = new ComboBox(options);
    private final ListView list = new ListView(options);
    private final Button delete = new Button("Delete User");
    private final RadioButton male = new RadioButton("Male");
    private final RadioButton female = new RadioButton("Female");
    private final CheckBox checkBox1 = new CheckBox("Playing");
    private final CheckBox checkBox2 = new CheckBox("Singing");
    private final CheckBox checkBox3 = new CheckBox("Dancing");
    private String gender;
    
    public Scene rootShow() {
        setRadioButtons();
        setButtons();
        setFields();
        setLabels();
        setColumns();
        setComboBoxes();
        setLists();
        setPositions();
        setOptions();
        setCheckBoxes();
        setDoubleClickTableItem();
        return scene;
    }
    
    private void setCheckBoxes() {
        
    }
    
    private void setDoubleClickTableItem() {
        table.setRowFactory(e -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())) {
                    new UpdateUser().updateUserShow(row.getItem());
                    refreshTable();
                }
            });
            return row;
        });
    }
    
    private void setRadioButtons() {
        ToggleGroup gender1 = new ToggleGroup();
        male.setToggleGroup(gender1);
        
        male.setOnAction(e -> { 
            gender = male.getText();
        });
        
        female.setToggleGroup(gender1);
        female.setOnAction(e -> {
            gender = female.getText();
        });
    }
    
    private void setButtons() {
        logout.setFont(Font.font("SanSerif", 15));
        logout.setOnAction(e ->{
            Main.getPrimaryStage().setScene(new Login().loginShow());
            Main.getPrimaryStage().centerOnScreen();
        });
        
        save.setFont(Font.font("SanSerif", 15));
        save.setOnAction(e->{
            saveUser();
        });
        
        load.setFont(Font.font("SanSerif", 15));
        load.setOnAction(e ->{
            setData();
        });
        
        delete.setFont(Font.font("SanSerif", 15));
        delete.setOnAction(e ->{
            if(table.getSelectionModel().getSelectedItem() != null) {
                deleteUser(table.getSelectionModel().getSelectedItem().getId());
            }
        });
    } 
    
    private User findUser(int id) {
        try {
            String sql = "SELECT * FROM user WHERE id=?";
            PreparedStatement pst = Main.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return new User(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));
        } catch (SQLException ex) {
            Logger.getLogger(Root.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private void deleteUser(int id) {
        User user = findUser(id);
        String formatUser = "\nFirst Name: " + user.getFirstName() + 
                            "\nLast Name: " + user.getLastName() +
                            "\nUser Name: " + user.getUserName() +
                            "\nDate of Birth: " + user.getDob();
                            
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to exclude user?" + formatUser, ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText(null);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES) {
            try{
                String sql = "DELETE FROM user WHERE id=?";
                PreparedStatement pst = Main.getConnection().prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
                refreshTable();
                pst.close();
            } catch (SQLException ex) {
            Logger.getLogger(Root.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.close();
        }
        
    }
    
    private boolean validateFields() {
        if(fn.getText().isEmpty() ||
           ln.getText().isEmpty() ||
           un.getText().isEmpty() ||
           pw.getText().isEmpty() ||
           dob.getEditor().getText().isEmpty()) {
           
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please enter into the fields");
            alert.showAndWait();
           
           return false;
        } 
        
        if(!(male.isSelected() || female.isSelected())) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please select the gender");
            alert.showAndWait();
            return false;
            
        }
        
        return true;
    }
    
    private  void setFields() {
        fn.setFont(Font.font("SanSerif", 20));
        fn.setPromptText("First Name");
        fn.setMaxWidth(300);
        fn.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                saveUser();
            }
        });
        
        
        ln.setFont(Font.font("SanSerif", 20));
        ln.setPromptText("Last Name");
        ln.setMaxWidth(300);
        ln.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                saveUser();
            }
        });
        
        un.setFont(Font.font("SanSerif", 20));
        un.setPromptText("User Name");
        un.setMaxWidth(300);
        un.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                saveUser();
            }
        });
        
        pw.setFont(Font.font("SanSerif", 20));
        pw.setPromptText("Password");
        pw.setMaxWidth(300);
        pw.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                saveUser();
            }
        });
        
        dob.setPromptText("Date of Birth");
        dob.setMaxWidth(300);
        dob.setStyle("-fx-font-size:20");
        dob.setEditable(false);
    }
    
    private  void saveUser() {
        if(validateFields()) {
            try{
                String sql = "INSERT INTO user(first_name, last_name, email, passwd, dob, gender) VALUES (?,?,?,?,?,?)";
                PreparedStatement pst = Main.getConnection().prepareStatement(sql);

                pst.setString(1, fn.getText());
                pst.setString(2, ln.getText());
                pst.setString(3, un.getText());
                pst.setString(4, pw.getText());
                pst.setString(5, ((TextField)dob.getEditor()).getText());
                pst.setString(6, gender);
                
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("User has been created");
                alert.showAndWait();
                
                refreshFields();
                
                pst.close();
                setOptions();
                if(!data.isEmpty())
                    refreshTable();
                
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private  void refreshFields() {
        fn.clear();
        ln.clear();
        un.clear();
        pw.clear();
        dob.setValue(null);
        dob.getEditor().setText(null);
        male.setSelected(false);
        female.setSelected(false);
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
        checkBox3.setSelected(false);
    }
    
    private  void refreshTable() {
        data.clear();
        setData();
    }
    
    private  void setLabels() {
        label.setFont(Font.font("SanSerif", 20));
    }
    
    private  void setColumns() {
        column1.setMinWidth(50);
        column1.setCellValueFactory(e -> e.getValue().idProperty().asString());
        
        column2.setMinWidth(150);
        column2.setCellValueFactory(e -> e.getValue().firstNameProperty());
        
        column3.setMinWidth(150);
        column3.setCellValueFactory(e -> e.getValue().lastNameProperty());
        
        column4.setMinWidth(200);
        column4.setCellValueFactory(e -> e.getValue().userNameProperty());
        
        column5.setMinWidth(150);
        column5.setCellValueFactory(e -> e.getValue().passwordProperty());
        
        column6.setMinWidth(100);
        column6.setCellValueFactory(e -> e.getValue().dobProperty());
        
        column7.setMinWidth(100);
        column7.setCellValueFactory(e -> e.getValue().genderProperty());
        
        
    }
    
    private  void setComboBoxes() {
        comboBox.setMaxHeight(30);
        comboBox.setOnAction(e ->{
            selectedName((String)comboBox.getSelectionModel().getSelectedItem());
        });
    }
    
    private  void setLists() {
        list.setMaxWidth(100);
        list.setOnMouseClicked(e -> {
            selectedName((String)list.getSelectionModel().getSelectedItem());
        });
    }
    
    private  void selectedName(String item) {
        try {
                String sql = "SELECT * FROM user WHERE first_name=?";
                PreparedStatement pst = Main.getConnection().prepareStatement(sql);
                pst.setString(1, item);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    fn.setText(rs.getString(2));
                    ln.setText(rs.getString(3));
                    un.setText(rs.getString(4));
                    pw.setText(rs.getString(5));
                     ((TextField)dob.getEditor()).setText(rs.getString(6));
                     
                     if("Male".equals(rs.getString(7))) {
                         male.setSelected(true);
                     } else if("Female".equals(rs.getString(7))) {
                         female.setSelected(true);
                     } else {
                         male.setSelected(false);
                         female.setSelected(false);
                     }
                }
                pst.close();
                rs.close();
            }catch(SQLException e1) {
                 System.err.println(e1.getMessage());
            }
    }
    
    private  void setPositions() {
        
        fields.getChildren().addAll(label, fn, ln, un, pw, dob, female, male, checkBox1, checkBox2, checkBox3, save);
        
        table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7);
        table.setTableMenuButtonVisible(true);
        
        hbox.getChildren().addAll(load, delete, comboBox);
        
        layout.setTop(logout);
        layout.setRight(table);
        layout.setCenter(fields);
        layout.setBottom(hbox);
        layout.setLeft(list);
        
        BorderPane.setAlignment(logout, Pos.TOP_RIGHT);
        BorderPane.setMargin(logout, new Insets(10));
        BorderPane.setMargin(table, new Insets(0, 10, 10, 0));
        BorderPane.setMargin(fields, new Insets(0,0,0,20));
        BorderPane.setMargin(hbox, new Insets(10, 0, 10, 10));
        BorderPane.setMargin(list, new Insets(10));
        
        
    }
    
    private  void setOptions() {
        try{
            options.clear();
            String sql = "SELECT first_name FROM user";
            PreparedStatement pst = Main.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                options.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private  void setData() {
        if(data.isEmpty()) {
            try{
                String sql = "SELECT * FROM user";
                PreparedStatement pst = Main.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                data.clear();
                while(rs.next()) {
                    data.add(new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7)
                    ));
                }
                table.setItems(data);
                rs.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}