package app.data;

import app.model.User;
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


public class UserDAO implements UserDAOInterface{
    
    private Connection conexao;
    private final static String SQL_SELECT = "select * from usuario;";
    private final static String SQL_SELECT_WHERE = "select * from usuario where id = ?;";
    private final static String SQL_INSERT = "insert into usuario(nome, email, instituicao,"
            + "senha, data_nascimento, imagem) values (?, ?, ?, ?, ?, ?);";
    private final static String SQL_UPDATE = "update usuario set palestrante = ? where id = ?;";
    

    
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                User temp = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6).toLocalDateTime().toLocalDate(), rs.getString(7),
                        rs.getBoolean(8), rs.getInt(9));
                
                users.add(temp);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return users;
        }
    

    @Override
    public User getUserById(long id) {
        conexao = ConnectionMySQL.openConnection();
        User user = null;
        try{
            
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_WHERE);
            preparedStatement.setLong(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                user = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6).toLocalDateTime().toLocalDate(), rs.getString(7),
                        rs.getBoolean(8), rs.getInt(9));
            }
            else{
                throw new IllegalArgumentException("Não existe usuário para o id informado");
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        ConnectionMySQL.closeConnection(conexao);
        return user;
    }

    @Override
    public User addUser(User user) {
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            
            
            LocalDateTime ldt = LocalDateTime.of(user.getDataNascimento(), LocalTime.now());
   
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getInstituicao());
            preparedStatement.setString(4, user.getSenha());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(ldt));
            preparedStatement.setString(6, user.getCaminhoImagem());
            
            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            
            long id = 0;
            
            if(resultSet.next()){
                id = resultSet.getLong(1);
            }
            
            user.setId(id);
         
        }
        catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return user;
    }
    
    @Override
    public void turnSpeaker(User user) {
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_UPDATE);
            
            preparedStatement.setBoolean(1, true);
            preparedStatement.setLong(2, user.getId());
            
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }

}
