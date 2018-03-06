package app.data;

import app.model.Board;
import app.model.Event;
import java.util.List;


public interface BoardDAOInterface {
    public Board addBoard(Board board);
    public void updateBoard(Board board);
    public void deleteBoard(Board board);
    public Board getBoardById(long id);
    public List<Board> getAllBoard();
    public boolean exists(String id);
    public List<String> getAllBoardNames();
    public List<Event> getAllEventsByBoard(Board board);
}
