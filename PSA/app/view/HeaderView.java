package app.view;

import app.Main;
import app.control.interfaces.PrivilegeTypeInterface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HeaderView {
    private final Label title = new Label();
    private final Label subtitle = new Label();
    private final Button login = new Button("Login");
    private final AnchorPane anchorPane = new AnchorPane();
    private final VBox vbox = new VBox(5);
    private final HBox hbox = new HBox(10);
    private final ImageView koallaImage = new ImageView(new Image(getClass().getResourceAsStream("koala.png")));
    private final Button logout = new Button("Sair");
    
    private PrivilegeTypeInterface privilege;
    
    public HeaderView(PrivilegeTypeInterface privilege) {
        this.privilege = privilege;
        setTitle("Título");
        setSubtitle("Aqui vai a descrição do título");
        setLabels();
        setImages();
        setButtons();
        
        vbox.getChildren().addAll(this.title, this.subtitle);
        hbox.getChildren().addAll(koallaImage, vbox);
        HBox.setMargin(vbox, new Insets(10, 0, 0, 10));

        anchorPane.getChildren().addAll(hbox, login);
        
        
         if(Main.getPrivilege().getPrivilegeType() != 3){
            switch(Main.getPrivilege().getPrivilegeType()){
                case PrivilegeTypeInterface.BOARD:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.EVALUATOR:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.USER:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.ADMIN:
                    anchorPane.getChildren().set(1, logout);
                    break;
            }
            AnchorPane.setRightAnchor(logout, 20.0);
            AnchorPane.setTopAnchor(logout, 20.0);
        }else{

            AnchorPane.setRightAnchor(login, 20.0);
            AnchorPane.setTopAnchor(login, 20.0);
        }
       
        
       
    
        AnchorPane.setLeftAnchor(hbox, 10.0);
    }
    
    public HeaderView(PrivilegeTypeInterface privilege, String title, String subtitle) {
        this.privilege = privilege;
        setTitle(title);
        
        setLabels();
        setImages();
        setButtons();
        
        vbox.getChildren().addAll(this.title, this.subtitle);
        hbox.getChildren().addAll(koallaImage, vbox);
        HBox.setMargin(vbox, new Insets(10, 0, 0, 10));

        anchorPane.getChildren().addAll(hbox, login);
       

        AnchorPane.setLeftAnchor(hbox, 10.0);
        
        if(subtitle == null) {
            vbox.getChildren().remove(1);
            HBox.setMargin(vbox, new Insets(30,0,0,0));
            hbox.spacingProperty().bind(Main.getPrimaryStage().widthProperty().divide(2).subtract(80+(((title.length()*11)/2))));
        } else setSubtitle(subtitle);
        
         if(Main.getPrivilege().getPrivilegeType() != 3){
            switch(Main.getPrivilege().getPrivilegeType()){
                case PrivilegeTypeInterface.BOARD:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.EVALUATOR:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.USER:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.ADMIN:
                    anchorPane.getChildren().set(1, logout);
                    break;
            }
            AnchorPane.setRightAnchor(logout, 20.0);
            AnchorPane.setTopAnchor(logout, 20.0);
        }else{

            AnchorPane.setRightAnchor(login, 20.0);
            AnchorPane.setTopAnchor(login, 20.0);
        }
        
        
    }
    
    public AnchorPane headerShow() {
        return anchorPane;
    }
 
    private void setImages() {
        koallaImage.setPreserveRatio(true);
        koallaImage.setFitHeight(90);
        koallaImage.setFitWidth(90);

    }
    
    private void setLabels(){
        this.title.setFont(Font.font("Century Gothic", FontWeight.BLACK, 24));
        this.subtitle.setFont(Font.font("Century Gothic", 16));
    }
    
    private void setButtons() {
       login.setFont(Font.font("Segoe UI", 15));
       login.setCursor(Cursor.HAND);
       login.setOnAction(e->{
           new LoginView(privilege);
       });
       
       logout.setFont(Font.font("Segoue UI", 15));
       logout.setCursor(Cursor.HAND);
       
       logout.setOnAction(e->{
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Deseja realmente sair?", 
            ButtonType.YES, ButtonType.NO);
            alerta.setTitle("Atenção");
            alerta.setHeaderText(null);
            Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
            alerta.showAndWait();



            if(alerta.getResult() == ButtonType.YES){

                switch(Main.getPrivilege().getPrivilegeType()){
                    case PrivilegeTypeInterface.BOARD:
                        Main.setBoard(null);
                        break;
                    case PrivilegeTypeInterface.EVALUATOR:
                        Main.setEvaluator(null);
                        break;
                    case PrivilegeTypeInterface.USER:
                        Main.setUser(null);
                        break;
                }
                Main.getPrivilege().setPrivilegeType(PrivilegeTypeInterface.NOTLOGGED);
                stage.close();
                Main.refresh();
            } else{
                stage.close();
            }
        });
    }
    
    public void setTitle(String title) {
        this.title.setText(title);
    }
    
    public void setTitle(CategoryView categoryView) {
        setTitle("Categoria");
        setSubtitle("Lista de categorias que englobam os eventos");
        
        anchorPane.getChildren().remove(login);
        HBox hbox1 = new HBox(10);
        Button add = new Button("Adicionar");

        add.setFont(Font.font("Segoe UI", 15));
        add.setCursor(Cursor.HAND);
        add.setOnAction(e->{
            categoryView.showAddDialog();
        });
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.ADMIN){
            hbox1.getChildren().addAll(add, logout);
        }
        

        
        
        
        anchorPane.getChildren().add(hbox1);

        AnchorPane.setRightAnchor(hbox1, 20.0);
        AnchorPane.setTopAnchor(hbox1, 20.0);
    }
    
    public void setTitle(CriteriaView criteriaView){
        setTitle("Critérios");
        setSubtitle("Aqui podem ser controlados os critérios de avaliação");
        
        
        anchorPane.getChildren().remove(login);
        HBox hbox1 = new HBox(10);
        Button add = new Button("Adicionar");

        add.setFont(Font.font("Segoe UI", 15));
        add.setCursor(Cursor.HAND);
        add.setOnAction(e->{
            criteriaView.showAddDialog();
        });
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.BOARD){
            hbox1.getChildren().addAll(add, logout);
        }
        anchorPane.getChildren().add(hbox1);

        AnchorPane.setRightAnchor(hbox1, 20.0);
        AnchorPane.setTopAnchor(hbox1, 20.0);
    }
        
    public void setSubtitle(String subtitle)
    {
        this.subtitle.setText(subtitle);
    }
    
    private void showAddDialog(String editText){
      
       Stage stage = new Stage();
       
       stage.setTitle("Editar");
       stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
       GridPane gridPane = new GridPane();
       Label label = new Label("Categoria:");
       TextField textField = new TextField(editText);
       
       label.setFont(Font.font("Segoe UI", 18));
       textField.setFont(Font.font("Segoe UI", 18));
       
       Button buttonOk = new Button("OK");
       Button buttonCancel = new Button("Cancelar");
       
       
       buttonOk.setFont(Font.font("Segoe UI", 18));
       buttonCancel.setFont(Font.font("Segoe UI", 18));
       
       buttonCancel.setOnAction(e -> {
           stage.close();
       });
       
       gridPane.setAlignment(Pos.CENTER);
       HBox hbox = new HBox(buttonOk, buttonCancel);
       
       hbox.setSpacing(5);
       
       gridPane.add(label, 0, 0);
       gridPane.add(textField, 1, 0);
       
       gridPane.setHgap(10);
       gridPane.setVgap(20);
       
       gridPane.add(hbox, 1, 1);
       
       hbox.setAlignment(Pos.BASELINE_RIGHT);
       
       Scene scene = new Scene(gridPane, 400, 200);
       
       stage.initModality(Modality.APPLICATION_MODAL);
       stage.setScene(scene);
       stage.showAndWait();
   }

    public void refresh() {
        vbox.getChildren().clear();
        hbox.getChildren().clear();
        anchorPane.getChildren().clear();
        
        setTitle("Título");
        setSubtitle("Aqui vai a descrição do título");
        setLabels();
        setImages();
        setButtons();
        vbox.getChildren().addAll(this.title, this.subtitle);
        hbox.getChildren().addAll(koallaImage, vbox);
        HBox.setMargin(vbox, new Insets(10, 0, 0, 10));

        anchorPane.getChildren().addAll(hbox, login);

        
        if(Main.getPrivilege().getPrivilegeType() != 3){
            switch(Main.getPrivilege().getPrivilegeType()){
                case PrivilegeTypeInterface.BOARD:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.EVALUATOR:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.USER:
                    anchorPane.getChildren().set(1, logout);
                    break;
                case PrivilegeTypeInterface.ADMIN:
                    anchorPane.getChildren().set(1, logout);
                    break;
            }
            AnchorPane.setRightAnchor(logout, 20.0);
            AnchorPane.setTopAnchor(logout, 20.0);
        }else{

            AnchorPane.setRightAnchor(login, 20.0);
            AnchorPane.setTopAnchor(login, 20.0);
        }
        AnchorPane.setLeftAnchor(hbox, 10.0);
    }
    
    
    public void setTitleAndSubTitle(String title, String subtitle) {
        setTitle(title);
        setSubtitle(subtitle);
    }
    
}
