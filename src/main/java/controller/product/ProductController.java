package controller.product;

import controller.interfaces.product.IProductController;
import model.Product;

public class ProductController implements IProductController {

    public int createProduct(String name, String token) {
        return 0;
    }

    public Product getProductByName(int id, String token) {
        return null;
    }

    public void changeProductName(int id, String name, String token) {

    }

    public void changeProductBrand(int id, String brand, String token) {

    }

    public void changeProductCategory(int id, int categoryId, String token) {

    }

    public void changeAmountInStock(int id, int amount, String token) {

    }

    public void changeDescription(String description, String token) {

    }
}
