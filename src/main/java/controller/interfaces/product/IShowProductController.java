package controller.interfaces.product;

import exception.NoObjectIdException;
import model.Product;

import java.util.ArrayList;

public interface IShowProductController {

    ArrayList<Product> getProducts(String token);

    Product getAProduct(String id, String token) throws NoObjectIdException;

   /* String getName(Product product, String token);

    String getBrand(Product product, String token);

    String getDescription(Product product, String token);

    String getCategoryAndSub(Product product, String token);

    String getAverageRate(Product product, String token);

    List<String> getSellers(Product product, String token);

    List<String> getComments(Product product, String token);

    String getAttribute(Product product, String token);*/
}
