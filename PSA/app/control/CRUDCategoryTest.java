package app.control;

import app.control.interfaces.CRUDCategoryInterface;
import java.util.ArrayList;
import java.util.List;

public class CRUDCategoryTest implements CRUDCategoryInterface{
    private List<String> list;
    
    public CRUDCategoryTest() {
        list = new ArrayList<>();
        setList("Thomas", "Turbando", "Oscar", "Alho", "Isadora", "Pinto", "Jacinto", "Leitte", "Aquino", "Rego", "Paula", "Tejando", "Ana", "Konda", "Cuca", "Beludo",
        "Diva", "Aginaberta", "Tati", "Komenno", "Rafaela");
    }
    
    private void setList(String... args)
    {
        for(String a : args) {
            list.add(a);
        }
    }
    
    @Override
    public List<String> getAllCategories() {
        return list;
    }

    @Override
    public String getCategoryById(int id) {
        return list.get(id);
    }
    
    @Override
    public void deleteCategoryById(int id) {
        list.remove(id);
    }
    
}
