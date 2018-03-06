package app.data;

import app.model.Board;
import app.model.Event;
import app.model.Login;
import com.mysql.jdbc.Statement;
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


public class BoardDAO implements BoardDAOInterface{

    private Connection conexao;
    private static final String SQL_INSERT = "insert into comite(prontuario, data_nascimento, funcao, id_login, imagem, nome) values (?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE = "update comite set prontuario = ?, data_nascimento = ?, funcao = ?, imagem = ?, nome = ? where id = ?;"; 
    private static final String SQL_DELETE = "delete from comite where id = ?;";
    private static final String SQL_SELECT_WHERE = "select * from comite where id = ?;";
    private static final String SQL_SELECT = "select * from comite order by nome;";
    private static final String SQL_SELECT_WHERE_ID = "select * from comite where prontuario = ?;";
    
    @Override
    public Board addBoard(Board board) {
        conexao = ConnectionMySQL.openConnection();
        LocalDateTime ldt = LocalDateTime.of(board.getDataNascimento(), LocalTime.now());
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, board.getProntuario());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(ldt));
            preparedStatement.setString(3, board.getFuncao());
            preparedStatement.setLong(4, board.getLogin().getId());
            preparedStatement.setString(5, board.getCaminhoDaImagem());
            preparedStatement.setString(6, board.getNome());
            
            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long id = 0;
            
            if(resultSet.next()){
                id = resultSet.getLong(1);
            }
            
            board.setId(id);
        }
        catch(SQLException ex){
            Logger.getLogger(BoardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return board;
        
    }

    @Override
    public void updateBoard(Board board) {
        conexao = ConnectionMySQL.openConnection();
        LocalDateTime ldt = LocalDateTime.of(board.getDataNascimento(), LocalTime.now());
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, board.getProntuario());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(ldt));
            preparedStatement.setString(3, board.getFuncao());
            preparedStatement.setString(4, board.getCaminhoDaImagem());
            preparedStatement.setString(5, board.getNome());
            preparedStatement.setLong(6, board.getId());
            
            preparedStatement.executeUpdate();
            
            
        }
        catch(SQLException ex){
            Logger.getLogger(BoardDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        ConnectionMySQL.closeConnection(conexao);

        
    }

    @Override
    public void deleteBoard(Board board) {
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, board.getId());
            preparedStatement.executeUpdate();
            
        }
         catch(SQLException ex){
            Logger.getLogger(BoardDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        ConnectionMySQL.closeConnection(conexao);
    }

    @Override
    public Board getBoardById(long id) {
        conexao = ConnectionMySQL.openConnection();
        
        Board board = null;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_WHERE);
            preparedStatement.setLong(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                Login login = new LoginDAO().getLoginById(resultSet.getLong(2));
                
                board = new Board(resultSet.getLong(1), resultSet.getString(3), resultSet.getTimestamp(4).toLocalDateTime().toLocalDate(), 
                        resultSet.getString(5), login, resultSet.getString(6), resultSet.getString(7));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(BoardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return board;
    }

    @Override
    public List<Board> getAllBoard() {
        conexao = ConnectionMySQL.openConnection();
        
        List<Board> lista = new ArrayList<>();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Login login = new LoginDAO().getLoginById(resultSet.getLong(2));
                
                Board board = new Board(resultSet.getLong(1), resultSet.getString(3), resultSet.getTimestamp(4).toLocalDateTime().toLocalDate(), 
                        resultSet.getString(5), login, resultSet.getString(6), resultSet.getString(7));
                
                lista.add(board);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(BoardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        ConnectionMySQL.closeConnection(conexao);
        return lista;
    }

    @Override
    public boolean exists(String id) {
        conexao = ConnectionMySQL.openConnection();
        boolean resultado = false;
        try{
           PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_WHERE_ID);
           preparedStatement.setString(1, id);
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
    public List<String> getAllBoardNames() {
        List<String> resultados = new ArrayList<>();
        
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("SELECT nome FROM comite;");
            
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
    public List<Event> getAllEventsByBoard(Board board){
        List<Event> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from evento where id_comite = ?;");
            preparedStatement.setLong(1, board.getId());
            
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
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
}
