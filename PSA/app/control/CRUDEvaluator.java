package app.control;

import app.control.interfaces.CRUDEvaluatorInterface;
import java.util.ArrayList;
import java.util.List;

public class CRUDEvaluator implements CRUDEvaluatorInterface {
    
    private List<String> list;
    
    
    
    public CRUDEvaluator(){
        list = new ArrayList<>();
        setList("Glayson", "Hernando", "Manuel", "Matheus", "Amanda", "Denis", "Arnaldo", "Cesar", "Douglas");
    }
    
    
    private void setList(String... args){
        for(String a : args){
            list.add(a);
        }
    }
   
    @Override
    public List<String> getAllEvaluators() {
        return list;
    }

    @Override
    public String getEvaluatorById(int id) {
        return list.get(id);
    }
    
    @Override
    public void deleteEvaluator(int id) {
        list.remove(id);
    }

    @Override
    public void updateEvaluator(int id, String value) {
        list.set(id, value);
    }

    @Override
    public void addEvaluator(String value) {
        list.add(value);
    }
    
}
