package controller.interfaces.product;

import controller.Exceptions;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public interface IShowProductController {

    ArrayList<Product> getProducts(String token);

    Product getAProduct(String id, String token) throws Exceptions.TheParameterDoesNOtExist;

   /* String getName(Product product, String token);

    String getBrand(Product product, String token);

    String getDescription(Product product, String token);

    String getCategoryAndSub(Product product, String token);

    String getAverageRate(Product product, String token);

    List<String> getSellers(Product product, String token);

    List<String> getComments(Product product, String token);

    String getAttribute(Product product, String token);*/
}
