package controller.interfaces.category;

import model.Category;
import model.Product;

import java.util.ArrayList;

public interface IShowCategoryController {




    Category[] getAllCategories(ArrayList<String> names, String token);

    Category getCategory(String name, String token);

    Category[] getSubCategories(int id, String token);

    Product[] getProducts(int id, String token);//
}
