package app.control;

import app.control.interfaces.PrivilegeTypeInterface;
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

    public PrivilegiesTest() {
        list = new ArrayList<>();
        setPrivilegeType(PrivilegeTypeInterface.ADMIN);
    }
    
    @Override
    public List<String> getButtons() {
        return list;
    }
    
    @Override
    public void setEvaluatorButtons() {
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
        list.add("Palestrante");
        list.add("Avaliadores");
        list.add("Comitê");
        list.add("Avaliação");
    }
    
    @Override
    public void setNotLoggedButtons() {
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
        list.add("Palestrante");
        list.add("Avaliadores");
        list.add("Comitê");
    }

    @Override
    public void setAdminButtons() {
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
        list.add("Palestrante");
        list.add("Avaliadores");
        list.add("Comitê");
        list.add("Categoria");
    }

    @Override
    public void setBoardButtons() {
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
        list.add("Palestrante");
        list.add("Avaliadores");
        list.add("Comitê");
        list.add("Critérios");
    }
    
    @Override
    public void setUserButtons(){
        if(!list.isEmpty())
            list.clear();
        list.add("Evento");
        list.add("Calendário");
        list.add("Palestrante");
        list.add("Avaliadores");
        list.add("Comitê");
    }
    @Override
    public int getAmountButtons() {
        return list.size();
    }
    
    @Override
    public int getPrivilegeType() {
        return type;
    }
    
    @Override
    public void setPrivilegeType(int type) {
        this.type = type;
        switch(this.type) {
            case PrivilegeTypeInterface.ADMIN:
                setAdminButtons();
                break;
            case PrivilegeTypeInterface.BOARD:
                setBoardButtons();
                break;
            case PrivilegeTypeInterface.EVALUATOR:
                setEvaluatorButtons();
                break;
            case PrivilegeTypeInterface.NOTLOGGED:
                setNotLoggedButtons();
                break;
            case PrivilegeTypeInterface.USER:
                setUserButtons();
                break;
        }
    }
}