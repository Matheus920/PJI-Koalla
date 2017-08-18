package app.control.interfaces;

import java.util.List;

public interface CRUDListSymposiumsInterface {
    public List<String[]> listSymposium();
    public List<String[]> listSymposium(int page);
    public int numberPages();
}
