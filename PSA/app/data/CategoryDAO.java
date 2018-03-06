
package app.data;

import app.data.CategoryDAOInterface;
import app.model.Category;
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


public class CategoryDAO implements CategoryDAOInterface {
    private Connection conexao;
    private static final String SQL_INSERT = "INSERT INTO categoria(nome) VALUES (?);";
    private static final String SQL_SELECT = "SELECT * FROM categoria order by nome;";
    private static final String SQL_UPDATE = "UPDATE categoria SET nome = ? WHERE id = ?;";
    private static final String SQL_DELETE = "DELETE FROM categoria WHERE id = ?";
    private static final String SQL_SELECT_WHERE = "SELECT * FROM categoria WHERE nome = ?;";
    private static final String SQL_SELECT_WHERE_ID = "SELECT * FROM categoria WHERE id = ?;";
    private static final String SQL_CATEGORIES_EVENT = "INSERT INTO eventos_categorias(id_evento, id_categoria) "
            + "VALUES(?, ?);";
    
    @Override
    public Category addCategory(Category category){
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.getNome());
            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long id = 0;
            
            if(resultSet.next()){
                id = resultSet.getLong(1);
            }
            
            category.setId(id);
            
        }
        catch(SQLException ex){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return category;
    }
    
    @Override
    public List<Category> getAllCategories(){
        List<Category> resultados = new ArrayList<>();
        
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Category category = new Category(resultSet.getString(2));
                category.setId(resultSet.getLong(1));
                resultados.add(category);
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
   
    
    @Override
    public void updateCategory(Category category){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, category.getNome());
            preparedStatement.setLong(2, category.getId());
            preparedStatement.executeUpdate();
            }
           
                
        
        catch(SQLException ex){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public void deleteCategory(Category category){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, category.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultado;
    }
    
    @Override
    public Category getCategoryById(long id){
        conexao = ConnectionMySQL.openConnection();
        Category category = null;
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_WHERE_ID);
            preparedStatement.setLong(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                category = new Category(resultSet.getLong(1), resultSet.getString(2));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }
    
    
    @Override
    public List<String> getAllCategoriesNames() {
        List<String> resultados = new ArrayList<>();
        
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("SELECT nome FROM categoria;");
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                resultados.add(resultSet.getString(1));
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }

    @Override
    public void addCategoryInAnEvent(Category category, Event event) {
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_CATEGORIES_EVENT);
            preparedStatement.setLong(1, event.getId());
            preparedStatement.setLong(2, category.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
    }
    
    
}