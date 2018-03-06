package app.data;

import app.model.Criteria;
import app.model.CriteriaWithValue;
import app.model.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CriteriaDAO implements CriteriaDAOInterface{
     private Connection conexao;
    private static final String SQL_INSERT = "INSERT INTO criterio(nome, descricao) VALUES (?, ?);";
    private static final String SQL_SELECT = "SELECT * FROM criterio order by nome;";
    private static final String SQL_UPDATE = "UPDATE criterio SET nome = ?, descricao = ? WHERE id = ?;";
    private static final String SQL_DELETE = "DELETE FROM criterio WHERE id = ?";
    private static final String SQL_SELECT_WHERE = "SELECT * FROM criterio WHERE nome = ?;";
    private static final String SQL_SELECT_WHERE_ID = "SELECT * FROM criterio WHERE id = ?;";
    private static final String SQL_CRITERIA_EVENT = "INSERT INTO eventos_criterios(id_evento, id_criterio, peso) VALUES "
            + "(?, ?, ?);";
    private static final String SQL_SELECT_CRITERIA_EVENT = "select id_criterio, peso from eventos_criterios where id_evento = ?;";
    
    
    @Override
    public Criteria addCriteria(Criteria criteria){
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, criteria.getNome());
            preparedStatement.setString(2, criteria.getDescricao());
            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long id = 0;
            
            if(resultSet.next()){
                id = resultSet.getLong(1);
            }
            
            criteria.setId(id);
            
        }
        catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return criteria;
    }
    
    @Override
    public List<Criteria> getAllCriteria(){
        List<Criteria> resultados = new ArrayList<>();
        
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Criteria criteria = new Criteria(resultSet.getString(2));
                criteria.setId(resultSet.getLong(1));
                criteria.setDescricao(resultSet.getString(3));
                resultados.add(criteria);
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
   
    
    @Override
    public void updateCriteria(Criteria criteria){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, criteria.getNome());
            preparedStatement.setLong(3, criteria.getId());
            preparedStatement.setString(2, criteria.getDescricao());
            preparedStatement.executeUpdate();
            }
           
                
        
        catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public void deleteCriteria(Criteria criteria){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, criteria.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    

    public boolean exists(String name){
        conexao = ConnectionMySQL.openConnection();
        boolean resultado = false;
        try{
           PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_WHERE);
           preparedStatement.setString(1, name);
           ResultSet resultSet = preparedStatement.executeQuery();
           
           if(resultSet.next()){
               resultado = true;
           } 
        }
        catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultado;
    }
    
    @Override
    public Criteria getCriteriaById(long id){
        conexao = ConnectionMySQL.openConnection();
        Criteria criteria = null;
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_WHERE_ID);
            preparedStatement.setLong(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                criteria = new Criteria(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return criteria;
    }
    
    
    @Override
    public List<String> getAllCriteriaNames() {
        List<String> resultados = new ArrayList<>();
        
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("SELECT nome FROM criterio;");
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                resultados.add(resultSet.getString(1));
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    @Override
    public void addACriteriaInAnEvent(Event event, Criteria criteria, int peso){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_CRITERIA_EVENT);
            preparedStatement.setLong(1, event.getId());
            preparedStatement.setLong(2, criteria.getId());
            preparedStatement.setInt(3, peso);
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public List<CriteriaWithValue> getAllCriteriaFromAnEvent(Event event){
        conexao = ConnectionMySQL.openConnection();
        List<CriteriaWithValue> resultados = new ArrayList<>();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_CRITERIA_EVENT);
            preparedStatement.setLong(1, event.getId());
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                CriteriaWithValue result = new CriteriaWithValue(getCriteriaById(rs.getLong(1)), rs.getInt(2));
                resultados.add(result);
            }
        }catch(SQLException ex){
            Logger.getLogger(CriteriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
}
