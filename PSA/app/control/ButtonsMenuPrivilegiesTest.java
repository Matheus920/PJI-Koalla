package app.control;

import java.util.ArrayList;
import java.util.List;

public class ButtonsMenuPrivilegiesTest implements ButtonsMenuPrivilegiesInterface {
    
    private 
    
    public ButtonsMenuPrivilegiesTest() {
        
    }
    
    @Override
    public List<String> judge() {
        List<String> list = new ArrayList<>();
        list.add("Evento");
        list.add("Calendário");
        list.add("Evento");
    }

    @Override
    public List<String> user() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> notLogged() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> admin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> board() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
