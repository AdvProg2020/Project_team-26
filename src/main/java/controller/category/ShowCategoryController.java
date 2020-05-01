package controller.category;

import controller.Exceptions;
import controller.interfaces.category.IShowCategoryController;
import model.Category;
import model.Product;

import java.util.ArrayList;

public class ShowCategoryController implements IShowCategoryController {

    @Override
    public CategoryForView[] getAllCategories(ArrayList<String> names, String token) {
        return new CategoryForView[0];
    }

    @Override
    public CategoryForView getCategory(String name, String token) {
        return null;
    }

    public Category[] getSubCategories(int id, String token) {
        return null;
    }

    public Product[] getProducts(int id, String token) {
        return null;
    }
}
