package app.view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    }
}
