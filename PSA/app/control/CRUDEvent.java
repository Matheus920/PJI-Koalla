
package app.control;

import app.control.interfaces.CRUDEventInterface;
import app.data.EventDAO;
import app.data.UserDAO;
import app.model.Category;
import app.model.Evaluator;
import app.model.Event;
import app.model.User;
import java.time.LocalDate;
import java.util.List;


public class CRUDEvent implements CRUDEventInterface{

    @Override
    public Event getEventById(long id) {
        return new EventDAO().getEventById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return new EventDAO().getAllEvents();
    }

    @Override
    public List<Event> getAllOpenEvents() {
        return new EventDAO().getAllOpenEvents();
    }

    @Override
    public List<Event> getAllClosedEvents() {
        return new EventDAO().getAllClosedEvents();
    }

    @Override
    public void deleteEvent(Event evento) {
        new EventDAO().deleteEvent(evento);
    }

    @Override
    public Event addEvent(Event evento) {
        return new EventDAO().addEvent(evento);
    }
    
    @Override
    public List<Category> getCategoriesByEvent(Event event){
        return new EventDAO().getCategoriesByEvent(event);
    }
    
    @Override
    public List<Event> searchByFields(String title, long categoriaId, LocalDate data, boolean status){
        return new EventDAO().searchByFields(title, categoriaId, data, status);
    }

    @Override
    public void addAnUserInAEvent(Event event, User user) {
        new EventDAO().addAnUserInAEvent(event, user);
    }

    @Override
    public boolean existsUserInThisEvent(User user, Event event) {
        return new EventDAO().existsUserInThisEvent(user, event);
    }

    @Override
    public void deleteUserFromAnEvent(User user, Event event) {
        new EventDAO().deleteUserFromAnEvent(user, event);
    }
    
    @Override
    public int getEnrolledUsers(Event event){
        return new EventDAO().getEnrolledUsers(event);
    }

    @Override
    public void addATemporarySpeaker(String name, Event event) {
        new EventDAO().addATemporarySpeaker(name, event);
    }

    @Override
    public void updateEvent(Event event) {
        new EventDAO().updateEvent(event);
    }

    @Override
    public List<String> getAllInvitedSpeakersByEvent(Event event) {
       return new EventDAO().getAllInvitedSpeakersByEvent(event);
    }
    
    @Override
    public boolean existsVacancies(Event event){
        int palestrantes = new UserDAO().getSpeakersInAnEvent(event).size();
        int convidados = new EventDAO().getAllInvitedSpeakersByEvent(event).size();
        if((palestrantes+convidados) < event.getQuantidadePalestrantes()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Event> getAllEventsByEvaluator(Evaluator evaluator) {
        return new EventDAO().getAllEventsByEvaluator(evaluator);
    }

    @Override
    public int getQuantityofEvaluatorsByEvent(Event event) {
        return new EventDAO().getQuantityofEvaluatorsByEvent(event);
    }
}
