package controller.interfaces.product;

import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.NotSellerException;
import exception.ObjectAlreadyExistException;
import model.Product;
import model.ProductSeller;

public interface IProductController {

    void createProduct(Product product, String token) throws ObjectAlreadyExistException, NotSellerException;

    void addSeller(int id, ProductSeller productSeller, String token) throws NotSellerException, NoAccessException;

    Product getProductById(int id, String token) throws NoObjectWithIdException;

    public void editProduct(int id, Product newProduct, String token) throws NoObjectWithIdException, NotSellerException, NoAccessException;
}
