package app.view;

import app.Main;
import app.control.interfaces.PrivilegeTypeInterface;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

public class SpeakerView {
    private final SplitPane splitPane = new SplitPane();
    private PrivilegeTypeInterface privilege;
    
    public SpeakerView(PrivilegeTypeInterface privilege) {
        this.privilege = privilege;
        setLeft();
        setRight();
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
        
        ListView<String> listView = new ListView<>(FXCollections.observableArrayList("Ana Julia", "Bárbara Pax", "Carlão do Céu",
                                                                                     "Douglas Silva", "Sthela de Olveira"));
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
        
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
    
    private void setRight() {
        AnchorPane anchorPane = new AnchorPane();
        VBox vbox1 = getBottom();
        VBox vbox2 = new VBox(10);
        StackPane stackPane = new StackPane();
        
        ImageView imageView = new ImageView();
        
        TextField name = new TextField();
        DatePicker dob = new DatePicker();
        TextField institution = new TextField();
        TextField email = new TextField();
        
        dob.setPromptText("Data de nascimento");
        name.setPromptText("Nome");
        institution.setPromptText("Instituição");
        email.setPromptText("Email");
        
        name.setEditable(false);
        dob.setEditable(false);
        institution.setEditable(false);
        email.setEditable(false);
        
        Main.setStyleTextField(name, institution, email);
        dob.setStyle("-fx-font-size:18;-fx-font-family:Segoe UI");
        
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        
        stackPane.getChildren().add(imageView);
        stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        
        vbox2.getChildren().addAll(name, email, dob, institution);
        anchorPane.getChildren().addAll(vbox2, stackPane, vbox1);
        
        if(privilege.getPrivilegeType() == PrivilegeTypeInterface.ADMIN) {
            HBox hbox1 = new HBox(5);
            Button delete = new Button("Remover");
            Button save = new Button("Salvar");
            
            Main.setStyleLabeled(delete, save);
            
            delete.setCursor(Cursor.HAND);
            save.setCursor(Cursor.HAND);
            
            name.setEditable(true);
            institution.setEditable(true);
            email.setEditable(true);

            
            if(imageView.getImage() == null) {
                stackPane.getChildren().add(new Label("Insira uma imagem"));
            }

            imageView.imageProperty().addListener((obv, nv, ov) ->{
                ImageView temp = imageView;
                if(ov != null) {
                    Button del = new Button("X");
                    del.setFont(Font.font("Segoe UI", 12));
                    del.setCursor(Cursor.HAND);
                    del.setOnAction(e->{
                        temp.setImage(null);
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

                } else {
                    stackPane.getChildren().remove(1);
                    stackPane.getChildren().add(new Label("Insira uma imagem"));
                }
            });
            
            stackPane.setCursor(Cursor.HAND);

            stackPane.setOnMouseClicked(e->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Escolha sua imagem de perfil");
                fileChooser.setInitialDirectory(new File("C:/Users/" + System.getProperty("user.name") + "/pictures"));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem [*.png, *.jpg, *.bmp, *.jpeg]", "*.png", "*.jpg", "*.bmp", "*.jpeg"));
                File selectedFile = fileChooser.showOpenDialog(null);

                if(selectedFile != null) {
                    System.out.println();
                    imageView.setImage(new Image("file:" + selectedFile.getPath()));
                    if(stackPane.getChildren().size() > 1) stackPane.getChildren().remove(1);
                } 
            });
            
            hbox1.setAlignment(Pos.TOP_RIGHT);
        
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
    
    private VBox getBottom() {
        ListView listView = new ListView(FXCollections.observableArrayList("Dia das abelhas", "Homenagem Raul Seixas", "Inglaterra antes de Deus"));
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        Label lblTitle = new Label("Participações em simpósios");
        VBox vbox = new VBox(5);
        
        lblTitle.setFont(Font.font("Segoe UI", 18));
        
        vbox.setMaxHeight(300);
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(lblTitle, listView);
        
        return vbox;
    }
}
