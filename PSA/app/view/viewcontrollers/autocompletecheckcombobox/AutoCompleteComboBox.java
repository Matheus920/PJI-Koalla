package app.autocompletecheckcombobox;

import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;


public class AutoCompleteComboBox<T> {
    
    ComboBox<T> comboBox = new ComboBox();
    ObservableList originalItems;
    String filteredText = "";
    
    public void bindAutoComplete(ComboBox<T> comboBox){
        this.comboBox = comboBox;
        originalItems = FXCollections.observableArrayList(comboBox.getItems());
        comboBox.setTooltip(new Tooltip());
        comboBox.setOnKeyPressed(this::handleOnKeyPressed);
        comboBox.setOnHidden(this::handleOnHiding);
    }
    
    public void handleOnKeyPressed(KeyEvent e){
        ObservableList<T> filteredItens = FXCollections.observableArrayList();
        KeyCode code = e.getCode();
        if(code.isArrowKey()) {
            e.consume();
            return;
        } else {
            filteredText += e.getText();
        }
        if(code == KeyCode.BACK_SPACE && filteredText.length() > 0){
            filteredText = filteredText.substring(0, filteredText.length() - 1);
            comboBox.getItems().setAll(originalItems);
        }
        if(code == KeyCode.ESCAPE){
            filteredText = "";
        }
        if(filteredText.length() == 0){
            filteredItens = originalItems;
            comboBox.getTooltip().hide();
        }
        else{
            Stream<T> itens = comboBox.getItems().stream();
            String searchText = filteredText.toString().toLowerCase();
            itens.filter(el -> el.toString().toLowerCase().contains(searchText)).forEach(filteredItens::add);
            comboBox.getTooltip().setText(searchText);
            
            Window stage = comboBox.getScene().getWindow();
            double positionX = stage.getX() + comboBox.getBoundsInParent().getMinX();
            double positionY = stage.getY() + comboBox.getBoundsInParent().getMinY();
            
            comboBox.getTooltip().show(stage, positionX, positionY);
            comboBox.show();
        }
        comboBox.getItems().setAll(filteredItens);
    }
    
    public void handleOnHiding(Event e){
        filteredText = "";
        comboBox.getTooltip().hide();
        T selected = comboBox.getSelectionModel().getSelectedItem();
        comboBox.getItems().setAll(originalItems);
        comboBox.getSelectionModel().select(selected);            
    }
  
}
