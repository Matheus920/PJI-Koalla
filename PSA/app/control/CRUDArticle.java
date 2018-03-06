package app.control;

import app.control.interfaces.CRUDArticleInterface;
import app.data.ArticleDAO;
import app.model.Article;
import app.model.Evaluator;
import app.model.Event;
import java.util.List;

public class CRUDArticle implements CRUDArticleInterface{

    @Override
    public Article addArticle(Article article) {
        return new ArticleDAO().addArticle(article);
    }

    @Override
    public List<Article> getAllArticlesByEvent(Event event) {
        return new ArticleDAO().getAllArticlesByEvent(event);
    }

    @Override
    public Article getArticleById(long id) {
        return new ArticleDAO().getArticleById(id);
    }

    @Override
    public List<Article> getAllArticlesByEvaluatorAndEvent(Event event) {
        return new ArticleDAO().getAllArticlesByEvaluatorAndEvent(event);
    }

    @Override
    public int getArticleQuantityAlreadyEvaluated(Article article) {
        return new ArticleDAO().getArticleQuantityAlreadyEvaluated(article);
    }

    @Override
    public void updateArticle(Article article) {
        new ArticleDAO().updateArticle(article);
    }
    
    
}
