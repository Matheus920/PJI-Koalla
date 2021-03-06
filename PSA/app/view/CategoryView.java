package app.view;

import app.Main;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import app.control.interfaces.CRUDCategoryInterface;
import app.model.Category;
import app.view.viewcontrollers.MaskField;
import javafx.geometry.HPos;
import javafx.scene.input.KeyCode;

public class CategoryView {
    private final ObservableList<Category> items  = FXCollections.observableArrayList();
    private final ListView<AnchorPane> list = new ListView();
    private final CRUDCategoryInterface categories;
    private final Label lblOk = new Label("");
    
    public CategoryView(CRUDCategoryInterface categories) {
        Main.refreshBottom();
        this.categories = categories;
        lblOk.setStyle("-fx-text-fill: red;");
    }
    
    public ListView getCategoryList(){
        setItems();
        setLists();
        return list;
    }
    
    private  void setItems(){
       setContent(categories.getAllCategories());
    }
    
    
    private void setContent(Category... content) {
        for(Category a : content) {
            items.add(a);
            AnchorPane anchorPane = new AnchorPane();
            Text text = new Text(a.getNome());
            Button btn = new Button("X");
            btn.setMinSize(5, 3);
            btn.setFont(Font.font("Segoe UI", 10));
            btn.setVisible(false);
            btn.setCursor(Cursor.HAND);
            
            btn.setOnAction(e -> {
                showDeleteDialog();
            });
                
            
            anchorPane.getChildren().addAll(text, btn);
            AnchorPane.setRightAnchor(btn, 5.0);
            AnchorPane.setTopAnchor(btn, 2.0);
            AnchorPane.setBottomAnchor(text, 3.0);
            
            list.getItems().add(anchorPane);
        }
    }
    
    private void setContent(List<Category> content) {
        for(Category a : content) {
            items.add(a);
            AnchorPane anchorPane = new AnchorPane();
            Text text = new Text(a.getNome());
            Button btn = new Button("X");
            btn.setMinSize(5, 3);
            btn.setFont(Font.font("Segoe UI", 10));
            btn.setVisible(false);
            btn.setCursor(Cursor.HAND);
            
            btn.setOnAction(e -> {
                showDeleteDialog();
            });
                
            
            anchorPane.getChildren().addAll(text, btn);
            AnchorPane.setRightAnchor(btn, 5.0);
            AnchorPane.setTopAnchor(btn, 2.0);
            AnchorPane.setBottomAnchor(text, 3.0);
            

            
            list.getItems().add(anchorPane);
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
                if(list.getSelectionModel().getSelectedItem() != null){
                    if(e.getClickCount() == 2){
                       if(!list.getItems().isEmpty())
                       showEditDialog(((Text)list.getSelectionModel().getSelectedItem().getChildren().get(0)).getText(), list.getSelectionModel().getSelectedIndex());
                    }
                }
            });
            
        
        
            list.setOnKeyPressed(e->{
                if(list.getSelectionModel().getSelectedItem() != null){
                    if(e.getCode() == KeyCode.ENTER) {
                       if(!list.getItems().isEmpty())
                       showEditDialog(((Text)list.getSelectionModel().getSelectedItem().getChildren().get(0)).getText(), list.getSelectionModel().getSelectedIndex());
                    }
                }
            });
    
        
        list.setCursor(Cursor.HAND);
    }
   private void showEditDialog(String editText, int index){
      
       Stage stage = new Stage();
       
       stage.setTitle("Editar");
       stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
       GridPane gridPane = new GridPane();
       Label label = new Label("Categoria:");
       TextField textField = new TextField(editText);
       
       MaskField.maxLength(textField, 255);
       
       label.setFont(Font.font("Segoe UI", 18));
       textField.setFont(Font.font("Segoe UI", 18));
       
       Button buttonOk = new Button("OK");
       Button buttonCancel = new Button("Cancelar");
       
       buttonOk.setCursor(Cursor.HAND);
       buttonCancel.setCursor(Cursor.HAND);
       
       buttonOk.setFont(Font.font("Segoe UI", 18));
       buttonCancel.setFont(Font.font("Segoe UI", 18));
       
       buttonOk.setOnAction(e->{
           if(textField.getText() == null || textField.getText().equals("")){
               lblOk.setText("* Campo categoria está vazio");
           }
           else {
            if(!categories.exists(textField.getText())){
                Category oldCategory = categories.getCategoryById(items.get(index).getId());
                oldCategory.setNome(textField.getText());
                categories.updateCategory(oldCategory);
                ((Text)list.getItems().get(index).getChildren().get(0)).setText(textField.getText());
                stage.close();
            }
            else{
               lblOk.setText("Categoria já cadastrada no sistema.");
            }
          }   
       });
       
       textField.setOnAction(e->{
           if(textField.getText() == null || textField.getText().equals("")){
               lblOk.setText("* Campo categoria está vazio");
           }
           else {
            if(!categories.exists(textField.getText())){
                Category oldCategory = categories.getCategoryById(items.get(index).getId());
                oldCategory.setNome(textField.getText());
                categories.updateCategory(oldCategory);
                ((Text)list.getItems().get(index).getChildren().get(0)).setText(textField.getText());
                stage.close();
            }
            else{
               lblOk.setText("Categoria já cadastrada no sistema.");
            }
          }
       });
       
       buttonCancel.setOnAction(e -> {
           stage.close();
       });
       
       gridPane.setAlignment(Pos.CENTER);
       HBox hbox = new HBox(buttonOk, buttonCancel);
       
       hbox.setSpacing(5);
       
       GridPane.setColumnSpan(lblOk, GridPane.REMAINING);
       
       gridPane.add(lblOk, 0, 0);
       gridPane.add(label, 0, 1);
       gridPane.add(textField, 1, 1);
       
       gridPane.setHgap(10);
       gridPane.setVgap(20);
       
       gridPane.add(hbox, 1, 2);
       
       hbox.setAlignment(Pos.BASELINE_RIGHT);
       
       Scene scene = new Scene(gridPane, 400, 200);
       
       stage.initModality(Modality.APPLICATION_MODAL);
       stage.setScene(scene);
       stage.showAndWait();
   }
   private void showDeleteDialog(){
       
       Alert alert = new Alert(AlertType.CONFIRMATION,
               "Você está prestes a realizar uma exclusão. \nDeseja confirmar?",
                ButtonType.YES, ButtonType.NO);
       alert.setTitle("Atenção");
       alert.setHeaderText(null);
       
       Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
       
       stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
       stage.showAndWait();
       
       if(alert.getResult() == ButtonType.YES){
           int index = list.getSelectionModel().getSelectedIndex();
           list.getItems().remove(index);
           Category toDelete = categories.getCategoryById(items.get(index).getId());
           categories.deleteCategory(toDelete);
       }
   }

    void showAddDialog() {
       Stage stage = new Stage();
       
       stage.setTitle("Adicionar");
       stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
       GridPane gridPane = new GridPane();
       Label label = new Label("Categoria:");
       TextField textField = new TextField();
       
       MaskField.maxLength(textField, 255);
       
       
       label.setFont(Font.font("Segoe UI", 18));
       textField.setFont(Font.font("Segoe UI", 18));
       
       Button buttonOk = new Button("OK");
       Button buttonCancel = new Button("Cancelar");
       
       buttonOk.setCursor(Cursor.HAND);
       buttonCancel.setCursor(Cursor.HAND);
       
       buttonOk.setFont(Font.font("Segoe UI", 18));
       buttonCancel.setFont(Font.font("Segoe UI", 18));
       
       buttonOk.setOnAction(e->{
           if(textField.getText() == null || textField.getText().equals("")){
               lblOk.setText("* Campo categoria está vazio");
           }
           else {
            if(!categories.exists(textField.getText())){
             categories.addCategory(new Category(textField.getText()));
             setContent(new Category(textField.getText()));
             stage.close();
            }
            else{
                lblOk.setText("Categoria já cadastrada no sistema");
            }
           }
       });
       
       textField.setOnAction(e->{
            if(textField.getText() == null || textField.getText().equals("")){
               lblOk.setText("* Campo categoria está vazio");
            }
            else {
                if(!categories.exists(textField.getText())){
                 categories.addCategory(new Category(textField.getText()));
                 setContent(new Category(textField.getText()));
                 stage.close();
                }
                else{
                    lblOk.setText("Categoria já cadastrada no sistema");
                }
            }
        });
       
       buttonCancel.setOnAction(e -> {
           stage.close();
       });
       
       gridPane.setAlignment(Pos.CENTER);
       HBox hbox = new HBox(buttonOk, buttonCancel);
       
       hbox.setSpacing(5);
       
       GridPane.setColumnSpan(lblOk, GridPane.REMAINING);
       GridPane.setHalignment(lblOk, HPos.CENTER);
       
       gridPane.add(lblOk, 0, 0);
       gridPane.add(label, 0, 1);
       gridPane.add(textField, 1, 1);
       
       gridPane.setHgap(10);
       gridPane.setVgap(20);
       
       gridPane.add(hbox, 1, 2);
       Main.setBackgroundWhite(gridPane);
       
       hbox.setAlignment(Pos.BASELINE_RIGHT);
       
       Scene scene = new Scene(gridPane, 400, 200);
       
       stage.initModality(Modality.APPLICATION_MODAL);
       stage.setScene(scene);
       stage.showAndWait();
    }
}
