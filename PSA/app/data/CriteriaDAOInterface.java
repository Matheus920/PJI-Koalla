package app.data;

import app.model.Criteria;
import java.util.List;


public interface CriteriaDAOInterface {
    
    public Criteria addCriteria(Criteria criteria);
    public void updateCriteria(Criteria criteria);
    public void deleteCriteria(Criteria criteria);
    public Criteria getCriteriaById(long id);
    public List<Criteria> getAllCriteria();
    public boolean exists(String name);
    public List<String> getAllCriteriaNames();
}
