package controller.interfaces.category;

import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.InvalidIdException;
import model.Category;
import model.CategoryFeature;
import model.Product;

import java.util.List;
import java.util.Map;

public interface ICategoryController {


    int addCategory(int patternId, String newCategoryName, String token) throws exception.InvalidIdException, exception.ObjectAlreadyExistException, NoAccessException, InvalidTokenException;

    void removeACategory(int id, int parentId, String token) throws NoAccessException, InvalidTokenException, InvalidIdException;

    void addAttribute(int id, String attributeName, String attribute, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;

    void changeAttribute(int id, String attributeName, String attribute, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;

    void removeAttribute(int id, String attributeName, String attribute, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;

    void addProduct(int id, int productName, String token) throws exception.InvalidIdException, NoAccessException, InvalidTokenException, InvalidIdException;

    void removeProduct(int id, int productName, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;


    List<Category> getAllCategories(int id, String token) throws InvalidIdException;

    void getExceptionOfIfCategoryExist(int id, String token) throws InvalidIdException;

    List<Category> getAllCategoriesWithFilter(Map<String, String> filter, String sortFiled, boolean isAscending, int id, String token) throws InvalidIdException;

    List<CategoryFeature> getAttribute(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;

    Category getCategory(int id, String token) throws InvalidIdException;

    List<Category> getAllProductWithFilter(Map<String, String> filter, String sortField, boolean isAscending, int id, String token) throws InvalidIdException;

    List<Product> getProducts(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException;


}
