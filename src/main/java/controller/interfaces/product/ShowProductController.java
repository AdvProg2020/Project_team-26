package controller.interfaces.product;

import model.Product;

public interface ShowProductController {

    Product[] getProducts(int startIndex, int endIndex, String token);

    Product getAProduct(int id, String token);
}
