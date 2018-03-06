package app.control;

import app.control.interfaces.CRUDEvaluatorInterface;
import app.data.EvaluatorDAO;
import app.model.Category;
import app.model.Evaluator;
import app.model.Event;
import java.util.List;

public class CRUDEvaluator implements CRUDEvaluatorInterface {

    @Override
    public List<Evaluator> getAllTeachers() {
        return new EvaluatorDAO().getAllTeachers();
    }

    @Override
    public List<Evaluator> getAllEvaluators() {
        return new EvaluatorDAO().getAllEvaluators();
    }

    @Override
    public void updateEvaluator(Evaluator evaluator) {
        new EvaluatorDAO().updateEvaluator(evaluator);
    }

    @Override
    public Evaluator getEvaluatorById(long index) {
        return new EvaluatorDAO().getEvaluatorById(index);
    }

    @Override
    public boolean exists(String id) {
        return new EvaluatorDAO().exists(id);
    }

    @Override
    public List<Evaluator> getOnlyTeachers() {
        return new EvaluatorDAO().getOnlyTeachers();
    }

    @Override
    public void turnEvaluator(Evaluator evaluator) {
        new EvaluatorDAO().turnEvaluator(evaluator);
    }

    @Override
    public void addAnEvaluatorInAnEvent(Event event, Evaluator evaluator) {
        new EvaluatorDAO().addAnEvaluatorInAnEvent(event, evaluator);
    }

    @Override
    public List<Event> getAllEventsByEvaluator(Evaluator evaluator) {
        return new EvaluatorDAO().getAllEventsByEvaluator(evaluator);
    }

    @Override
    public void addInACategoryInAnEvaluator(Category category, Evaluator evaluator) {
        new EvaluatorDAO().addInACategoryInAnEvaluator(category, evaluator);
    }

    @Override
    public List<Category> getAllCategoriesByEvaluator(Evaluator evaluator) {
        return new EvaluatorDAO().getAllCategoriesByEvaluator(evaluator);
    }

    @Override
    public void deleteAllEvaluatorsCategories(Evaluator evaluator) {
        new EvaluatorDAO().deleteAllEvaluatorsCategories(evaluator);
    }
    
    
    
}
