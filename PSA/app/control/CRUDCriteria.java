
package app.control;

import app.control.interfaces.CRUDCriteriaInterface;
import java.util.ArrayList;
import java.util.List;

public class CRUDCriteria implements CRUDCriteriaInterface{
    
private List<String> list;
    
    public CRUDCriteria() {
        list = new ArrayList<>();
        setList("Normas ABNT", "Regras de Conduta", "Utilização de Argumentos Formais",
                "Lógica Bem Construída");
    }
    
    private void setList(String... args)
    {
        for(String a : args) {
            list.add(a);
        }
    }
    
    @Override
    public List<String> getAllCriteria() {
        return list;
    }

    @Override
    public String getCriteriaById(int id) {
        return list.get(id);
    }
    
    @Override
    public void deleteCriteriaById(int id) {
        list.remove(id);
    }

    @Override
    public void updateCriteriaById(int id, String value) {
        list.set(id, value);
    }

    @Override
    public void addCriteria(String value) {
        list.add(value);
    }
    
}
