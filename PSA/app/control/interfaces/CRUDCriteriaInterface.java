package app.control.interfaces;

import java.util.List;


public interface CRUDCriteriaInterface {
    public List<String> getAllCriteria();
    public String getCriteriaById(int id);
    public void deleteCriteriaById(int id);
    public void updateCriteriaById(int id, String value);
    public void addCriteria(String value);
}
