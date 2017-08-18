package app.control;

import java.util.ArrayList;
import java.util.List;
import app.control.interfaces.CRUDListSymposiumsInterface;

public class CRUDListSymposiumsTest implements CRUDListSymposiumsInterface{

    List<String[]> list;
    
    public CRUDListSymposiumsTest() {
        list = new ArrayList<String[]>();
        for(int i = 1; i <= 2001; i++) {
            String[] temp = new String[2];
            temp[0] = "Título " + i;
            temp[1] = "Descrição: " + i;
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
        return (list.size()%10==0) ? list.size()/10: (list.size()/10)+1;
    }
}