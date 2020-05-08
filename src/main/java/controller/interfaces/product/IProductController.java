package controller.interfaces.product;

import exception.*;
import model.Product;
import model.ProductSeller;

public interface IProductController {
    void createProduct(Product product, String token) throws ObjectAlreadyExistException, NotSellerException, InvalidTokenException;

    void addSeller(int id, ProductSeller productSeller, String token) throws NotSellerException, NoAccessException, InvalidTokenException;

    Product getProductById(int id, String token) throws InvalidIdException;

    void editProduct(int id, Product newProduct, String token) throws InvalidIdException, NotSellerException, NoAccessException, InvalidTokenException;
}
