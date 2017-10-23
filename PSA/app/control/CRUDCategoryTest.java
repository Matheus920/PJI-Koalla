package app.control;

import app.control.interfaces.CRUDCategoryInterface;
import app.data.CategoryDAO;
import app.model.Category;
import java.util.List;

public class CRUDCategoryTest implements CRUDCategoryInterface{

    @Override
    public Category addCategory(Category category) {
       return new CategoryDAO().addCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        new CategoryDAO().updateCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        new CategoryDAO().deleteCategory(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return new CategoryDAO().getCategoryById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return new CategoryDAO().getAllCategories();
    }

    
}
