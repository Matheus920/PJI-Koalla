package app.control;

import app.control.interfaces.CRUDSymposiumInterface;
import app.model.Event;
import app.model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CRUDSymposiumTest implements CRUDSymposiumInterface{
    private List<String> speakers;
    private String overview;
    private String place;
    private String title;
    private LocalDate date;
    private int vacancies;
    private int enrolledUsers;
    private boolean status;
    private long id;
    
    public CRUDSymposiumTest(Event event) {
        vacancies = event.getQuantidadePalestrantes();
        speakers = new ArrayList<>();
        status = event.isStatus();
        title = event.getTitulo();
        overview = event.getResumo();
        date = event.getDataInicio().toLocalDate();
        id = event.getId();
        enrolledUsers = new CRUDEvent().getEnrolledUsers(event);
        
        place = event.getLocal() + "\n"
                + "Capacidade máxima: " + event.getCapacidade()
                + "\nHora inicio: " +  event.getDataInicio().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n"
                + "Hora fim: " + getFinalTime(event.getDataInicio(), event.getDuracao()).format(DateTimeFormatter.ofPattern("HH:mm"));
        
        ObservableList<String> invitedSpeakers = FXCollections.observableArrayList(new CRUDEvent().getAllInvitedSpeakersByEvent(event));
        ObservableList<User> speakerData = FXCollections.observableArrayList(new CRUDUser().getSpeakersInAnEvent(event));
        
        for(String a : invitedSpeakers){
            speakers.add(a + " (Convidado)");
        }
        
        for(User a : speakerData){
            speakers.add(a.getNome());
        }
        
    }
    
    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public List<String> getSpeakers() {
        return speakers;
    }

    @Override
    public String getPlace() {
        return place;
    }

    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public LocalDate getDate(){
        return date;
    }

    @Override
    public int getSpeakersVacancies() {
        return vacancies;
    }
    
    @Override
    public long getId(){
        return id;
    }
    
    private LocalDateTime getFinalTime(LocalDateTime initialTime, int totalMinutes){
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        
        LocalDateTime result = LocalDateTime.of(initialTime.toLocalDate(), 
                initialTime.toLocalTime().plusHours(hours).plusMinutes(minutes));
        
        return result;
    }
    
    @Override
    public boolean getStatus(){
        return status;
    }
    
    @Override
    public int getEnrolledUsers(){
        return enrolledUsers;
    }
}
