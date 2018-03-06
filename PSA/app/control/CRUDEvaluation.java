package app.control;

import app.control.interfaces.CRUDEvaluationInterface;
import app.data.ArticleDAO;
import app.data.EvaluationDAO;
import app.data.EventDAO;
import app.model.Article;
import app.model.Evaluation;
import java.util.ArrayList;
import java.util.List;


public class CRUDEvaluation implements CRUDEvaluationInterface{

    @Override
    public Evaluation addEvaluation(Evaluation evaluation) {
        return new EvaluationDAO().addEvaluation(evaluation);
    }

    
    public boolean isTheLastEvaluation(Article article) {
        int evaluatorsQuantity = new EventDAO().getQuantityofEvaluatorsByEvent(article.getEvento());
        int articlesQuantity = new ArticleDAO().getArticleQuantityAlreadyEvaluated(article);
        
        if(evaluatorsQuantity == 1){
            if((evaluatorsQuantity - articlesQuantity) == 0){
            return true;
        } else{
            return false;
        }
        }
        else if((evaluatorsQuantity - articlesQuantity) == 1){
            return true;
        }else{
            return false;
    }
        
     
    }

    @Override
    public void makeArticleEvaluation(Article article) {
        List<Evaluation> evaluations = new ArrayList<>();
        int sim = 0;
        int nao = 0;
        if(isTheLastEvaluation(article)){
            evaluations = getAllEvaluationsByArticle(article);
            
            for(Evaluation a : evaluations){
                if(a.isResultado()){
                    sim++;
                }else{
                    nao++;
                }
            }
            
            if(sim >= nao){
                article.setAprovacao(true);
                new CRUDArticle().updateArticle(article);
            }else{
                article.setAprovacao(false);
                new CRUDArticle().updateArticle(article);
            }
            
        }
    }

    @Override
    public List<Evaluation> getAllEvaluationsByArticle(Article article) {
        return new EvaluationDAO().getAllEvaluationsByArticle(article);
    }
    
    
    
}
