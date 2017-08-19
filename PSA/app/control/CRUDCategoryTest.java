package app.control;

import app.control.interfaces.CRUDCategoryInterface;
import java.util.ArrayList;
import java.util.List;

public class CRUDCategoryTest implements CRUDCategoryInterface{
    private List<String> list;
    
    public CRUDCategoryTest() {
        list = new ArrayList<>();
        setList("Artes", "Biologia", "Mecânica", "Informática", "Medicina", "Eletrônica", "Enfermagem", "Sociologia", "Economia", "Política", "Engenharia", "Arquitetura", "Redes", "Comunicação", "Ecologia", "Agricultura",
        "Mobilidade", "Teologia", "Turismo", "Filosofia", "Química");
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

    @Override
    public void updateCategoryById(int id, String value) {
        list.set(id, value);
    }

    @Override
    public void addCategory(String value) {
        list.add(value);
    }
    
}
