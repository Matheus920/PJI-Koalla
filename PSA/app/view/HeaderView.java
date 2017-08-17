package app.view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderView {
    private final Label title = new Label();
    private final Label subtitle = new Label();
    private final Button login = new Button("Login");
    private final AnchorPane anchorPane = new AnchorPane();
    private final VBox vbox = new VBox(5);
    private final HBox hbox = new HBox(10);
    private final ImageView koallaImage = new ImageView(new Image(getClass().getResourceAsStream("koala.png")));
    
    public HeaderView(String title, String subtitle) {
        setTitle(title);
        setSubtitle(subtitle);
        setLabels();
        setImages();
        setButtons();
        
        
        vbox.getChildren().addAll(this.title, this.subtitle);
        hbox.getChildren().addAll(koallaImage, vbox);
        HBox.setMargin(vbox, new Insets(10, 0, 0, 10));

        anchorPane.getChildren().addAll(hbox, login);
       
        AnchorPane.setRightAnchor(login, 20.0);
        AnchorPane.setTopAnchor(login, 20.0);
        AnchorPane.setLeftAnchor(hbox, 10.0);
    }
    
    public HeaderView() {
        setTitle(null);
        setSubtitle(null);
        setLabels();
        setImages();
        setButtons();
        
        
        vbox.getChildren().addAll(this.title, this.subtitle);
        hbox.getChildren().addAll(koallaImage, vbox);
        HBox.setMargin(vbox, new Insets(10, 0, 0, 10));

        anchorPane.getChildren().addAll(hbox, login);
       
        AnchorPane.setRightAnchor(login, 20.0);
        AnchorPane.setTopAnchor(login, 20.0);
        AnchorPane.setLeftAnchor(hbox, 10.0);
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
           new LoginView();
       });
    }
    
    public void setTitle(String title)
    {
        this.title.setText(title);
    }
    
    public void setSubtitle(String subtitle)
    {
        this.subtitle.setText(subtitle);
    }
    
}
