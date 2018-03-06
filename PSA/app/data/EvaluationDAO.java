package app.data;

import app.model.Article;
import app.model.Evaluation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EvaluationDAO implements EvaluationDAOInterface{
    private Connection conexao;
    private static final String SQL_INSERT = "insert into avaliacao(resultado, mensagem, id_avaliador, id_artigo) values"
            + "(?, ?, ?, ?);";
    private static final String SQL_WHERE = "select * from avaliacao where id_artigo = ?;";
    
    @Override
    public Evaluation addEvaluation(Evaluation evaluation){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setBoolean(1, evaluation.isResultado());
            preparedStatement.setString(2, evaluation.getMensagem());
            preparedStatement.setLong(3, evaluation.getAvaliador().getId());
            preparedStatement.setLong(4, evaluation.getArtigo().getId());
            
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            
            if(rs.next()){
                evaluation.setId(rs.getLong(1));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return evaluation;
    }

    @Override
    public List<Evaluation> getAllEvaluationsByArticle(Article article) {
        List<Evaluation> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_WHERE);
            preparedStatement.setLong(1, article.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Evaluation evaluation = new Evaluation(rs.getLong(1), rs.getBoolean(2), rs.getString(3), 
                        new EvaluatorDAO().getEvaluatorById(rs.getLong(4)), new ArticleDAO().getArticleById(rs.getLong(5)));
                
                resultados.add(evaluation);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
        
    }
    
    
}
