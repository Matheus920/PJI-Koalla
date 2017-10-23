package app.control.interfaces;

import app.data.UserDAOInterface;
import app.model.User;


public interface CRUDUserInterface extends UserDAOInterface {

    public void turnSpeaker(User user);
}
