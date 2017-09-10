package app.view;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EvaluationView {
    private final SplitPane splitPane = new SplitPane();
    private ObservableList<String> data = FXCollections.observableArrayList();
    public EvaluationView() {
        setLeft();
        setCenter();
        splitPane.getItems().add(getRight("NONE"));
        splitPane.setDividerPositions(0.3333f, 0.67777f);
    }
    
    public SplitPane evaluationShow() {
        return splitPane;
    }
    
    private void setLeft() {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        Label lblMembers = new Label("Lista de eventos");
        
        vbox2.getChildren().addAll(lblMembers);
        
        ListView<String> listView = new ListView<>(FXCollections.observableArrayList("Dia das abelhas", "Homenagem Raul Seixas", "Inglaterra antes de Deus"));
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
        listView.setOnMouseClicked(e->{
            data.clear();
            String tem = listView.getSelectionModel().getSelectedItem();
            for(int i = 0; i < 10; i++){
                data.add(tem + " - " + (i+1));
            }
        });
                
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
    
    private void setCenter() {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        Label lblMembers = new Label("Lista de artigos");
        
        vbox2.getChildren().addAll(lblMembers);
        
        ListView<String> listView = new ListView<>(data);
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
        
        listView.setOnMouseClicked(e->{
            if(splitPane.getItems().size() < 3) {
                splitPane.getItems().add(getRight(listView.getSelectionModel().getSelectedItem()));
            } else{
                splitPane.getItems().set(2, getRight(listView.getSelectionModel().getSelectedItem()));
            }
        });
        
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
    
    private VBox getRight(String item) {
        VBox vbox1 = new VBox(5);
        HBox hbox1 = new HBox();
        Hyperlink pdf = new Hyperlink(item);
        HBox hbox = new HBox(10);
        ToggleGroup group = new ToggleGroup();
        RadioButton approved = new RadioButton("Aprovado");
        RadioButton disapproved = new RadioButton("Reprovado");
        TextArea comments = new TextArea();
        HBox hbox2 = new HBox();
        hbox2.getChildren().add(new Button("Enviar"));
        
        approved.setToggleGroup(group);
        disapproved.setToggleGroup(group);
        
        hbox1.getChildren().add(new Label("Veredito"));
        
        hbox1.setBorder(new Border(new BorderStroke(Color.WHITE, Color.WHITE, Color.GRAY, Color.WHITE,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
                CornerRadii.EMPTY,new BorderWidths(1), Insets.EMPTY)));
        
        hbox1.setPrefHeight(40);
        
        hbox.getChildren().addAll(approved, disapproved);
        
        hbox.setAlignment(Pos.CENTER);
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER_RIGHT);
        
        vbox1.getChildren().addAll(hbox1, pdf, hbox, comments, hbox2);
        
        Main.setBackgroundWhite(vbox1, hbox);
        
        return vbox1;
    }
}
