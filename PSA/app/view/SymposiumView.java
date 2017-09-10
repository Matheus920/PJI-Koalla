package app.view;

import app.control.interfaces.CRUDSymposiumInterface;
import app.control.interfaces.PrivilegeTypeInterface;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SymposiumView {
    private final VBox vbox1 = new VBox(10);
    private final VBox vbox2 = new VBox(2);
    private final VBox vbox3 = new VBox(5);
    private final VBox vbox4 = new VBox(5);
    private final StackPane stackPane = new StackPane();
    private ObservableList<Label> speakers = FXCollections.observableArrayList();
    private CRUDSymposiumInterface symposium;
    private PrivilegeTypeInterface privilege;
    
    public SymposiumView(CRUDSymposiumInterface symposium, PrivilegeTypeInterface privilege) {
        this.symposium = symposium;
        this.privilege = privilege;
        Label speakers = new Label("Palestrante");
        Label overview = new Label("Resumo");
        Label place = new Label("Local");
        
        speakers.setFont(Font.font("Century Gothic", FontWeight.BLACK, 24));
        overview.setFont(Font.font("Century Gothic", FontWeight.BLACK, 24));
        place.setFont(Font.font("Century Gothic", FontWeight.BLACK, 24));
        
        setVboxes();
        setOverview();
        setSpeakers();
        setPlace();
        
        vbox1.getChildren().addAll(overview, vbox2, speakers, vbox3, place, vbox4);
        vbox1.setAlignment(Pos.TOP_CENTER);
        
        stackPane.getChildren().add(vbox1);
        stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, 
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,new BorderWidths(1), Insets.EMPTY)));
    }
    
    public ScrollPane symposiumShow() {
        ScrollPane scrollPane = new ScrollPane(stackPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        return scrollPane;
    }
    
    private void addSpeakers() {
        for(String a : symposium.getSpeakers()) {
            Label lblSpeaker = new Label((char)0x2022 + a);
            lblSpeaker.setFont(Font.font("Segoe UI", 24));
            this.speakers.add(lblSpeaker);
        }
    }
    
    private void setVboxes() {
        vbox1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        vbox2.prefWidthProperty().bind(stackPane.widthProperty());
        vbox2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1))));
        
        vbox3.prefWidthProperty().bind(stackPane.widthProperty());
        vbox3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1))));
        
        vbox4.prefWidthProperty().bind(stackPane.widthProperty());
        vbox4.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1))));
    }
    
    private void setOverview() {
        Label lblOverview = new Label(symposium.getOverview());
        lblOverview.setWrapText(true);
        
        lblOverview.setFont(Font.font("Segoe UI", 16));
        
        vbox2.getChildren().add(lblOverview);
    }
    
    private void setPlace() {
        Label lblPlace = new Label(symposium.getPlace());
        lblPlace.setFont(Font.font("Segoe UI", 18));
        
        vbox4.getChildren().add(lblPlace);
    }
    
    private void setSpeakers() {
        addSpeakers();
        FlowPane flowPane = new FlowPane();
        
        for(Label a : speakers) {
            flowPane.getChildren().add(a);
        }
        
        flowPane.setHgap(50);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setPrefWrapLength(150);
        
        Label lblVacancy = new Label("Vagas para palestrante: " + Integer.toString(2));
        lblVacancy.setFont(Font.font("Segoe UI", 16));
        
        vbox3.getChildren().addAll(flowPane, lblVacancy);
        vbox3.setAlignment(Pos.CENTER_RIGHT);
        
        VBox.setMargin(lblVacancy, new Insets(5, 10, 0, 0));
    }
    
    public HBox getBottom() {
        Label lblDate = new Label("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Button btnAnnals = new Button("Anais");
        Button btnSign = new Button("Inscrever");
        
        btnAnnals.setCursor(Cursor.HAND);
        btnSign.setCursor(Cursor.HAND);
        
        HBox hbox = new HBox(10);
        hbox.prefWidthProperty().bind(stackPane.widthProperty().subtract(100));
        hbox.getChildren().add(lblDate);
        if(privilege.getPrivilegeType() != PrivilegeTypeInterface.NOTLOGGED) {
            Button btnSubmit = new Button("Submeter");
            btnSubmit.setCursor(Cursor.HAND);
            
            hbox.getChildren().add(btnSubmit);
        }
        hbox.getChildren().addAll(btnAnnals, btnSign);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.setPadding(new Insets(10, 20, 5, 0));
        return hbox;
    }
}
