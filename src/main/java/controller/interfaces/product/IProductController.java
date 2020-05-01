package controller.interfaces.product;

import exception.NoObjectWithIdException;
import model.Product;
import model.ProductSeller;

public interface IProductController {

    int createProduct(String name, String token);

    Product getProductByName(String name, String token);
    Product getProductById(int id, String token) throws NoObjectWithIdException;

    void changeProductName(int id, String name, String token);

    void changeProductBrand(int id, String brand, String token);

    void changeProductCategory(int id, int categoryId, String token);

    void changeAmountInStock(int id, int amount, String token);

    void changeDescription(String description, String token);

}
