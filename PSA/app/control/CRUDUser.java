
package app.control;

import app.control.interfaces.CRUDUserInterface;
import app.data.UserDAO;
import app.model.User;
import java.util.List;


public class CRUDUser implements CRUDUserInterface{

    
    @Override
    public List<User> getUsers() {
        return new UserDAO().getUsers();
    }

    @Override
    public User getUserById(long id) {
        return new UserDAO().getUserById(id);
    }

    @Override
    public User addUser(User user) {
        return new UserDAO().addUser(user);
    }
    
    @Override
    public void turnSpeaker(User user){
        new UserDAO().turnSpeaker(user);
    }
}