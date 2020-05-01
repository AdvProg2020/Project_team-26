package controller.interfaces.category;

import controller.category.CategoryForView;
import model.Product;

import java.util.ArrayList;

public interface IShowCategoryController {


    CategoryForView[] getAllCategories(ArrayList<String> names, String token);

    CategoryForView getCategory(String name, String token);

    CategoryForView[] getSubCategories(int id, String token);

    Product[] getProducts(int id, String token);//
}
