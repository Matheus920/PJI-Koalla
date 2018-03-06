
package app.control;

import app.control.interfaces.CRUDUserInterface;
import app.data.UserDAO;
import app.model.Event;
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
    
    @Override
    public List<User> getSpeakers(){
        return new UserDAO().getSpeakers();
    }
    
    @Override
    public void updateUser(User user){
        new UserDAO().updateUser(user);
    }
    
    @Override
    public boolean exists(String email){
        return new UserDAO().exists(email);
    }

    @Override
    public List<Event> getAllEventsBySpeaker(User user) {
        return new UserDAO().getAllEventsBySpeaker(user);
    }

    @Override
    public List<User> getSpeakersInAnEvent(Event event) {
        return new UserDAO().getSpeakersInAnEvent(event);
    }

    @Override
    public void insertSpeakerInAnEvent(Event event, User user) {
        new UserDAO().insertSpeakerInAnEvent(event, user);
    }
}