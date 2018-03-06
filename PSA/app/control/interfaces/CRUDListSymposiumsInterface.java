package app.control.interfaces;

import app.model.Event;
import java.util.List;

public interface CRUDListSymposiumsInterface {
    public List<String[]> listSymposium();
    public List<String[]> listSymposium(int page);
    public int numberPages();
    public List<Event> getEventDataList();
    public void setEventDataList(List<Event> eventDataList);
}
