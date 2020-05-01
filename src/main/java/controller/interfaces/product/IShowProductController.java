package controller.interfaces.product;

import controller.Exceptions;
import model.Product;

import java.util.ArrayList;

public interface IShowProductController {

    ArrayList<Product> getProducts(String token);

    Product getAProduct(String id, String token) throws Exceptions.TheParameterDoesNOtExist;
}
