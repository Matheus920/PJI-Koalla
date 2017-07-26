package app.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Header {
    private final Label title = new Label();
    private final Label subtitle = new Label();
    private final Button login = new Button("Login");
    private final AnchorPane anchorPane = new AnchorPane();
    private final VBox vbox = new VBox(5);
    private final HBox hbox = new HBox(10);
    
    public AnchorPane headerShow(String title, String subtitle) {
        Image image = new Image(getClass().getResourceAsStream("icon.jpg"));
        ImageView koallaImage = new ImageView(image);
        koallaImage.setPreserveRatio(true);
        koallaImage.setFitHeight(100);
        koallaImage.setFitWidth(100);
        
        this.title.setText(title);
        this.subtitle.setText(subtitle);
        this.title.setFont(Font.font("Century Gothic", FontWeight.BLACK, 24));
        this.subtitle.setFont(Font.font("Century Gothic", 12));
        vbox.getChildren().addAll(this.title, this.subtitle);
        hbox.getChildren().addAll(koallaImage, vbox);
        HBox.setMargin(vbox, new Insets(10, 0, 0, 10));

        anchorPane.getChildren().addAll(hbox, login);
        anchorPane.setBorder(new Border(new BorderStroke(Color.WHITE, Color.WHITE, Color.BLACK, Color.WHITE, null, null, BorderStrokeStyle.SOLID, null, null, new BorderWidths(1.0), null)));
        
        AnchorPane.setRightAnchor(login, 20.0);
        AnchorPane.setTopAnchor(login, 20.0);
        AnchorPane.setLeftAnchor(hbox, 10.0);
        
        return anchorPane;
    }
    
}
