package app.control;

import java.util.ArrayList;
import java.util.List;
import app.control.interfaces.CRUDListSymposiumsInterface;
import app.model.Category;
import app.model.Event;

public class CRUDListSymposiumsTest implements CRUDListSymposiumsInterface{

    List<String[]> list;
    List<Event> eventDataList;
    CRUDEvent crudEvent = new CRUDEvent();
    
    
    public CRUDListSymposiumsTest() {
        String resultadoCategorias = "";
       
        eventDataList = crudEvent.getAllOpenEvents();
        list = new ArrayList<String[]>();
        for(int i = 1; i <= eventDataList.size(); i++) {
            List<Category> foundCategories = crudEvent.getCategoriesByEvent(eventDataList.get(i-1));
            
            for(Category a : foundCategories){
                resultadoCategorias += a.getNome() + ", ";
            }
            
            String[] temp = new String[3];
            temp[0] = eventDataList.get(i-1).getTitulo();
            temp[1] = "Descrição: " + eventDataList.get(i-1).getDescricao();
            if(resultadoCategorias.length() > 0){
                temp[2] = resultadoCategorias.substring(0, resultadoCategorias.length() - 2);
            }
            else{
                temp[2] = resultadoCategorias;
            }
            resultadoCategorias = "";
            list.add(temp);
        }
    }
    
    
    
    @Override
    public List<String[]> listSymposium() {
        return list;
    }
    
    @Override
    public List<String[]> listSymposium(int page) {
        ArrayList<String[]> temp = new ArrayList<String[]>();
        if(page < 0 || page > numberPages())
            throw new IllegalArgumentException("Número da página inválidos");
        
        for(int i = (page-1)*10; i < page*10 && i < list.size(); i++) {
            temp.add(list.get(i));
        }
        return temp;
    }
    
    @Override
    public int numberPages() {
        return (list.size()%10==0) ? list.size()/10 : (list.size()/10)+1;
    }
    
    @Override
    public List<Event> getEventDataList(){
        return eventDataList;
    }
    
    @Override
    public void setEventDataList(List<Event> eventDataList){
        this.eventDataList = eventDataList;
        String resultadoCategorias = "";
        list = new ArrayList<String[]>();
        for(int i = 1; i <= eventDataList.size(); i++) {
            List<Category> foundCategories = crudEvent.getCategoriesByEvent(eventDataList.get(i-1));
            
            for(Category a : foundCategories){
                resultadoCategorias += a.getNome() + ", ";
            }
            
            String[] temp = new String[3];
            temp[0] = eventDataList.get(i-1).getTitulo();
            temp[1] = "Descrição: " + eventDataList.get(i-1).getDescricao();
            if(resultadoCategorias.length() > 0){
                temp[2] = resultadoCategorias.substring(0, resultadoCategorias.length() - 2);
            }
            else{
                temp[2] = resultadoCategorias;
            }
            resultadoCategorias = "";
            list.add(temp);
        }
    }
}