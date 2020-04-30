package controller.interfaces.category;

import model.Category;
import model.Product;

public interface IShowCategoryController {

    controller.category.Category getCategory(String name, String token);

    Category[] getSubCategories(int id, String token);

    Product[] getProducts(int id, String token);
}
