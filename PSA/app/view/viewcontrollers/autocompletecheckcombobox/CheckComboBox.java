package app.view.viewcontrollers.autocompletecheckcombobox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class CheckComboBox<T> extends ComboBox<CheckBox> {
    private ObservableList<T> checkItems;
    private Map<T, Integer> selectedItemsMap;
    
    public CheckComboBox(ObservableList<T> items) {
        super();
        setEditable(false);
        this.checkItems = FXCollections.<T>observableArrayList(items);
        this.selectedItems = FXCollections.<T>observableArrayList();
        ObservableList<CheckBox> x = FXCollections.<CheckBox>observableArrayList();
        selectedItemsMap = new HashMap<T, Integer>();
        for(T a : checkItems) {
            CheckBox check = new CheckBox(a.toString());
            check.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> obv, Boolean ov, Boolean nv) {
                    if(nv) {
                        selectedItemsMap.put(a, checkItems.indexOf(a));
                    } else {
                        selectedItemsMap.remove(a);
                    }
                }
            });
            x.add(check);
        }
        super.setItems(x);
        new AutoCompleteComboBox<CheckBox>().bindAutoComplete(this);
    }

    public ObservableList<T> getCheckItems() {
        return checkItems;
    }
    
    public ObservableList<T> getSelectedItems() {
        ObservableList<T> selectedItems = FXCollections.<T>observableArrayList();
        List<Integer> temp = new ArrayList<>();
        temp.addAll(selectedItemsMap.values());
        Collections.sort(temp);
        for(Integer a : temp) {
            selectedItems.add(checkItems.get(a));
        }
}
