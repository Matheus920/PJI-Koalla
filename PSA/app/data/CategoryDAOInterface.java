package app.data;

import app.model.Category;
import java.util.List;


public interface CategoryDAOInterface {
    
    public Category addCategory(Category category);
    public void updateCategory(Category category);
    public void deleteCategory(Category category);
    public Category getCategoryById(long id);
    public List<Category> getAllCategories();
    public boolean exists(String name);
    public List<String> getAllCategoriesNames();
}
