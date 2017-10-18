package app.view.viewcontrollers.autocompletecheckcombobox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class CheckComboBox<T> extends ComboBox<CheckBox> {
    private ObservableList<T> checkItems;
    private ObservableList<T> selectedItems;
    private Map<T, Integer> itemsMap;
    
    public CheckComboBox(ObservableList<T> items) {
        super();
        setEditable(false);
        this.checkItems = FXCollections.<T>observableArrayList(items);
        this.selectedItems = FXCollections.<T>observableArrayList();
        ObservableList<CheckBox> x = FXCollections.<CheckBox>observableArrayList();
        itemsMap = new HashMap<T, Integer>();
        for(T a : checkItems) {
            CheckBox check = new CheckBox(a.toString());
            check.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> obv, Boolean ov, Boolean nv) {
                    if(nv) {
                        itemsMap.put(a, checkItems.indexOf(a));
                        List<Integer> temp = new ArrayList<>();
                        temp.addAll(itemsMap.values());
                        Collections.sort(temp);
                        selectedItems.clear();
                        for(Integer a : temp) {
                            selectedItems.add(checkItems.get(a));
                        }
                    } else {
                        itemsMap.remove(a);
                        List<Integer> temp = new ArrayList<>();
                        temp.addAll(itemsMap.values());
                        Collections.sort(temp);
                        selectedItems.clear();
                        for(Integer a : temp) {
                            selectedItems.add(checkItems.get(a));
                        }
                    }
                }
            });
            x.add(check);
        }
        super.setItems(x);
        new AutoCompleteComboBox<CheckBox>().bindAutoComplete(this);
    }

    public CheckComboBox(ObservableList<T> items, String title) {
        super();
        super.setPromptText(title);
        setEditable(false);
        this.checkItems = FXCollections.<T>observableArrayList(items);
        this.selectedItems = FXCollections.<T>observableArrayList();
        ObservableList<CheckBox> x = FXCollections.<CheckBox>observableArrayList();
        itemsMap = new HashMap<T, Integer>();
        for(T a : checkItems) {
            CheckBox check = new CheckBox(a.toString());
            check.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> obv, Boolean ov, Boolean nv) {
                    if(nv) {
                        itemsMap.put(a, checkItems.indexOf(a));
                        List<Integer> temp = new ArrayList<>();
                        temp.addAll(itemsMap.values());
                        Collections.sort(temp);
                        selectedItems.clear();
                        for(Integer a : temp) {
                            selectedItems.add(checkItems.get(a));
                        }
                    } else {
                        itemsMap.remove(a);
                        List<Integer> temp = new ArrayList<>();
                        temp.addAll(itemsMap.values());
                        Collections.sort(temp);
                        selectedItems.clear();
                        for(Integer a : temp) {
                            selectedItems.add(checkItems.get(a));
                        }
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
        return selectedItems;
    }
    
    
}
