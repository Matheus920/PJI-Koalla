package app.control;

import app.control.interfaces.CRUDCategoryInterface;
import app.data.CategoryDAO;
import app.model.Category;
import app.model.Event;
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

@Override
public boolean exists(String name){
    return new CategoryDAO().exists(name);
}

@Override
public List<String> getAllCategoriesNames() {
    return new CategoryDAO().getAllCategoriesNames();
}

    @Override
    public void addCategoryInAnEvent(Category category, Event event) {
        new CategoryDAO().addCategoryInAnEvent(category, event);
    }
}
