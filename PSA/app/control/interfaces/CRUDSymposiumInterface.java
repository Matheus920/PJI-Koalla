package app.control.interfaces;

import java.time.LocalDate;
import java.util.List;

public interface CRUDSymposiumInterface {
    public String getOverview();
    public List<String> getSpeakers();
    public String getPlace();
    public String getTitle();
    public LocalDate getDate();
    public int getSpeakersVacancies();
    public long getId();
    public boolean getStatus();
    public int getEnrolledUsers();
}
