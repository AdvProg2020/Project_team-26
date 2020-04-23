package controller.interfaces.category;

import model.Category;
import model.Product;

public interface ShowCategoryController {

    Category getCategory(int id, String token);

    Category[] getSubCategories(int id, String token);

    Product[] getProducts(int id, String token);
}
