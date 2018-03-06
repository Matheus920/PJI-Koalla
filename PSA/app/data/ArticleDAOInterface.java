package app.data;

import app.model.Article;
import app.model.Event;
import java.util.List;


public interface ArticleDAOInterface {
    public Article addArticle(Article article);
    public List<Article> getAllArticlesByEvent(Event event);
    public Article getArticleById(long id);
    public List<Article> getAllArticlesByEvaluatorAndEvent(Event event);
    public int getArticleQuantityAlreadyEvaluated(Article article);
    public void updateArticle(Article article);
}
