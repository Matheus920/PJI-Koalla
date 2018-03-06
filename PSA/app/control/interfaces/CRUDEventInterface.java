
package app.control.interfaces;

import app.data.EventDAOInterface;
import app.model.Event;

public interface CRUDEventInterface extends EventDAOInterface{
    public boolean existsVacancies(Event event);
}
