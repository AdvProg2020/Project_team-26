package controller.interfaces.category;

import controller.Exceptions;
import controller.category.Category;

public interface ICategoryController {

    void addCategory(String name, String token) throws Exceptions.FieldsExistWithSameName;

    void addAttribute(String name, String attribute, String token);

    void removeACategory(String name, String token) throws Exceptions.TheParameterDoesNOtExist;

    void addCustomer(String name, String username, String token) throws Exceptions.TheParameterDoesNOtExist;

    void addProduct(String name, String productName, String token) throws Exceptions.TheParameterDoesNOtExist;

    void addSubCategories(String parentName, Category subCategory, String token) throws Exceptions.FieldsExistWithSameName;


}
