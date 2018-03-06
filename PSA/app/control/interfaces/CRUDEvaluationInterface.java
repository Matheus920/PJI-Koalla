package app.control.interfaces;

import app.data.EvaluationDAOInterface;
import app.model.Article;


public interface CRUDEvaluationInterface extends EvaluationDAOInterface{
    
    public void makeArticleEvaluation(Article article);          
    public boolean isTheLastEvaluation(Article article);
    
}
