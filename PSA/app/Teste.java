package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class Teste extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, 1000, 800);
        MaskFormatter mf = new MaskFormatter();
        mf.setMask("######A");
        mf.setValidCharacters("123456789xX0");
        JFormattedTextField ftf = new JFormattedTextField(mf);
        JOptionPane.showOptionDialog(null, new Object[] {"Digita aqui, seu porra:", ftf}, "", 0, 0, null, new Object[] { "glayson", "boladao" }, null);
        stage.setScene(scene);
        stage.setTitle("SplitPane");
        stage.show();
    }
    
}
