package controller.interfaces.product;

import model.Product;

public interface IShowProductController {

    Product[] getProducts(int startIndex, int endIndex, String token);

    Product getAProduct(int id, String token);
}
