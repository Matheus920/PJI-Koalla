
package app.data;

import app.model.User;
import java.util.List;


public interface UserDAOInterface {
    
    public List<User> getUsers();
    public User getUserById(long id);
    public User addUser(User user);
    public void turnSpeaker(User user);
    
}
