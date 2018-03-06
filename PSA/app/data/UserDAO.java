package app.data;

import app.model.Event;
import app.model.Login;
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
    private final static String SQL_INSERT = "insert into usuario(nome, id_login, instituicao,"
            + "data_nascimento, imagem) values (?, ?, ?, ?, ?);";
    private final static String SQL_UPDATE = "update usuario set palestrante = ? where id = ?;";
    private final static String SQL_SELECT_SPEAKERS = "select * from usuario where palestrante is true order by nome;";
    private final static String SQL_UPDATE_USER = "update usuario set nome = ?, instituicao = ?, data_nascimento = ?, imagem = ? where id = ?;";
    private final static String SQL_EVENT_USER = "select evento.* from evento inner join artigo on evento.id = artigo.id_evento inner " +
                                "join usuario on artigo.id_usuario = usuario.id where artigo.aprovado = 1 and " +
                                "usuario.id = ?;";
    private final static String SQL_SPEAKERS = "select usuario.* from palestras inner join "
            + "usuario on palestras.id_usuario = usuario.id where id_evento = ?;";

    
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Login login = new LoginDAO().getLoginById(rs.getLong(2));
                
                User temp = new User(rs.getLong(1), rs.getString(3), login, rs.getString(4), 
                        rs.getTimestamp(5).toLocalDateTime().toLocalDate(), rs.getString(6),
                        rs.getBoolean(7));
                
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
    public List<User> getSpeakers(){
        List<User> users = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_SPEAKERS);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Login login = new LoginDAO().getLoginById(rs.getLong(2));
                
                User temp = new User(rs.getLong(1), rs.getString(3), login, rs.getString(4), 
                        rs.getTimestamp(5).toLocalDateTime().toLocalDate(), rs.getString(6),
                        rs.getBoolean(7));
                
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
                Login login = new LoginDAO().getLoginById(rs.getLong(2));
                
                user = new User(rs.getLong(1), rs.getString(3), login, rs.getString(4), 
                        rs.getTimestamp(5).toLocalDateTime().toLocalDate(), rs.getString(6),
                        rs.getBoolean(7));
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
            preparedStatement.setLong(2, user.getLogin().getId());
            preparedStatement.setString(3, user.getInstituicao());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(ldt));
            preparedStatement.setString(5, user.getCaminhoImagem());
            
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
    
    @Override
    public void updateUser(User user){
        conexao = ConnectionMySQL.openConnection();
        
        LocalDateTime ldt = LocalDateTime.of(user.getDataNascimento(), LocalTime.now());
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_UPDATE_USER);
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getInstituicao());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(ldt));
            preparedStatement.setString(4, user.getCaminhoImagem());
            preparedStatement.setLong(5, user.getId());
            preparedStatement.executeUpdate();
            
            
        }
        catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public boolean exists(String email){
        conexao = ConnectionMySQL.openConnection();
        boolean resultado = false;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from usuario where email = ?;");
            preparedStatement.setString(1, email);
            
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
    public List<Event> getAllEventsBySpeaker(User user){
        List<Event> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_EVENT_USER);
            preparedStatement.setLong(1, user.getId());
            
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    @Override
    public List<User> getSpeakersInAnEvent(Event event){
        List<User> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SPEAKERS);
            preparedStatement.setLong(1, event.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                User user = new User(rs.getLong(1), rs.getString(3), new LoginDAO().getLoginById(rs.getLong(2)), rs.getString(4), 
                        rs.getTimestamp(5).toLocalDateTime().toLocalDate(), rs.getString(6),
                        rs.getBoolean(7));
                
                resultados.add(user);
            }
        }catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    @Override
    public void insertSpeakerInAnEvent(Event event, User user){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("insert into palestras "
                    + "(id_usuario, id_evento) values (?, ?);");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, event.getId());
            
            preparedStatement.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        ConnectionMySQL.closeConnection(conexao);
    }
    
    
}
