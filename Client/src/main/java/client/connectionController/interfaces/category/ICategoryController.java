package client.connectionController.interfaces.category;

import exception.*;
import model.Category;
import model.CategoryFeature;
import model.Product;
import model.enums.FeatureType;

import java.util.List;
import java.util.Map;

public interface ICategoryController {

    void addCategory(int patternId, Category newCategory, String token) throws InvalidIdException, ObjectAlreadyExistException, NoAccessException, InvalidTokenException;

    void removeACategory(int id, int parentId, String token) throws NoAccessException, InvalidTokenException, InvalidIdException, NoObjectIdException;

    void addAttribute(int id, String attributeName, FeatureType featureType, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;

    void removeAttribute(int id, String attributeName, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;

    void addProduct(int id, int productName, String token) throws  NoAccessException, InvalidTokenException, InvalidIdException;

    void removeProduct(int id, int productName, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;

    List<Category> getAllCategories(int id, String token) throws InvalidIdException;

    void getExceptionOfIfCategoryExist(int id, String token) throws InvalidIdException;

    List<CategoryFeature> getAttribute(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;

    Category getCategory(int id, String token) throws InvalidIdException;

    List<Category> getSubCategories(int id, String token) throws InvalidIdException;

    Category getCategoryByName(String name, String token) throws InvalidIdException;

    List<Product> getAllProducts(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, int id, String token) throws InvalidIdException;

    List<Product> getAllProductsInOff(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, int id, String token) throws InvalidIdException;

    List<Product> getProducts(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;


}
