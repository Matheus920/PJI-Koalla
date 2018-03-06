package app.view;

import app.Main;
import app.control.LoginController;
import app.control.interfaces.PrivilegeTypeInterface;
import app.data.UserDAOInterface;
import app.model.Event;
import app.model.Login;
import app.model.User;
import app.view.viewcontrollers.MaskField;
import java.io.File;
import java.time.LocalDate;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

public class SpeakerView {
    private final SplitPane splitPane = new SplitPane();
    private final LoginController loginController = new LoginController();
    private PrivilegeTypeInterface privilege;
    private UserDAOInterface users;
    private ListView<String> listView;
    private ObservableList<User> userData;
    private String imagePath;
    private String[] fields = {"Nome", "Email", "Data de nascimento", "Instituição", "Senha"};
    private final static TextField name = new TextField();
    private final static DatePicker dob = new DatePicker();
    private final static TextField institution = new TextField();
    private final static TextField email = new TextField();
    private final static PasswordField password = new PasswordField();
    private final Label lblOk = new Label("");
    
    public SpeakerView(PrivilegeTypeInterface privilege, UserDAOInterface users) {
        Main.refreshBottom();
        this.privilege = privilege;
        this.users = users;
        userData = FXCollections.observableArrayList(users.getSpeakers());
        setLeft();
        splitPane.setDividerPositions(0.13f);
    }
    
    public SplitPane speakerShow() {
        return splitPane;
    }
    
    private void setLeft() {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        Label lblMembers = new Label("Lista de palestrante");
        
        vbox2.getChildren().addAll(lblMembers);
        
        ObservableList<String> usersDataString = FXCollections.observableArrayList();
        
        for(User a : userData){
            usersDataString.add(a.getNome());
        }
        
        listView = new ListView<>(usersDataString);
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
        
        listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obsv, String oldv, String nv) -> {
            if(nv == null) return;
            if(listView.getSelectionModel().getSelectedItem() != null){
                setRight(users.getUserById(userData.get(listView.getSelectionModel().getSelectedIndex()).getId()));
            }
            if(oldv == null) return;
        });
        
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
    
    private void setRight(User user) {
        if(splitPane.getItems().size() > 1){
            splitPane.getItems().remove(1);
            splitPane.setDividerPositions(0.13f);
        }
        
       
        password.setVisible(false);
        ImageView imageView = new ImageView();
        
        if(user!=null){
            name.setText(user.getNome());
            dob.setValue(user.getDataNascimento());
            institution.setText(user.getInstituicao());
            email.setText(user.getLogin().getEmail());
            password.setText(user.getLogin().getSenha());
            
            if(user.getCaminhoImagem() != null && (!user.getCaminhoImagem().equals(""))){
                imageView.setImage(new Image("file:" + user.getCaminhoImagem()));
            }
        }else{
            name.setText("");
            institution.setText("");
            ((TextField)dob.getEditor()).setText("");
            email.setText("");
            password.setText("");

        }
        
        AnchorPane anchorPane = new AnchorPane();
        VBox vbox1 = getBottom(user);
        VBox vbox2 = new VBox(10);
        StackPane stackPane = new StackPane();

        dob.setPromptText("Data de nascimento");
        name.setPromptText("Nome");
        institution.setPromptText("Instituição");
        email.setPromptText("Email");
        password.setPromptText("Senha");
        
        name.setEditable(false);
        dob.setEditable(false);
        institution.setEditable(false);
        email.setEditable(false);
        password.setEditable(false);
        
        MaskField.maxLength(name, 255);
        MaskField.maxLength(institution, 255);
        MaskField.maxLength(email, 255);
        MaskField.maxLength(password, 255);
        
        Main.setStyleTextField(name, institution, email);
        dob.setStyle("-fx-font-size:18;-fx-font-family:Segoe UI");
        lblOk.setStyle("-fx-text-fill: red;");
        
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        
        stackPane.getChildren().add(imageView);
        stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        
        vbox2.getChildren().addAll(lblOk, name, email, dob, institution, password);
        anchorPane.getChildren().addAll(vbox2, stackPane, vbox1);
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.ADMIN) {
            password.setVisible(true);
            HBox hbox1 = new HBox(5);
            Button save = new Button("Salvar");
            
            Main.setStyleLabeled(save);
            
         
            save.setCursor(Cursor.HAND);
            
            name.setEditable(true);
            institution.setEditable(true);
            email.setEditable(true);

            
            if(imageView.getImage() == null) {
                stackPane.getChildren().add(new Label("Insira uma imagem"));
            } else {
                Button del = new Button("X");
                del.setFont(Font.font("Segoe UI", 12));
                del.setCursor(Cursor.HAND);
                del.setOnAction(e->{
                    imageView.setImage(null);
                });

                HBox hbox = new HBox();
                hbox.setAlignment(Pos.TOP_RIGHT);
                hbox.getChildren().add(del);
                hbox.setPadding(new Insets(5, 5, 0, 0));
                hbox.setVisible(false);
                stackPane.getChildren().add(hbox);

                stackPane.setOnMouseEntered(e->{
                    hbox.setVisible(true);
                });

                stackPane.setOnMouseExited(e->{
                    hbox.setVisible(false);
                });
            }

            imageView.imageProperty().addListener((obv, nv, ov) ->{
                ImageView temp = imageView;
                if(ov == null) {
                    stackPane.getChildren().remove(1);
                    stackPane.getChildren().add(new Label("Insira uma imagem"));
                    imagePath = "";
                } 
            });
            
            stackPane.setCursor(Cursor.HAND);

            stackPane.setOnMouseClicked(e->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Escolha sua imagem de perfil");
                fileChooser.setInitialDirectory(new File("C:/Users/" + System.getProperty("user.name") + "/pictures"));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem [*.png, *.jpg, *.bmp, *.jpeg]", "*.png", "*.jpg", "*.bmp", "*.jpeg"));
                File selectedFile = fileChooser.showOpenDialog(null);
                imagePath = selectedFile.getPath();
                    
                
                if(selectedFile != null) {
                    System.out.println();
                    imageView.setImage(new Image("file:" + imagePath));
                    if(stackPane.getChildren().size() > 1) stackPane.getChildren().remove(1);
                }
                else {
                    imagePath = user.getCaminhoImagem();
                }
            });
            
            if(imagePath == null){
                imagePath = user.getCaminhoImagem();
            }
            
            hbox1.setAlignment(Pos.TOP_RIGHT);
        
            save.setOnAction(e-> {
                if(listView.getSelectionModel().getSelectedItem() != null){
                
                lblOk.setText(invalidField());
               
                if(lblOk.getText().equals("")){
                    if(loginController.emailExists(email.getText()) && !(email.getText().equals(user.getLogin().getEmail()))){
                        lblOk.setText("O email já está cadastrado no sistema");
                    }
                    else {
                        Login login = new Login(user.getLogin().getId(), email.getText(), password.getText(), 1); 

                        loginController.updateLogin(login);

                        user.setNome(name.getText());
                        user.setDataNascimento(dob.getValue());
                        user.setLogin(login);
                        user.setCaminhoImagem(imagePath);

                        user.setInstituicao(institution.getText());
                        int index = listView.getSelectionModel().getSelectedIndex();
                        listView.getItems().set(index, name.getText());
                        this.users.updateUser(user);
                        setRight(user);
                    }
                }
                }
            });
            
         
            
            hbox1.getChildren().addAll(save);
            vbox2.getChildren().add(hbox1);
        }
        
        
        AnchorPane.setTopAnchor(vbox2, 10.0);
        AnchorPane.setLeftAnchor(vbox2, 10.0);
        
        AnchorPane.setTopAnchor(stackPane, 20.0);
        AnchorPane.setRightAnchor(stackPane, 20.0);
        
        AnchorPane.setBottomAnchor(vbox1, 1.0);
        AnchorPane.setRightAnchor(vbox1, 0.0);
        AnchorPane.setLeftAnchor(vbox1, 0.0);
        
        Main.setBackgroundWhite(anchorPane);
        
        splitPane.getItems().add(anchorPane);
    }
    
    private VBox getBottom(User user) {
        ObservableList<Event> eventsData = FXCollections.observableArrayList(users.getAllEventsBySpeaker(user));
        ObservableList<String> eventsDataString = FXCollections.observableArrayList();
        
        for(Event a : eventsData){
            eventsDataString.add(a.getTitulo());
        }
        
        ListView listView = new ListView(eventsDataString);
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.setCursor(Cursor.HAND);
        
        listView.setOnMouseClicked(e->{
            if(listView.getSelectionModel().getSelectedItem() != null){
                if(e.getClickCount() == 2){
                    Main.symposiumShow(eventsData.get(listView.getSelectionModel().getSelectedIndex()));
                }
            }
        });
        
        Label lblTitle = new Label("Participações em simpósios");
        VBox vbox = new VBox(5);
        
        lblTitle.setFont(Font.font("Segoe UI", 18));
        
        vbox.setMaxHeight(250);
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(lblTitle, listView);
        
        return vbox;
    }
    
    private String invalidField(){
        if(name.getText().equals("") || name.getText() == null){
            return "* Campo " + fields[0] + " está vazio";
        }else if(email.getText().equals("") || email.getText() == null){
            return "* Campo " + fields[1] + " está vazio";
        } else if((((TextField)dob.getEditor()).getText() == null || ((TextField)dob.getEditor()).getText().equals(""))){
            return "* Campo " + fields[2] + " está vazio";
        }else if(institution.getText().equals("") || institution.getText() == null){
            return "* Campo " + fields[3] + " está vazio";
        }else if(password.getText().equals("") || password.getText() == null){
            return "* Campo " + fields[4] + " está vazio";
        }else if((LocalDate.now().getYear() -  dob.getValue().getYear()) < 14){
            return "Data de nascimento inválida";
        }
        return "";
    }
    
}
