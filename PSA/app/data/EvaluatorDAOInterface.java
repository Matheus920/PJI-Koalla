package app.data;

import app.model.Category;
import app.model.Evaluator;
import app.model.Event;
import app.model.User;
import java.util.List;



public interface EvaluatorDAOInterface {
    
    public List<Evaluator> getAllTeachers();
    public List<Evaluator> getOnlyTeachers();
    public List<Evaluator> getAllEvaluators();
    public void updateEvaluator(Evaluator evaluator);
    public Evaluator getEvaluatorById(long index);
    public boolean exists(String id);
    public void turnEvaluator(Evaluator evaluator);
    public void addAnEvaluatorInAnEvent(Event event, Evaluator evaluator);
    public List<Event> getAllEventsByEvaluator(Evaluator evaluator);
    public void addInACategoryInAnEvaluator(Category category, Evaluator evaluator);
    public List<Category> getAllCategoriesByEvaluator(Evaluator evaluator);
    public void deleteAllEvaluatorsCategories(Evaluator evaluator);
    
}
