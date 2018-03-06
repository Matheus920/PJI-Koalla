package app.data;

import app.model.Article;
import app.model.Evaluation;
import java.util.List;


public interface EvaluationDAOInterface {
    public Evaluation addEvaluation(Evaluation evaluation);
    public List<Evaluation> getAllEvaluationsByArticle(Article article);
}
