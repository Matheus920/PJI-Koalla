package app.view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Category {
    private final ObservableList<AnchorPane> items  = FXCollections.observableArrayList();
    private final ListView<AnchorPane> list = new ListView(items);
    
    public ListView showCategory(){
        setItems();
        setLists();
        return list;
    }
    
    private void setItems(){
        setContent("Luka", "To", "Nem", "Ai", "Pode", "Falar", "Dos", "Seus", "Problemas", "To", "Nem", "Ai", "Pitty", "As", "Mais", "Tocadas",
        "Rola", "Rolinha", "Roluda", "Rolão", "Rafaela");
    }
    
    private void setContent(String... content) {
        for(String a : content) {
            AnchorPane anchorPane = new AnchorPane();
            Text text = new Text(a);
            Button btn = new Button("X");
            btn.setMinSize(5, 3);
            btn.setFont(Font.font("Segoe UI", 10));
            btn.setVisible(false);
            btn.setCursor(Cursor.HAND);
            
            anchorPane.getChildren().addAll(text, btn);
            AnchorPane.setRightAnchor(btn, 5.0);
            AnchorPane.setTopAnchor(btn, 2.0);
            AnchorPane.setBottomAnchor(text, 3.0);
            

            
            items.add(anchorPane);
        }
    }
    
    private void setLists(){
        list.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 24; "
                + "-fx-font-family: 'Segoe UI'");
     
        list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends AnchorPane> obsv, AnchorPane oldv, AnchorPane nv) -> {
            if(nv == null) return;
            nv.getChildren().get(1).visibleProperty().set(true);
            
            if(oldv == null) return;
            oldv.getChildren().get(1).visibleProperty().set(false);
        });
        
        list.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2){
               System.out.println(list.getSelectionModel().getSelectedIndex());
               showEditDialog(((Text)list.getSelectionModel().getSelectedItem().getChildren().get(0)).getText());
            }
            
            
        }); 
    }
   private void showEditDialog(String editText){
      
       Stage stage = new Stage();
       
       stage.setTitle("Editar");
       stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
       GridPane gridPane = new GridPane();
       Label label = new Label("Categoria:");
       TextField textField = new TextField(editText);
       
       label.setFont(Font.font("Segoe UI", 18));
       textField.setFont(Font.font("Segoe UI", 18));
       
       Button buttonOk = new Button("OK");
       Button buttonCancel = new Button("Cancel");
       
       
       buttonOk.setFont(Font.font("Segoe UI", 18));
       buttonCancel.setFont(Font.font("Segoe UI", 18));
       
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
}
