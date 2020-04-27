package controller.interfaces.product;

import model.Product;

public interface ProductController {

    int createProduct(String name, String token);

    Product getProductByName(int id, String token);

    void changeProductName(int id, String name, String token);

    void changeProductBrand(int id, String brand, String token);

    void changeProductCategory(int id, int categoryId, String token);

    void changeAmountInStock(int id, int amount, String token);

    void changeDescription(String description, String token);
}
