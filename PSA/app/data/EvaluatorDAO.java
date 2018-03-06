package app.data;

import app.model.Category;
import app.model.Evaluator;
import app.model.Event;
import app.model.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EvaluatorDAO implements EvaluatorDAOInterface{

    private final static String SQL_SELECT_ALL = "select * from professor order by nome;";
    private final static String SQL_SELECT_EVALUATORS = "select * from professor where avaliador is true order by nome;";
    private final static String SQL_SELECT_TEACHERS = "select * from professor where avaliador is false order by nome;";
    private final static String SQL_SELECT_EVALUATOR_BY_ID = "select * from professor where id = ?;";
    private final static String SQL_UPDATE = "update professor set nome = ?, prontuario = ?, area = ?, avaliador = ?, data_nascimento = ? where id = ?";
    private final static String SQL_TO_EVALUTOR = "update professor set avaliador = true where id = ?;";
    private final static String SQL_CATEGORY = "insert into especializacao(id_categoria, id_professor) values (?, ?);";
    private final static String SQL_WHERE_CATEGORY = "select categoria.* from especializacao inner join categoria on "
            + "categoria.id = especializacao.id_categoria where id_professor = ?;";
    private final static String SQL_DELETE_CATEGORY = "delete from especializacao where id_professor = ?;";
    
    private Connection conexao;
    

    @Override
    public List<Evaluator> getAllTeachers() {
        conexao = ConnectionMySQL.openConnection();
        List<Evaluator> resultados = new ArrayList<>();
        
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Login login = new LoginDAO().getLoginById(rs.getLong(2));
                
                Evaluator e = new Evaluator(rs.getLong(1), login, rs.getString(3), rs.getString(4), rs.getTimestamp(5).toLocalDateTime().toLocalDate(),
                rs.getString(6), rs.getBoolean(7));
                
                resultados.add(e);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }

    @Override
    public List<Evaluator> getAllEvaluators() {
        conexao = ConnectionMySQL.openConnection();
        List<Evaluator> resultados = new ArrayList<>();
        
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_EVALUATORS);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Login login = new LoginDAO().getLoginById(rs.getLong(2));
                
                Evaluator e = new Evaluator(rs.getLong(1), login, rs.getString(3), rs.getString(4), rs.getTimestamp(5).toLocalDateTime().toLocalDate(),
                rs.getString(6), rs.getBoolean(7));
                
                resultados.add(e);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }

    @Override
    public void updateEvaluator(Evaluator evaluator) {
        conexao = ConnectionMySQL.openConnection();
        
        LocalDateTime ldt = LocalDateTime.of(evaluator.getDataNascimento(), LocalTime.now());
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, evaluator.getNome());
            preparedStatement.setString(2, evaluator.getProntuario());
            preparedStatement.setString(3, evaluator.getArea());
            preparedStatement.setBoolean(4, evaluator.isAvaliador());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(ldt));
            preparedStatement.setLong(6, evaluator.getId());
            
           preparedStatement.executeUpdate();
           
        }
        catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public boolean exists(String id){
        conexao = ConnectionMySQL.openConnection();
        boolean resultado = false;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from professor where prontuario = ?;");
            preparedStatement.setString(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                resultado = true;
            }
        }
        catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return resultado;
    }  
    
    @Override
    public Evaluator getEvaluatorById(long id){
        conexao = ConnectionMySQL.openConnection();
        Evaluator evaluator = null;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_EVALUATOR_BY_ID);
            preparedStatement.setLong(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                Login login = new LoginDAO().getLoginById(rs.getLong(2));
                
                evaluator = new Evaluator(rs.getLong(1), login, rs.getString(3), rs.getString(4), rs.getTimestamp(5).toLocalDateTime().toLocalDate(),
                rs.getString(6), rs.getBoolean(7));
            }
        }
         catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return evaluator;
    }

    @Override
    public List<Evaluator> getOnlyTeachers() {
        conexao = ConnectionMySQL.openConnection();
        List<Evaluator> resultados = new ArrayList<>();
        
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_TEACHERS);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Login login = new LoginDAO().getLoginById(rs.getLong(2));
                
                Evaluator e = new Evaluator(rs.getLong(1), login, rs.getString(3), rs.getString(4), rs.getTimestamp(5).toLocalDateTime().toLocalDate(),
                rs.getString(6), rs.getBoolean(7));
                
                resultados.add(e);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }

    @Override
    public void turnEvaluator(Evaluator evaluator) {
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_TO_EVALUTOR);
            preparedStatement.setLong(1, evaluator.getId());
            preparedStatement.executeUpdate();
            
        }
        catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        
    }
    
    @Override
    public void addAnEvaluatorInAnEvent(Event event, Evaluator evaluator){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("insert into eventos_avaliadores(id_evento, id_professor) "
                    + "values(?, ?);");
            preparedStatement.setLong(1, event.getId());
            preparedStatement.setLong(2, evaluator.getId());
            preparedStatement.executeUpdate();
            
        }catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public List<Event> getAllEventsByEvaluator(Evaluator evaluator){
        conexao = ConnectionMySQL.openConnection();
        List<Event> resultados = new ArrayList<>();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select evento.* from evento inner join "
                    + "eventos_avaliadores on eventos_avaliadores.id_evento = evento.id where id_professor = ?;");
            preparedStatement.setLong(1, evaluator.getId());
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                                resultSet.getString(3), resultSet.getString(4), 
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));
            
                resultados.add(evento);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    @Override
    public void addInACategoryInAnEvaluator(Category category, Evaluator evaluator){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_CATEGORY);
            preparedStatement.setLong(1, category.getId());
            preparedStatement.setLong(2, evaluator.getId());
            
            preparedStatement.executeUpdate();
            
        }catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public List<Category> getAllCategoriesByEvaluator(Evaluator evaluator){
        conexao = ConnectionMySQL.openConnection();
        List<Category> resultados = new ArrayList<>();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_WHERE_CATEGORY);
            preparedStatement.setLong(1, evaluator.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Category categoria = new Category(rs.getLong(1), rs.getString(2));
                
                resultados.add(categoria);
            }
        }catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }

    @Override
    public void deleteAllEvaluatorsCategories(Evaluator evaluator) {
        conexao = ConnectionMySQL.openConnection();
        
        try{
            
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_DELETE_CATEGORY);
            preparedStatement.setLong(1, evaluator.getId());
            
            preparedStatement.executeUpdate();
            
        }catch(SQLException ex){
            Logger.getLogger(EvaluatorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
    }
}
