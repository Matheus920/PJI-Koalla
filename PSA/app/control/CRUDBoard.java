package app.control;

import app.control.interfaces.CRUDBoardInterface;
import app.data.BoardDAO;
import app.model.Board;
import app.model.Event;
import java.util.List;


public class CRUDBoard implements CRUDBoardInterface{

    @Override
    public Board addBoard(Board board) {
        return new BoardDAO().addBoard(board);
    }

    @Override
    public void updateBoard(Board board) {
        new BoardDAO().updateBoard(board);
    }

    @Override
    public void deleteBoard(Board board) {
        new BoardDAO().deleteBoard(board);
    }

    @Override
    public Board getBoardById(long id) {
        return new BoardDAO().getBoardById(id);
    }

    @Override
    public List<Board> getAllBoard() {
        return new BoardDAO().getAllBoard();
    }

    @Override
    public boolean exists(String id) {
        return new BoardDAO().exists(id);
    }

    @Override
    public List<String> getAllBoardNames() {
        return new BoardDAO().getAllBoardNames();
    }

    @Override
    public List<Event> getAllEventsByBoard(Board board) {
        return new BoardDAO().getAllEventsByBoard(board);
    }
    
}
