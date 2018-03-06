package app.data;

import app.model.Article;
import app.model.Evaluator;
import app.model.Event;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ArticleDAO implements ArticleDAOInterface{
    
    private Connection conexao;
    private static final String SQL_INSERT = "insert into artigo(titulo, caminho, id_evento, palestrante, id_usuario, aprovado)"
            + " values(?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_BY_EVENT = "select * from artigo where id_evento = ? and aprovado is true;";
    private static final String SQL_SELECT_ID = "select * from artigo where id = ?;";
    private static final String SQL_ARTICLE_EVALUATOR = "select * from artigo\n" +
"    where artigo.id not in (select avaliacao.id_artigo from avaliacao) and id_evento = ?;";
    private static final String SQL_UPDATE = "update artigo set titulo = ?, caminho = ?, "
            + "id_evento = ?, palestrante = ?, id_usuario = ?, aprovado = ? where id = ?;";
    
    @Override
    public Article addArticle(Article article){
        conexao = ConnectionMySQL.openConnection();
        long id = 0;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, article.getTitulo());
            preparedStatement.setString(2, article.getCaminho());
            preparedStatement.setLong(3, article.getEvento().getId());
            preparedStatement.setBoolean(4, article.isPalestrante());
            preparedStatement.setLong(5, article.getUsuario().getId());
            preparedStatement.setBoolean(6, article.isAprovacao());
            
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            
            if(rs.next()){
                id = rs.getLong(1);
            }
            article.setId(id);
        }
        catch(SQLException ex){
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return article;
    }
    
    @Override
    public List<Article> getAllArticlesByEvent(Event event){
        List<Article> resultados = new ArrayList<>();
        
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_BY_EVENT);
            preparedStatement.setLong(1, event.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Article article = new Article(rs.getLong(1), rs.getString(2), rs.getString(3), new EventDAO().getEventById(rs.getLong(4)),
                rs.getBoolean(5), new UserDAO().getUserById(rs.getLong(6)), rs.getBoolean(7));
                
                resultados.add(article);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    @Override
    public Article getArticleById(long id){
        conexao = ConnectionMySQL.openConnection();
        Article article = null;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_ID);
            preparedStatement.setLong(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                article =  new Article(rs.getLong(1), rs.getString(2), rs.getString(3), 
                        new EventDAO().getEventById(rs.getLong(4)),
                        rs.getBoolean(5), new UserDAO().getUserById(rs.getLong(6)), rs.getBoolean(7));
            }
        }catch(SQLException ex){
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return article;
    }
    
    @Override
    public List<Article> getAllArticlesByEvaluatorAndEvent(Event event){
        List<Article> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_ARTICLE_EVALUATOR);
            preparedStatement.setLong(1, event.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Article article = new Article(rs.getLong(1), rs.getString(2), rs.getString(3), 
                        new EventDAO().getEventById(rs.getLong(4)),
                        rs.getBoolean(5), new UserDAO().getUserById(rs.getLong(6)), rs.getBoolean(7));
                
                resultados.add(article);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    @Override
    public int getArticleQuantityAlreadyEvaluated(Article article){
        conexao = ConnectionMySQL.openConnection();
        int resultado = 0;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select count(*) from avaliacao where id_artigo = ?; ");
            preparedStatement.setLong(1, article.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                resultado = rs.getInt(1);
            }
        }catch(SQLException ex){
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return resultado;
    }

    @Override
    public void updateArticle(Article article) {
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, article.getTitulo());
            preparedStatement.setString(2, article.getCaminho());
            preparedStatement.setLong(3, article.getEvento().getId());
            preparedStatement.setBoolean(4, article.isPalestrante());
            preparedStatement.setLong(5, article.getUsuario().getId());
            preparedStatement.setBoolean(6, article.isAprovacao());
            preparedStatement.setLong(7, article.getId());
            
            preparedStatement.executeUpdate();
            
            
        }catch(SQLException ex){
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
    }
}
