package controller.interfaces.product;

import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.Product;

public interface IProductController {

    void createProduct(Product product, String token) throws ObjectAlreadyExistException;

    Product getProductByName(int id, String token) throws NoObjectWithIdException;

    public void editProduct(int id, Product newProduct) throws NoObjectWithIdException;
}
