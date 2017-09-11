package app.control.interfaces;

import java.util.List;


public interface CRUDEvaluatorInterface {
    
    public List<String> getAllEvaluators();
    public String getEvaluatorById(int id);
    public void deleteEvaluator(int id);
    public void updateEvaluator(int id, String value);
    public void addEvaluator(String value);
    
}
