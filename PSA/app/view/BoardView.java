package app.view;

import app.Main;
import app.control.CRUDEvent;
import app.control.LoginController;
import app.control.interfaces.PrivilegeTypeInterface;
import app.data.BoardDAOInterface;
import app.model.Board;
import app.model.Event;
import app.model.Login;
import app.view.viewcontrollers.MaskField;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
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

public class BoardView {
    private final SplitPane splitPane = new SplitPane();
    private final LoginController loginController = new LoginController();
    private PrivilegeTypeInterface privilege;
    private BoardDAOInterface board;
    private ListView<String> listView;
    private ObservableList<Board> boardData;
    private ObservableList<String> boardDataString;
    private String imagePath;
    private String[] fields = {"Prontuário", "Nome", "Data de Nascimento", "Cargo", "Email", "Senha"};
    private final TextField id = new TextField();
    private final TextField name = new TextField();
    private final DatePicker dob = new DatePicker();
    private final TextField function = new TextField();
    private final TextField email = new TextField();
    private final PasswordField password = new PasswordField();
    private final Label lblOk = new Label("");
    
    public BoardView(PrivilegeTypeInterface privilege, BoardDAOInterface board) {
        Main.refreshBottom();
        this.privilege = privilege;
        this.board = board;
        boardData = FXCollections.observableArrayList(board.getAllBoard());
        setLeft();
        splitPane.setDividerPositions(0.13f);
    }
    
    public SplitPane boardShow() {
        return splitPane;
    }
    
    private void setLeft() {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        Label lblMembers = new Label("Lista de membros");
        Button btnAdd = new Button("Add...");
        btnAdd.setCursor(Cursor.HAND);
        vbox2.getChildren().add(lblMembers);
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.ADMIN) vbox2.getChildren().addAll(btnAdd);
        
        VBox.setMargin(btnAdd, new Insets(5, 0, 0, 20));
        
        btnAdd.setOnAction(e->{
            setRight(null);
        });
        
        
        boardDataString = FXCollections.observableArrayList();
        
        for(Board a : boardData){
            boardDataString.add(a.getNome());
        }
        
        listView = new ListView<>(boardDataString);
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
        
        listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obsv, String oldv, String nv) -> {
            if(nv == null) return;
            if(listView.getSelectionModel().getSelectedItem() != null){
                setRight(board.getBoardById(boardData.get(listView.getSelectionModel().getSelectedIndex()).getId()));
            }
            lblOk.setText("");
            
            if(oldv == null) return;
        });
       
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
    
        
    private void setRight(Board board) {        
        if(splitPane.getItems().size() > 1){
            splitPane.getItems().remove(1);
            splitPane.setDividerPositions(0.13f);
        }
            password.setVisible(false);
            ImageView imageView = new ImageView();
        
        if(board != null){
            id.setText(board.getProntuario());
            name.setText(board.getNome());
            dob.setValue(board.getDataNascimento());
            function.setText(board.getFuncao());
            email.setText(board.getLogin().getEmail());
            password.setText(board.getLogin().getSenha());
            if(board.getCaminhoDaImagem() != null && (!board.getCaminhoDaImagem().equals(""))){
                imageView.setImage(new Image("file:" + board.getCaminhoDaImagem()));
            }
        }
        else{
            id.setText("");
            name.setText("");
            dob.setValue(null);
            function.setText("");
            email.setText("");
            password.setText("");
        }
        
        AnchorPane anchorPane = new AnchorPane();
        VBox vbox1 = getBottom(board);
        VBox vbox2 = new VBox(10);
        StackPane stackPane = new StackPane();

        dob.setPromptText("Data de nascimento");
        id.setPromptText("Prontuário");
        name.setPromptText("Nome");
        function.setPromptText("Cargo");
        email.setPromptText("Email");
        password.setPromptText("Senha");
        
        MaskField.maxLength(id, 255);
        MaskField.maxLength(name, 255);
        MaskField.maxLength(function, 255);
        MaskField.maxLength(email, 255);
        MaskField.maxLength(password, 255);
        
        id.setEditable(false);
        name.setEditable(false);
        dob.setEditable(false);
        function.setEditable(false);
        email.setEditable(false);
        password.setEditable(false);
        
        Main.setStyleTextField(id, name, function, email, password);
        dob.setStyle("-fx-font-size:18;-fx-font-family:Segoe UI");
        lblOk.setStyle("-fx-text-fill: #ff0000");
        
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        
        stackPane.getChildren().add(imageView);
        stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        
        vbox2.getChildren().addAll(lblOk, id, name, dob, function, email, password);
        anchorPane.getChildren().addAll(vbox2, stackPane, vbox1);
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.ADMIN) {
            password.setVisible(true);
            HBox hbox1 = new HBox(5);
            Button delete = new Button("Remover");
            Button save = new Button("Salvar");
            
            Main.setStyleLabeled(delete, save);
            
            delete.setCursor(Cursor.HAND);
            save.setCursor(Cursor.HAND);
            
            
            name.setEditable(true);
            id.setEditable(true);
            function.setEditable(true);
            email.setEditable(true);
            password.setEditable(true);

            
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
                if(ov == null){
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
                    imageView.setImage(new Image("file:" + imagePath));
                    if(stackPane.getChildren().size() > 1) stackPane.getChildren().remove(1);
                } 
                else{
                    imagePath = board.getCaminhoDaImagem();
                }
            });
            
            if(imagePath == null){
                if(board != null){
                imagePath = board.getCaminhoDaImagem();
                }
            }
            
            
            hbox1.setAlignment(Pos.TOP_RIGHT);
        
            save.setOnAction(e-> {
                if(board == null) {
                    
                    lblOk.setText(invalidField());
                    
                    if(lblOk.getText().equals("")){
                        if(this.board.exists(id.getText())){
                            lblOk.setText("* O prontuário já está cadastrado.");
                        } else if(loginController.emailExists(email.getText())){
                            lblOk.setText("* O email já está cadastrado");
                        } else {
                            Login login = new Login(email.getText(), password.getText(), 4);
                            Login loginId = loginController.addLogin(login);

                            boardData.add(this.board.addBoard(new Board(id.getText(), dob.getValue(), function.getText(), loginId, imagePath, name.getText())));
                            setRight(new Board(id.getText(), dob.getValue(), function.getText(), loginId, imagePath, name.getText()));
                            listView.getItems().add(name.getText());
                        }
                    }
                }else{
                    
                    lblOk.setText(invalidField());
                    
                    if(lblOk.getText().equals("")){
                        if(this.board.exists(id.getText()) && !(id.getText().equals(board.getProntuario()))){
                            lblOk.setText("* O prontuário já está cadastrado");
                        } else if (loginController.emailExists(email.getText()) && !(email.getText().equals(board.getLogin().getEmail()))){
                            lblOk.setText("* O email já está cadastrado");
                        } else {
                            Login login1 = new Login(board.getLogin().getId(), email.getText(), password.getText(), 4);
                            loginController.updateLogin(login1);

                            board.setProntuario(id.getText());
                            board.setFuncao(function.getText());
                            board.setLogin(login1);
                            board.setNome(name.getText());
                            board.setDataNascimento(dob.getValue());
                            board.setCaminhoDaImagem(imagePath);
                            int index = listView.getSelectionModel().getSelectedIndex();
                            listView.getItems().set(index, name.getText());
                            this.board.updateBoard(board);
                            setRight(board);
                        }
                    }  
                }
            });
            
            delete.setOnAction(e->{
                int index = listView.getSelectionModel().getSelectedIndex();
                listView.getItems().remove(index);
                this.board.deleteBoard(board);
                
                boardData = FXCollections.observableArrayList(this.board.getAllBoard());
                boardDataString.clear();
                
                
                for(Board a : boardData){
                    boardDataString.add(a.getNome());
                }
                
                
                listView.setItems(boardDataString);
            });
            
            hbox1.getChildren().addAll(delete, save);
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
    
    private VBox getBottom(Board board) {
        
        
        ObservableList<Event> eventData = FXCollections.observableArrayList(this.board.getAllEventsByBoard(board));
        ObservableList<String> eventDataString = FXCollections.observableArrayList();
        
        for(Event a : eventData){
            eventDataString.add(a.getTitulo());
        }
        
        ListView listView = new ListView(eventDataString);
        
        listView.setStyle("-fx-control-inner-background-alt: white;");
        listView.setCursor(Cursor.HAND);
        
        listView.setOnMouseClicked(e->{
            if(listView.getSelectionModel().getSelectedItem() != null){
                if(e.getClickCount() == 2){
                    Main.symposiumShow(eventData.get(listView.getSelectionModel().getSelectedIndex()));
                }
            }
        });
        
        Label lblTitle = new Label("Eventos concebidos");
        VBox vbox = new VBox(5);
        
        lblTitle.setFont(Font.font("Segoe UI", 18));
        
        vbox.setMaxHeight(200);
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(lblTitle, listView);
        
        return vbox;
    }
    
    private String invalidField(){
        if(id.getText().equals("") || id.getText() == null){
            return "* Campo " + fields[0] + " está vazio";
        } else if(name.getText().equals("") || name.getText() == null){
            return "* Campo " + fields[1] + " está vazio";
        } else if((((TextField)dob.getEditor()).getText() == null || ((TextField)dob.getEditor()).getText().equals(""))){
            return "* Campo " + fields[2] + " está vazio";
        }else if(function.getText().equals("") || function.getText() == null){
            return "* Campo " + fields[3] + " está vazio";
        }else if(email.getText().equals("") || email.getText() == null){
            return "* Campo " + fields[4] + " está vazio";
        }else if(password.getText().equals("") || password.getText() == null){
            return "* Campo " + fields[5] + " está vazio";
        }else if((LocalDate.now().getYear() -  dob.getValue().getYear()) < 19){
            return "Data de nascimento inválida";
        }
        return "";
    }
    
}
