package app.data;

import app.model.Category;
import app.model.Evaluator;
import app.model.Event;
import app.model.User;
import java.time.LocalDate;
import java.util.List;


public interface EventDAOInterface {
    
    public Event getEventById(long id);
    public List<Event> getAllEvents();
    public List<Event> getAllOpenEvents();
    public List<Event> getAllClosedEvents();
    public void deleteEvent(Event evento);
    public Event addEvent(Event evento);
    public List<Category> getCategoriesByEvent(Event event);
    public List<Event> searchByFields(String title, long categoriaId, LocalDate data, boolean status);
    public void addAnUserInAEvent(Event event, User user);
    public boolean existsUserInThisEvent(User user, Event event);
    public void deleteUserFromAnEvent(User user, Event event);
    public int getEnrolledUsers(Event event);
    public void addATemporarySpeaker(String name, Event event);
    public void updateEvent(Event event);
    public List<String> getAllInvitedSpeakersByEvent(Event event);
    public List<Event> getAllEventsByEvaluator(Evaluator evaluator);
    public int getQuantityofEvaluatorsByEvent(Event event);
}
