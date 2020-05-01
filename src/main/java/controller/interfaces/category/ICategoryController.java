package controller.interfaces.category;

import controller.Exceptions;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import model.Category;
import model.repository.CategoryRepository;

import java.util.ArrayList;

public interface ICategoryController {


    int addCategory(int patternId, String newCategoryName, String token) throws exception.NoObjectWithIdException, exception.ObjectAlreadyExistException, NoAccessException;

    void editCategory(int id, ArrayList<String> changes, String token) throws exception.NoObjectWithIdException, NoAccessException;

    void addAttribute(int id, String attributeName , String attribute, String token) throws NoObjectWithIdException, NoAccessException

    void removeACategory(int id, int parentId , String token) throws NoObjectWithIdException, NoAccessException

    void addProduct(int id, int productName, String token) throws exception.NoObjectWithIdException, NoAccessException;


}
