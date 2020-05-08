package controller.product;

import controller.interfaces.product.IShowProductController;
import exception.NoObjectIdException;
import model.Product;

import java.util.ArrayList;

public class ShowProductController implements IShowProductController {

    public ArrayList<Product> getProducts(String token) {
        return null;
    }

    @Override
    public Product getAProduct(int id, String token) throws NoObjectIdException {
        return null;
    }

}
