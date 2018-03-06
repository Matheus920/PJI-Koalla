package app.control;

import app.control.interfaces.CRUDCriteriaInterface;
import app.data.CriteriaDAO;
import app.model.Criteria;
import app.model.CriteriaWithValue;
import app.model.Event;
import java.util.List;

public class CRUDCriteria implements CRUDCriteriaInterface{
    
    @Override
    public Criteria addCriteria(Criteria category) {
       return new CriteriaDAO().addCriteria(category);
    }

    @Override
    public void updateCriteria(Criteria category) {
        new CriteriaDAO().updateCriteria(category);
    }

    @Override
    public void deleteCriteria(Criteria category) {
        new CriteriaDAO().deleteCriteria(category);
    }

    @Override
    public Criteria getCriteriaById(long id) {
        return new CriteriaDAO().getCriteriaById(id);
    }

    @Override
    public List<Criteria> getAllCriteria() {
        return new CriteriaDAO().getAllCriteria();
    }

    @Override
    public boolean exists(String name){
        return new CriteriaDAO().exists(name);
    }
    
    @Override
    public List<String> getAllCriteriaNames() {
        return new CriteriaDAO().getAllCriteriaNames();
    }

    @Override
    public void addACriteriaInAnEvent(Event event, Criteria criteria, int peso) {
        new CriteriaDAO().addACriteriaInAnEvent(event, criteria, peso);
    }

    @Override
    public List<CriteriaWithValue> getAllCriteriaFromAnEvent(Event event) {
        return new CriteriaDAO().getAllCriteriaFromAnEvent(event);
    }
    
}
