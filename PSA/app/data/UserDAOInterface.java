
package app.data;

import app.model.Event;
import app.model.User;
import java.util.List;


public interface UserDAOInterface {
    
    public List<User> getUsers();
    public User getUserById(long id);
    public User addUser(User user);
    public void turnSpeaker(User user);
    public List<User> getSpeakers();
    public void updateUser(User user);
    public boolean exists(String email);
    public List<Event> getAllEventsBySpeaker(User user);
    public List<User> getSpeakersInAnEvent(Event event);
    public void insertSpeakerInAnEvent(Event event, User user);
}
