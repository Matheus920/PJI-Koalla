package app.control;

import app.control.interfaces.LoginControllerInterface;
import app.control.interfaces.PrivilegeTypeInterface;
import app.data.LoginDAO;
import app.model.Board;
import app.model.Evaluator;
import app.model.Login;
import app.model.User;


public class LoginController implements LoginControllerInterface{

    @Override
    public boolean exists(String email, String password, PrivilegeTypeInterface test) {
        return new LoginDAO().exists(email, password, test);
    }

    @Override
    public Login getLoginById(long id) {
        return new LoginDAO().getLoginById(id);
    }
    
    @Override
    public void updateLogin(Login login){
        new LoginDAO().updateLogin(login);
    }
    
    @Override
    public Login addLogin(Login login){
        return new LoginDAO().addLogin(login);
    }
    
    @Override
    public boolean emailExists(String email){
        return new LoginDAO().emailExists(email);
    }

    @Override
    public User getUserByLogin(Login login) {
        return new LoginDAO().getUserByLogin(login);
    }

    @Override
    public Evaluator getEvaluatorByLogin(Login login) {
        return new LoginDAO().getEvaluatorByLogin(login);
    }

    @Override
    public Board getBoardByLogin(Login login) {
        return new LoginDAO().getBoardByLogin(login);
    }

    @Override
    public Login getLoginByEmail(String email) {
        return new LoginDAO().getLoginByEmail(email);
    }
}
