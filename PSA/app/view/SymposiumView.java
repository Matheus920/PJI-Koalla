package app.view;

import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SymposiumView {
    private final VBox vbox1 = new VBox(10);
    private final VBox vbox2 = new VBox(2);
    private final VBox vbox3 = new VBox(5);
    private final VBox vbox4 = new VBox(5);
    
    public SymposiumView() {
        Label speakers = new Label("Palestrante");
        Label overview = new Label("Resumo");
        Label place = new Label("Local");
        
        vbox1.getChildren().addAll(overview, vbox2, speakers, vbox3, place, vbox4);
    }
    
    public void addSpeakers(List<String> speakers) {
        
    }
    
    public void addSpeakers(String... speakers) {
        
    }
    
    public void setPlace(String place) {
        
    }
}
