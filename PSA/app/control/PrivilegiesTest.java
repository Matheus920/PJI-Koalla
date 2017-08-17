package app.control;

import app.control.interfaces.PrivilegiesButtonsInterface;
import java.util.ArrayList;
import java.util.List;

public class PrivilegiesTest implements PrivilegiesButtonsInterface {
    private int type;
    private List<String> list;
    
    public PrivilegiesTest(int type) {
        list = new ArrayList<>();
        setPrivilegeType(type);
    }
    
    @Override
    public List<String> getButtons() {
        return list;
    }
    
    private void setEvaluatorButtons() {
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
        list.add("Avaliação");
    }
    
    private void setNotLoggedButtons() {
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
    }

    private void setAdminButtons() {
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
        list.add("Comitê");
        list.add("Palestrante");
        list.add("Categoria");
    }

    private void setBoardButtons() {
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
        list.add("Avaliadores");
    }
    
    private void setUserButtons(){
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
    }
    
    @Override
    public int getPrivilegeType() {
        return type;
    }
    
    @Override
    public void setPrivilegeType(int type) {
        this.type = type;
        switch(this.type) {
            case PrivilegeType.ADMIN:
                setAdminButtons();
                break;
            case PrivilegeType.BOARD:
                setBoardButtons();
                break;
            case PrivilegeType.EVALUATOR:
                setEvaluatorButtons();
                break;
            case PrivilegeType.NOTLOGGED:
                setNotLoggedButtons();
                break;
            case PrivilegeType.USER:
                setUserButtons();
                break;
        }
    }
}