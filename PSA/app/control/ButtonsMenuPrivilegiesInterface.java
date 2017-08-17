package app.control;

import java.util.List;
import javafx.scene.control.Button;

public interface ButtonsMenuPrivilegiesInterface {
    public List<String> judge();
    public List<String> user();
    public List<String> notLogged();
    public List<String> admin();
    public List<String> board();
}
