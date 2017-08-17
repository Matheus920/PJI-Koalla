package app.control;

import java.util.List;

public interface CRUDCategoryInterface {
    public List<String> getAllCategories();
    public String getCategoryById(int id);
    public void deleteCategoryById(int id);
}
