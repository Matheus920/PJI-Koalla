package app.data;

import app.control.LoginController;
import app.control.PrivilegiesTest;
import app.control.interfaces.PrivilegeTypeInterface;
import app.model.Board;
import app.model.Evaluator;
import app.model.Login;
import app.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO implements LoginDAOInterface{
    
    private Connection conexao;
    private static final String SQL_INSERT = "insert into login(email, senha, privilegio) values (?, ?, ?);";
    private static final String SQL_USER = "select usuario.* from usuario inner join login on usuario.id_login = login.id "
            + "where id_login = ?;";
    private static final String SQL_BOARD = "select comite.* from comite inner join login on comite.id_login = login.id "
            + "where id_login = ?;";
    private static final String SQL_EVALUATOR = "select professor.* from professor inner join login on "
            + "professor.id_login = login.id "
            + "where id_login = ?;";
    
    public boolean exists(String email, String password, PrivilegeTypeInterface test){
        
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select privilegio from login"
                    + " where email = ? and senha = ?;");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                
                switch (rs.getInt(1)) {
                    case PrivilegiesTest.ADMIN:
                        test.setPrivilegeType(0);
                        break;
                    case PrivilegiesTest.BOARD:
                        test.setPrivilegeType(4);
                        break;
                    case PrivilegiesTest.EVALUATOR:
                        test.setPrivilegeType(2);
                        break;
                    case PrivilegiesTest.USER:
                        test.setPrivilegeType(1);
                        break;
                    default:
                        break;
                }
              return true;  
              }
              ConnectionMySQL.closeConnection(conexao);
              return false;  
        }
     
        catch(SQLException ex){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Login getLoginById(long id){
        conexao = ConnectionMySQL.openConnection();
        Login login = null;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from login where id = ?;");
            preparedStatement.setLong(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                login = new Login(rs.getLong(1), rs.getString(2), rs.getString(3),
                rs.getInt(4));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return login;
    }
    
    @Override
    public void updateLogin(Login login){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("update login set email = ?,"
                    + "senha = ?, privilegio = ? where id = ?;");
            preparedStatement.setString(1, login.getEmail());
            preparedStatement.setString(2, login.getSenha());
            preparedStatement.setInt(3, login.getPrivilegio());
            preparedStatement.setLong(4, login.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public Login addLogin(Login login){
        conexao = ConnectionMySQL.openConnection();
        long id = 0;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, login.getEmail());
            preparedStatement.setString(2, login.getSenha());
            preparedStatement.setInt(3, login.getPrivilegio());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            
            if(rs.next()){
                id = rs.getLong(1);
            }
            
            login.setId(id);
        }        
        catch(SQLException ex){
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return login;
    }
    
    @Override
    public boolean emailExists(String email){
        conexao = ConnectionMySQL.openConnection();
        boolean resultado = false;
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from login where email = ?;");
            preparedStatement.setString(1, email);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                resultado = true;
            }
        }
        catch(SQLException ex){
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultado;
    }
    
    @Override
    public User getUserByLogin(Login login){
        conexao = ConnectionMySQL.openConnection();
        User user = null;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_USER);
            preparedStatement.setLong(1, login.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                user = new User(rs.getLong(1), rs.getString(3), login, rs.getString(4), 
                        rs.getTimestamp(5).toLocalDateTime().toLocalDate(), rs.getString(6),
                        rs.getBoolean(7));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return user;
    }
    
    @Override
    public Board getBoardByLogin(Login login){
        conexao = ConnectionMySQL.openConnection();
        Board board = null;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_BOARD);
            preparedStatement.setLong(1, login.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                board = new Board(rs.getLong(1), rs.getString(3), 
                        rs.getTimestamp(4).toLocalDateTime().toLocalDate(), 
                        rs.getString(5), login, rs.getString(6), 
                        rs.getString(7));
            }
        }catch(SQLException ex){
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ConnectionMySQL.closeConnection(conexao);
        return board;
    }
    
    @Override
    public Evaluator getEvaluatorByLogin(Login login){
        conexao = ConnectionMySQL.openConnection();
        Evaluator evaluator = null;
        
        try{
            
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_EVALUATOR);
            preparedStatement.setLong(1, login.getId());
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                evaluator = new Evaluator(resultSet.getLong(1), login, resultSet.getString(3), 
                        resultSet.getString(4), resultSet.getTimestamp(5).toLocalDateTime().toLocalDate(),
                resultSet.getString(6), resultSet.getBoolean(7));
            }
            
        }catch(SQLException ex){
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return evaluator;
    }

    @Override
    public Login getLoginByEmail(String email) {
        conexao = ConnectionMySQL.openConnection();
        Login login = null;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from login where email = ?;");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                login = new Login(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return login;
      
    }

  
}