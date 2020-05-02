package controller.interfaces.category;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import model.Category;
import model.CategoryFeature;
import model.Product;

import java.util.List;

public interface ICategoryController {


    int addCategory(int patternId, String newCategoryName, String token) throws exception.NoObjectWithIdException, exception.ObjectAlreadyExistException, NoAccessException;

    void removeACategory(int id, int parentId, String token) throws NoObjectWithIdException, NoAccessException, InvalidTokenException;

    void addAttribute(int id, String attributeName, String attribute, String token) throws NoObjectWithIdException, NoAccessException, InvalidTokenException;

    void changeAttribute(int id, String attributeName, String attribute, String token) throws NoObjectWithIdException, NoAccessException, InvalidTokenException;

    void removeAttribute(int id, String attributeName, String attribute, String token) throws NoObjectWithIdException, NoAccessException, InvalidTokenException;

    void addProduct(int id, int productName, String token) throws exception.NoObjectWithIdException, NoAccessException;

    void removeProduct(int id, int productName, String token) throws NoObjectWithIdException, NoAccessException, InvalidTokenException;


    List<Category> getAllCategories(int id, String token) throws NoObjectWithIdException;

    List<CategoryFeature> getAttribute(int id, String token) throws NoObjectWithIdException;

    Category getCategory(int id, String token) throws NoObjectWithIdException;

    List<Product> getProducts(int id, String token) throws NoObjectWithIdException;


}
