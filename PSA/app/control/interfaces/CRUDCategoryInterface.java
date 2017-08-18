package app.control.interfaces;

import java.util.List;

public interface CRUDCategoryInterface {
    public List<String> getAllCategories();
    public String getCategoryById(int id);
    public void deleteCategoryById(int id);
    public void updateCategoryById(int id, String value);
    public void addCategory(String value);
}
