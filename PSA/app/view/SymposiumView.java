package app.view;

import app.Main;
import app.control.AnnalsController;
import app.control.CRUDArticle;
import app.control.CRUDEvent;
import app.control.interfaces.CRUDSymposiumInterface;
import app.control.interfaces.PrivilegeTypeInterface;
import app.model.Article;
import app.view.viewcontrollers.MaskField;
import java.io.File;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SymposiumView {
    private final VBox vbox1 = new VBox(10);
    private final VBox vbox2 = new VBox(2);
    private final VBox vbox3 = new VBox(5);
    private final VBox vbox4 = new VBox(5);
    private final StackPane stackPane = new StackPane();
    private ObservableList<Label> speakers = FXCollections.observableArrayList();
    private CRUDSymposiumInterface symposium;
    private PrivilegeTypeInterface privilege;
    private final String[] fields = {"Título", "Caminho"};
    private final TextField textField = new TextField();
    private final TextField tfTitle = new TextField();
    private final CRUDEvent crudEvent = new CRUDEvent();
    private Label lblEnrolled;
   
    
    
    public SymposiumView(CRUDSymposiumInterface symposium, PrivilegeTypeInterface privilege) {
        this.symposium = symposium;
        this.privilege = privilege;
        Label speakers = new Label("Palestrante");
        Label overview = new Label("Resumo");
        Label place = new Label("Local");
        
        speakers.setFont(Font.font("Century Gothic", FontWeight.BLACK, 24));
        overview.setFont(Font.font("Century Gothic", FontWeight.BLACK, 24));
        place.setFont(Font.font("Century Gothic", FontWeight.BLACK, 24));
        
        setVboxes();
        setOverview();
        setSpeakers();
        setPlace();
        
        vbox1.getChildren().addAll(overview, vbox2, speakers, vbox3, place, vbox4);
        vbox1.setAlignment(Pos.TOP_CENTER);
        
        stackPane.getChildren().add(vbox1);
        stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, 
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,new BorderWidths(1), Insets.EMPTY)));
    }
    
    public ScrollPane symposiumShow() {
        ScrollPane scrollPane = new ScrollPane(stackPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        return scrollPane;
    }
    
    private void addSpeakers() {
        for(String a : symposium.getSpeakers()) {
            Label lblSpeaker = new Label((char)0x2022 + a);
            lblSpeaker.setFont(Font.font("Segoe UI", 24));
            this.speakers.add(lblSpeaker);
        }
    }
    
    private void setVboxes() {
        vbox1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        vbox2.prefWidthProperty().bind(stackPane.widthProperty());
        vbox2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1))));
        
        vbox3.prefWidthProperty().bind(stackPane.widthProperty());
        vbox3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1))));
        
        vbox4.prefWidthProperty().bind(stackPane.widthProperty());
        vbox4.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1))));
    }
    
    private void setOverview() {
        Label lblOverview = new Label(symposium.getOverview());
        lblOverview.setWrapText(true);
        
        lblOverview.setFont(Font.font("Segoe UI", 16));
        
        vbox2.getChildren().add(lblOverview);
    }
    
    private void setPlace() {
        Label lblEnrolledUsers = new Label("Inscritos: ");
        lblEnrolled = new Label(Integer.toString(symposium.getEnrolledUsers()));
        Label lblPlace = new Label(symposium.getPlace());
        lblPlace.setFont(Font.font("Segoe UI", 18));
        lblEnrolledUsers.setFont(Font.font("Segoe UI", 18));
        lblEnrolled.setFont(Font.font("Segoe UI", 18));
        
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.getChildren().addAll(lblEnrolledUsers, lblEnrolled);
        
        vbox4.getChildren().addAll(lblPlace, hbox);
    }
    
    private void setSpeakers() {
        addSpeakers();
        FlowPane flowPane = new FlowPane();
        
        for(Label a : speakers) {
            flowPane.getChildren().add(a);
        }
        
        flowPane.setHgap(50);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setPrefWrapLength(150);
        
        Label lblVacancy = new Label("Vagas para palestrante: " + symposium.getSpeakersVacancies());
        lblVacancy.setFont(Font.font("Segoe UI", 16));
        
        vbox3.getChildren().addAll(flowPane, lblVacancy);
        vbox3.setAlignment(Pos.CENTER_RIGHT);
        
        VBox.setMargin(lblVacancy, new Insets(5, 10, 0, 0));
    }
    
    public HBox getBottom() {
        Label lblDate = new Label("Data: " + symposium.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Button btnAnnals = new Button("Anais");
        Button btnSign = new Button("Inscrever");
        Button btnUnsign = new Button("Desinscrever-se");
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(btnAnnals, btnSign);
        
        if(crudEvent.getEventById(symposium.getId()).getCapacidade() == Integer.parseInt(lblEnrolled.getText())){
            btnSign.setDisable(true);
        }
            
        if(privilege.getPrivilegeType() != PrivilegeTypeInterface.NOTLOGGED && 
                  privilege.getPrivilegeType() != PrivilegeTypeInterface.USER){
            btnSign.setDisable(true);
        }
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.USER) {
            Button btnSubmit = new Button("Submeter");
            btnSubmit.setCursor(Cursor.HAND);
            btnSubmit.setOnAction(e-> {
                showSubmitDialog();
            });
            
            hbox.getChildren().add(0, btnSubmit);
            
            if(!crudEvent.getEventById(symposium.getId()).isStatus()){
                btnSubmit.setDisable(true);
            }
            
            if(privilege.getPrivilegeType() != PrivilegeTypeInterface.NOTLOGGED && 
                privilege.getPrivilegeType() != PrivilegeTypeInterface.USER){
                btnSubmit.setDisable(true);
            }
        }
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.USER){
            if(crudEvent.existsUserInThisEvent(Main.getUser(), crudEvent.getEventById(symposium.getId()))){
                hbox.getChildren().set(2, btnUnsign);
            }
        }
        
        btnAnnals.setCursor(Cursor.HAND);
        btnSign.setCursor(Cursor.HAND);
        
        btnAnnals.setOnAction(e->{
            String selectedDirectoryString;
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Onde deseja salvar os anais? ");
            directoryChooser.setInitialDirectory(new File("C:/Users/" + System.getProperty("user.name") + "/documents"));
            File selectedDirectory = directoryChooser.showDialog(null);
            
            if(selectedDirectory == null){
                selectedDirectoryString = "Nenhum diretório foi selecionado";
            }else{
                selectedDirectoryString = selectedDirectory.getAbsolutePath();
            }
            
            if(!selectedDirectoryString.equals("Nenhum diretório foi selecionado")){
                new AnnalsController().makeAnnals(crudEvent.getEventById(symposium.getId()), selectedDirectoryString);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Gravação realizada com sucesso.", ButtonType.CLOSE);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
       
                stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
                stage.showAndWait();
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Nenhum diretório selecionado.", ButtonType.CLOSE);
                alerta.setTitle("Atenção");
                alerta.setHeaderText("Problema na gravação");
                Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
       
                stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
                stage.showAndWait();
            }
            
        });
        
        btnSign.setOnAction(e->{
           if(privilege.getPrivilegeType() != PrivilegeTypeInterface.NOTLOGGED){
            crudEvent.addAnUserInAEvent(crudEvent.getEventById(symposium.getId()), Main.getUser());
            hbox.getChildren().set(2, btnUnsign);
            lblEnrolled.setText(Integer.toString(Integer.parseInt(lblEnrolled.getText()) + 1));
           }else{
               new LoginView(privilege);
           } 
        });
        
        btnUnsign.setCursor(Cursor.HAND);
        btnUnsign.setOnAction(e->{
            crudEvent.deleteUserFromAnEvent(Main.getUser(), crudEvent.getEventById(symposium.getId()));
            hbox.getChildren().set(2, btnSign);
            lblEnrolled.setText(Integer.toString(Integer.parseInt(lblEnrolled.getText()) - 1));
        });
        
        if(crudEvent.getEventById(symposium.getId()).isStatus()){
            btnAnnals.setDisable(true);
        }else{
            btnSign.setDisable(true);
            btnUnsign.setDisable(true);
        }
        
        hbox.prefWidthProperty().bind(stackPane.widthProperty().subtract(100));
        hbox.getChildren().add(lblDate);
        
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.setPadding(new Insets(10, 20, 5, 0));
        return hbox;
    }
    
    private void showSubmitDialog(){
       Stage stage = new Stage();
       
       stage.setTitle("Submeter");
       stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
       GridPane gridPane = new GridPane();
       Label lblTitle = new Label("Título:");
       
       Label label = new Label("Caminho do artigo:");
      
       FileChooser pdf = new FileChooser();
       Button file = new Button("...");
       
       file.setOnAction(e->{
            pdf.setTitle("Escolha o artigo que deseja submeter");
            pdf.setInitialDirectory(new File("C:/Users/" + System.getProperty("user.name") + "/documents"));
            pdf.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documento [*.pdf]", "*.pdf"));
            File selectedFile = pdf.showOpenDialog(null);

            if(selectedFile != null) {
                textField.setText(selectedFile.getAbsolutePath());
            } else {
                textField.setText(null);
            }
        });
           
    
       
       MaskField.maxLength(textField, 255);
       
       
       Label lblOk = new Label("");
       lblOk.setStyle("-fx-text-fill: red;");
       label.setFont(Font.font("Segoe UI", 15));
       textField.setFont(Font.font("Segoe UI", 15));
       lblTitle.setFont(Font.font("Segoe UI", 15));
       tfTitle.setFont(Font.font("Segoe UI", 15));
       
       Button buttonOk = new Button("Confirmar");
       Button buttonCancel = new Button("Cancelar");
       CheckBox speaker = new CheckBox("Deseja palestrar?");
       
       buttonOk.setCursor(Cursor.HAND);
       buttonCancel.setCursor(Cursor.HAND);
       
       buttonOk.setFont(Font.font("Segoe UI", 15));
       buttonCancel.setFont(Font.font("Segoe UI", 15));
       
       HBox hbox = new HBox();
       
       buttonCancel.setOnAction(e->{
           stage.close();
       });
       
       buttonOk.setOnAction(e->{
        lblOk.setText(invalidFields());
           
        if(lblOk.getText().equals("")){
            
            Article article = new Article(tfTitle.getText(), textField.getText(), 
                crudEvent.getEventById(symposium.getId()), speaker.isSelected(), 
                   Main.getUser(), false);

            CRUDArticle crudArticle = new CRUDArticle();
            crudArticle.addArticle(article);
            stage.close();
        }
        });
       
       hbox.getChildren().addAll(buttonOk, buttonCancel);
       
       GridPane.setColumnSpan(lblOk, GridPane.REMAINING);
       
       gridPane.add(lblOk, 0, 0);
       gridPane.add(lblTitle, 0, 1);
       gridPane.add(tfTitle, 1, 1);
       gridPane.add(label, 0, 2);
       gridPane.add(textField, 1, 2);
       gridPane.add(file, 2, 2);
       gridPane.add(speaker, 2, 3);
       gridPane.add(hbox, 2, 4);
       gridPane.setAlignment(Pos.CENTER);
       hbox.setSpacing(5);
       
       gridPane.setVgap(20);
       gridPane.setHgap(10);
       
      
       
       hbox.setAlignment(Pos.BASELINE_RIGHT);
       
       Main.setBackgroundWhite(gridPane);
       
       Scene scene = new Scene(gridPane, 550, 300);
       stage.setScene(scene);
       stage.showAndWait();
    }
    
    
    private String invalidFields(){
      if(tfTitle.getText() == null || tfTitle.getText().equals("")){
          return "Campo " + fields[0] + " está vazio";
      } else if(textField.getText() == null || textField.getText().equals("")){
          return "Campo " + fields[1] + " está vazio";
      } else{
          return "";
      }
    }
}
