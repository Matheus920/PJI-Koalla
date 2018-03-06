package app.data;

import app.control.interfaces.PrivilegeTypeInterface;
import app.model.Board;
import app.model.Evaluator;
import app.model.Login;
import app.model.User;


public interface LoginDAOInterface {
    public boolean exists(String email, String password, PrivilegeTypeInterface test);
    public boolean emailExists(String email);
    public Login getLoginById(long id);
    public void updateLogin(Login login);
    public Login addLogin(Login login);
    public User getUserByLogin(Login login);
    public Evaluator getEvaluatorByLogin(Login login);
    public Board getBoardByLogin(Login login);
    public Login getLoginByEmail(String email);
}
