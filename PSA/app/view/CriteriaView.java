package app.view;

import app.Main;
import app.control.interfaces.CRUDCriteriaInterface;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class CriteriaView {
   
    private final ObservableList<AnchorPane> items = FXCollections.observableArrayList();
    private final ListView<AnchorPane> list = new ListView(items);
    private final TextArea txtDescription = new TextArea();
    private final CRUDCriteriaInterface criteria;
    
    public CriteriaView(CRUDCriteriaInterface criteria){
        this.criteria = criteria;
    }
    
    public ListView getCriteriaList(){
        setItems();
        setLists();
        return list;
    } 
    
    private void setItems(){
        setContent(criteria.getAllCriteria());
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
               showDescriptionDialog(((Text)list.getSelectionModel().getSelectedItem().getChildren().get(0)).getText(), 
                       list.getSelectionModel().getSelectedIndex());
           }
        });
        
        list.setCursor(Cursor.HAND);
    }
    
    private void setContent(String... content){
        for(String a : content){
            AnchorPane anchorPane = new AnchorPane();
            Text text = new Text(a);
            
            Button delete = new Button("X");
            delete.setMinSize(5, 3);
            delete.setFont(Font.font("Segoe UI", 10));
            delete.setVisible(false);
            delete.setCursor(Cursor.HAND);
            
            delete.setOnAction(e->{
                showDeleteDialog();
            });
            
            
            anchorPane.getChildren().addAll(text, delete);
            AnchorPane.setRightAnchor(delete, 5.0);
            AnchorPane.setTopAnchor(delete, 2.0);
            AnchorPane.setBottomAnchor(text, 3.0);
            
            items.add(anchorPane);
        }
    }
    
     private void setContent(List<String> content) {
        for(String a : content) {
            AnchorPane anchorPane = new AnchorPane();
            Text text = new Text(a);
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
            

            
            items.add(anchorPane);
        }
    }
     
    private void showDeleteDialog(){
       
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
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
           criteria.deleteCriteriaById(index);
       }
    }
   
    private void showDescriptionDialog(String title, int index){
       
       Stage stage = new Stage();
       
       stage.setTitle("Descrição do critério");
       stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));

       GridPane gridPane = new GridPane();
       Label lblTitle = new Label("Título:");
       TextField txtTitle = new TextField(title);
       
       Label lblDescription = new Label("Descrição:");
       
       txtTitle.setEditable(false);
       txtDescription.setEditable(false);

       
       Button btnEdit = new Button("Editar");
       Button btnCancel = new Button("Cancelar");
       
       btnEdit.setFont(Font.font("Segoe UI", 18));
       btnCancel.setFont(Font.font("Segoe UI", 18));
      
       HBox hbox = new HBox(btnEdit, btnCancel);
       hbox.setSpacing(5);
       
     
       
       gridPane.setAlignment(Pos.CENTER);
       
       gridPane.add(lblTitle, 0, 0);
       gridPane.add(txtTitle, 1, 0);
       gridPane.add(lblDescription, 0, 1);
       gridPane.add(txtDescription, 1, 1);
       
       gridPane.setHgap(10);
       gridPane.setVgap(20);
       
       gridPane.add(hbox, 1, 2);
       
       hbox.setAlignment(Pos.BASELINE_RIGHT);
       
       
    
       
       btnEdit.setOnAction(e -> {
           txtTitle.setEditable(true);
           txtDescription.setEditable(true);
           
           gridPane.getChildren().remove(hbox);
           
           Button btnSave = new Button("Salvar");
           btnSave.setFont(Font.font("Segoe UI", 18));
           
           HBox hbox1 = new HBox();
           
           hbox1.getChildren().addAll(btnSave, btnCancel);
           hbox1.setSpacing(5);
           
           hbox1.setAlignment(Pos.BASELINE_RIGHT);
           
           gridPane.add(hbox1, 1, 2);
           
           btnSave.setOnAction(e1 -> {
               criteria.updateCriteriaById(index, txtTitle.getText());
               ((Text)items.get(index).getChildren().get(0)).setText(txtTitle.getText());
               txtDescription.setText(txtDescription.getText());
               gridPane.getChildren().remove(hbox1);
               
               gridPane.add(hbox, 1, 2);
               
               
               stage.close();
           });
       });
       
       btnCancel.setOnAction(e->{
           stage.close();
       });
       
       Scene scene = new Scene(gridPane, 575, 300); 
       
       Main.setBackgroundWhite(gridPane);
       stage.initModality(Modality.APPLICATION_MODAL);
       stage.setScene(scene);
       stage.showAndWait();
       
    }
    void showAddDialog(){
     Stage stage = new Stage();
     stage.setTitle("Adicionar categoria");
     stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));

     GridPane gridPane = new GridPane();
     Label lblTitle = new Label("Título:");
     TextField txtTitle = new TextField();

     Label lblDescription = new Label("Descrição:");

     Button btnSave = new Button("Salvar");
     Button btnCancel = new Button("Cancelar");

     btnSave.setFont(Font.font("Segoe UI", 18));
     btnCancel.setFont(Font.font("Segoe UI", 18));

     HBox hbox = new HBox(btnSave, btnCancel);
     hbox.setSpacing(5);
     txtDescription.setEditable(true);

     btnSave.setOnAction(e -> {
         criteria.addCriteria(txtTitle.getText());
         setContent(txtTitle.getText());
         txtDescription.setText(txtDescription.getText());
         stage.close();
     });


     btnCancel.setOnAction(e -> {
         stage.close();
     });

     gridPane.setAlignment(Pos.CENTER);

     gridPane.add(lblTitle, 0, 0);
     gridPane.add(txtTitle, 1, 0);
     gridPane.add(lblDescription, 0, 1);
     gridPane.add(txtDescription, 1, 1);

     gridPane.setHgap(10);
     gridPane.setVgap(20);

     gridPane.add(hbox, 1, 2);

     hbox.setAlignment(Pos.BASELINE_RIGHT);

     Scene scene = new Scene(gridPane, 575, 300); 

     Main.setBackgroundWhite(gridPane);
     stage.initModality(Modality.APPLICATION_MODAL);
     stage.setScene(scene);
     stage.showAndWait();

   } 
}