package app.data;

import app.model.Category;
import app.model.Evaluator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import app.model.Event;
import app.model.User;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements EventDAOInterface{
    
    private Connection conexao;
    private static final String SQL_SELECT_WHERE = "select * from evento where id = ?;";
    private static final String SQL_SELECT = "select * from evento;";
    private static final String SQL_SELECT_OPEN = "select * from evento where `status` is true;";
    private static final String SQL_SELECT_CLOSED = "select * from evento where `status` is false;";
    private static final String SQL_DELETE = "delete from evento where id = ?;";
    private static final String SQL_INSERT = "insert into evento(titulo, resumo, descricao, data_inicio,"
            + "duracao, `local`, capacidade, `status`, quantidade_palestrantes, id_comite) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_CATEGORIES_EVENT = "SELECT categoria.* FROM eventos_categorias INNER JOIN\n" +
    "categoria ON eventos_categorias.id_categoria = categoria.id WHERE eventos_categorias.id_evento = ? order by "
            + "categoria.nome;";
    private static final String SQL_INSERT_USER_IN_AN_EVENT = "insert into eventos_usuarios(id_usuario, id_evento) "
            + "values(?, ?);";
    private static final String SQL_EXISTS_USER = "select * from eventos_usuarios where id_usuario = ? and id_evento = ?;";
    private static final String SQL_DELETE_USER = "delete from eventos_usuarios where id_usuario = ? and id_evento = ?;";
    private static final String SQL_UPDATE = "update evento set titulo = ?, resumo = ?, descricao = ? where id = ?;";
    private static final String SQL_INVITED = "select nome from convidados where id_evento = ?;";
    private static final String SQL_EVENTS_EVALUATOR = "select evento.* from eventos_avaliadores inner join "
            + "evento on eventos_avaliadores.id_evento = evento.id where id_professor = ?;";
    
    @Override
    public List<String> getAllInvitedSpeakersByEvent(Event event){
        List<String> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INVITED);
            preparedStatement.setLong(1, event.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                resultados.add(rs.getString(1));
            }
        }catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    
    public Event getEventById(long id){
        endEvent();
        conexao = ConnectionMySQL.openConnection();
        Event evento = null;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_WHERE);
            preparedStatement.setLong(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                evento = new Event(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                rs.getTimestamp(5).toLocalDateTime(), rs.getInt(6), rs.getString(7), rs.getInt(8),
                rs.getBoolean(9), rs.getInt(10), new BoardDAO().getBoardById(rs.getLong(11)));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return evento;
    }
    
    public List<Event> getAllEvents(){
        endEvent();
        List<Event> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Event evento = new Event(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                rs.getTimestamp(5).toLocalDateTime(), rs.getInt(6), rs.getString(7), rs.getInt(8),
                rs.getBoolean(9), rs.getInt(10), new BoardDAO().getBoardById(rs.getLong(11)));
                
                resultados.add(evento);
            }
        }
         catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    public List<Event> getAllOpenEvents(){
        endEvent();
        List<Event> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_OPEN);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Event evento = new Event(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                rs.getTimestamp(5).toLocalDateTime(), rs.getInt(6), rs.getString(7), rs.getInt(8),
                rs.getBoolean(9), rs.getInt(10), new BoardDAO().getBoardById(rs.getLong(11)));
                
                resultados.add(evento);
            }
        }
         catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    public List<Event> getAllClosedEvents(){
        endEvent();
        List<Event> resultados = new ArrayList<>();
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_SELECT_CLOSED);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Event evento = new Event(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                rs.getTimestamp(5).toLocalDateTime(), rs.getInt(6), rs.getString(7), rs.getInt(8),
                rs.getBoolean(9), rs.getInt(10), new BoardDAO().getBoardById(rs.getLong(11)));
                
                resultados.add(evento);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    public void deleteEvent(Event evento){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1, evento.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    } 
    
    public Event addEvent(Event evento){
        conexao = ConnectionMySQL.openConnection();
        long id = 0;
        
       
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
           
            preparedStatement.setString(1, evento.getTitulo());
            preparedStatement.setString(2, evento.getResumo());
            preparedStatement.setString(3, evento.getDescricao());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(evento.getDataInicio()));
            preparedStatement.setInt(5, evento.getDuracao());
            preparedStatement.setString(6, evento.getLocal());
            preparedStatement.setInt(7, evento.getCapacidade());
            preparedStatement.setBoolean(8, evento.isStatus());
            preparedStatement.setInt(9, evento.getQuantidadePalestrantes());
            preparedStatement.setLong(10, evento.getComite().getId());
            
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            
            if(rs.next()){
                id = rs.getLong(1);
            }
            
            evento.setId(id);
        }
        catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return evento;
    }
    
    @Override
    public List<Category> getCategoriesByEvent(Event event){
        List<Category> resultados = new ArrayList<>();
        
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_CATEGORIES_EVENT);
            preparedStatement.setLong(1, event.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Category category = new Category(rs.getLong(1), rs.getString(2));
                
                resultados.add(category);
            }
        }catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
        
    }
    
    @Override
    public List<Event> searchByFields(String title, long categoriaId, LocalDate data, boolean status){
        String statement = "";
        List<Event> resultados = new ArrayList<>();
        boolean opened = true;
        
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        if(!status){
            opened = false;
        }
        if(title.equals("") && categoriaId == -1 && data.equals(LocalDate.of(1976, 5, 3))){
            statement = "select * from evento where `status` = ?;";
            try{
                conexao = ConnectionMySQL.openConnection();
                preparedStatement = conexao.prepareStatement(statement);
                preparedStatement.setBoolean(1, opened);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                            resultSet.getString(3), resultSet.getString(4), 
                    resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                            resultSet.getString(7), resultSet.getInt(8),
                    resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));

                    resultados.add(evento);
                }

                ConnectionMySQL.closeConnection(conexao);
            }
            catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return resultados;

        } else if(title.equals("") && categoriaId == -1){
                statement = "select * from evento where cast(data_inicio as date) = ?"
                        + " and `status` = ?;";
                try{
                    conexao = ConnectionMySQL.openConnection();
                    preparedStatement = conexao.prepareStatement(statement);
                    preparedStatement.setDate(1, Date.valueOf(data));
                    preparedStatement.setBoolean(2, opened);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()){
                        Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                                resultSet.getString(3), resultSet.getString(4), 
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));

                        resultados.add(evento);
                    }

                    ConnectionMySQL.closeConnection(conexao);
                }
                catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                return resultados;
                } else if(categoriaId == -1 && data.equals(LocalDate.of(1976, 5, 3))){
                statement = "select * from evento where titulo like '%" + title + "%' and "
                        + "`status` = ?;";
                try{
                    conexao = ConnectionMySQL.openConnection();
                    preparedStatement = conexao.prepareStatement(statement);
                    preparedStatement.setBoolean(1, opened);
                    resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                                resultSet.getString(3), resultSet.getString(4), 
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));

                        resultados.add(evento);
                    }

                    ConnectionMySQL.closeConnection(conexao);
                }
                catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                return resultados;
                } else if(title.equals("") && data.equals(LocalDate.of(1976, 5, 3))){
                statement =  "select * from evento inner join eventos_categorias on "
                        + "eventos_categorias.id_evento = evento.id and "
                        + "eventos_categorias.id_categoria = ? and `status` = ?;";


                try{
                    conexao = ConnectionMySQL.openConnection();
                    preparedStatement = conexao.prepareStatement(statement);
                    preparedStatement.setLong(1, categoriaId);
                    preparedStatement.setBoolean(2, opened);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()){
                        Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                                resultSet.getString(3), resultSet.getString(4), 
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));

                        resultados.add(evento);
                    }

                    ConnectionMySQL.closeConnection(conexao);
                }
                catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                return resultados;
                } else if(title.equals("")){
                statement = "select * from evento inner join eventos_categorias on "
                        + "eventos_categorias.id_evento = evento.id where cast(data_inicio as date) = ? and "
                        + "eventos_categorias.id_categoria = ? and `status` = ?;";

                try{
                    conexao = ConnectionMySQL.openConnection();
                    preparedStatement = conexao.prepareStatement(statement);
                    preparedStatement.setDate(1, Date.valueOf(data));
                    preparedStatement.setLong(2, categoriaId);
                    preparedStatement.setBoolean(3, opened);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()){
                        Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                                resultSet.getString(3), resultSet.getString(4), 
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));

                        resultados.add(evento);
                    }

                    ConnectionMySQL.closeConnection(conexao);
                }
                catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                return resultados;
            } else if(categoriaId == -1){
                statement = "select * from evento where titulo like '%" + title + "%' and "
                        + "cast(data_inicio as date) = ? and `status` = ?;";

                try{
                    conexao = ConnectionMySQL.openConnection();
                    preparedStatement = conexao.prepareStatement(statement);
                    preparedStatement.setDate(1, Date.valueOf(data));
                    preparedStatement.setBoolean(2, opened);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()){
                        Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                                resultSet.getString(3), resultSet.getString(4), 
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));

                        resultados.add(evento);
                    }

                    ConnectionMySQL.closeConnection(conexao);
                }
                catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                return resultados;
            } else if(data.equals(LocalDate.of(1976, 5, 3))){
                statement =  "select * from evento inner join eventos_categorias on "
                        + "eventos_categorias.id_evento = evento.id and "
                        + "eventos_categorias.id_categoria = ? and titulo like '%" + title + "%' and "
                        + "`status` = ?;";


                try{
                    conexao = ConnectionMySQL.openConnection();
                    preparedStatement = conexao.prepareStatement(statement);
                    preparedStatement.setLong(1, categoriaId);
                    preparedStatement.setBoolean(2, opened);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()){
                        Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                                resultSet.getString(3), resultSet.getString(4), 
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));

                        resultados.add(evento);
                    }

                    ConnectionMySQL.closeConnection(conexao);
                }
                catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                return resultados; 
            }
                else{
                statement = "select * from evento inner join eventos_categorias on "
                        + "eventos_categorias.id_evento = evento.id and "
                        + "eventos_categorias.id_categoria = ? and titulo like '%" + title +"%' and "
                        + "cast(data_inicio as date) = ? and `status` = ?";
                try{
                    conexao = ConnectionMySQL.openConnection();
                    preparedStatement = conexao.prepareStatement(statement);

                    preparedStatement.setLong(1, categoriaId);
                    preparedStatement.setDate(2, Date.valueOf(data));
                    preparedStatement.setBoolean(3, opened);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()){
                        Event evento = new Event(resultSet.getLong(1), resultSet.getString(2), 
                                resultSet.getString(3), resultSet.getString(4), 
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getInt(8),
                        resultSet.getBoolean(9), resultSet.getInt(10), new BoardDAO().getBoardById(resultSet.getLong(11)));

                        resultados.add(evento);
                    }

                    ConnectionMySQL.closeConnection(conexao);
                }
                catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                return resultados;
            }
     
    }
    
    @Override
    public void addAnUserInAEvent(Event event, User user){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_INSERT_USER_IN_AN_EVENT);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, event.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public boolean existsUserInThisEvent(User user, Event event){
        conexao = ConnectionMySQL.openConnection();
        boolean resultado = false;
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_EXISTS_USER);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, event.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                resultado = true;
            }
        }
        catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return resultado;
    }
    
    @Override
    public void deleteUserFromAnEvent(User user, Event event){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, event.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
                    Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public int getEnrolledUsers(Event event){
        int resultado = 0;
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select "
                    + "count(id) from eventos_usuarios where id_evento = ?;");
            preparedStatement.setLong(1, event.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                resultado = rs.getInt(1);
            }
        }catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultado;
    }
    
    private void endEvent(){
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("update evento set status = 0 where "
                    + "addtime(data_inicio, sec_to_time(duracao*60)) < " 
                    + "now() and id > 0;");
            
            preparedStatement.executeUpdate();
                
        }catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public void addATemporarySpeaker(String name, Event event){
        conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("insert into convidados(nome, id_evento)  "
                    + "values(?, ?);");
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, event.getId());
            
            preparedStatement.executeUpdate();
            
        }catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public void updateEvent(Event event){
        conexao = ConnectionMySQL.openConnection();
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, event.getTitulo());
            preparedStatement.setString(2, event.getResumo());
            preparedStatement.setString(3, event.getDescricao());
            preparedStatement.setLong(4, event.getId());
            
            preparedStatement.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ConnectionMySQL.closeConnection(conexao);
    }
    
    @Override
    public List<Event> getAllEventsByEvaluator(Evaluator evaluator){
        conexao = ConnectionMySQL.openConnection();
        List<Event> resultados = new ArrayList<>();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement(SQL_EVENTS_EVALUATOR);
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
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConnectionMySQL.closeConnection(conexao);
        return resultados;
    }
    
    @Override
    public int getQuantityofEvaluatorsByEvent(Event event){
        conexao = ConnectionMySQL.openConnection();
        int resultado = 0;
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select count(*) from eventos_avaliadores where id_evento = ?;");
            preparedStatement.setLong(1, event.getId());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            
            if(rs.next()){
                resultado = rs.getInt(1);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionMySQL.closeConnection(conexao);
        return resultado;
    }
}
