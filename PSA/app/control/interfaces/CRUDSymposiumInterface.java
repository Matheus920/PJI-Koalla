package app.control.interfaces;

import java.util.List;

public interface CRUDSymposiumInterface {
    public String getOverview();
    public List<String> getSpeakers();
    public String getPlace();
    public String getTitle();
    public int getSpeakersVacancies();
}
