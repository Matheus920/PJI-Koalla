package app.view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Menu {
    private final Button btnEvento = new Button("Evento");
    private final Button btnCalendario = new Button("Calendário");
    private final Button btnComite = new Button("Comitê");
    private final Button btnPalestrante = new Button("Palestrante");
    private final Button btnCategoria = new Button("Categoria");
    private VBox vbox = new VBox(0);
    
    public Menu()
    {
        vbox.getChildren().addAll(btnEvento, btnCalendario, btnComite, btnPalestrante, btnCategoria);
        //vbox.setPadding(new Insets(0, 0, 0, 2));
        setButtons();
    }
    
    public VBox menuShow()
    {
        return vbox;
    }
    
    private void setButtons() {
        btnEvento.setFont(Font.font("Segoe UI", 15));
        btnEvento.setPrefSize(100, 70);
        btnEvento.setCursor(Cursor.HAND);
        btnEvento.setOnAction(e->{
        });
        
        btnComite.setFont(Font.font("Segoe UI", 15));
        btnComite.setPrefSize(100, 70);
        btnComite.setCursor(Cursor.HAND);
        btnComite.setOnAction(e->{
            
        });
        
        btnCalendario.setFont(Font.font("Segoe UI", 15));
        btnCalendario.setPrefSize(100, 70);
        btnCalendario.setCursor(Cursor.HAND);
        btnCalendario.setOnAction(e->{
            
        });
        
        btnCategoria.setFont(Font.font("Segoe UI", 15));
        btnCategoria.setPrefSize(100, 70);
        btnCategoria.setCursor(Cursor.HAND);
        btnCategoria.setOnAction(e->{
            
        });
        
        btnPalestrante.setFont(Font.font("Segoe UI", 15));
        btnPalestrante.setPrefSize(100, 70);
        btnPalestrante.setCursor(Cursor.HAND);
        btnPalestrante.setOnAction(e->{
            
        });
    }
}
