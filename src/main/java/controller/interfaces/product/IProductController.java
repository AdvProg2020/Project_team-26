package controller.interfaces.product;

import exception.*;
import model.Product;
import model.ProductSeller;

import java.util.List;
import java.util.Map;

public interface IProductController {
    void createProduct(Product product, String token) throws ObjectAlreadyExistException, NotSellerException, InvalidTokenException;

    Product getProductByName(String name, String token) throws NoObjectIdException;

    void addSeller(int id, ProductSeller productSeller, String token) throws NotSellerException, NoAccessException, InvalidTokenException;

    Product getProductById(int id, String token) throws InvalidIdException;

    void removeProduct(int id, String token) throws InvalidIdException, InvalidTokenException, NoAccessException, NotLoggedINException;

    List<Product> getAllProductWithFilter(Map<String, String> filter, String fieldName, boolean isAscending, String token);

    List<Product> getAllProductWithFilter(Map<String, String> filter, String fieldName, boolean isAscending,int startIndex,int endIndex, String token);

    List<Product> getAllProductWithFilterForSellerId(Map<String, String> filter, String fieldName, boolean isAscending, String token) throws NotLoggedINException, InvalidTokenException, NoAccessException;

    List<Product> getAllProductWithFilterForSellerId(Map<String, String> filter, String fieldName, boolean isAscending, int startIndex, int endIndex, String token) throws NotLoggedINException, InvalidTokenException, NoAccessException;

    ProductSeller getProductSellerByIdAndSellerId(int productId, String token) throws InvalidIdException, InvalidTokenException, NotLoggedINException, NoAccessException, NoObjectIdException;

    public void editProductSeller(int id, ProductSeller newProductSeller,String token) throws InvalidIdException, InvalidTokenException, NotSellerException, NoAccessException;

    void editProduct(int id, Product newProduct, String token) throws InvalidIdException, NotSellerException, NoAccessException, InvalidTokenException;
}
