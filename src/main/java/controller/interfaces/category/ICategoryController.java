package controller.interfaces.category;

import controller.Exceptions;
import model.Category;

import java.util.ArrayList;

public interface ICategoryController {

    void addCategory(ArrayList<String> parents, String newCategoryName, String token) throws Exceptions.FieldsExistWithSameName;

    void editCategory(ArrayList<String> names, ArrayList<String> changes, String token) throws Exceptions.TheParameterDoesNOtExist, Exceptions.FieldsExistWithSameName;

    void addAttribute(ArrayList<String> names, String attribute, String token);

    void removeACategory(ArrayList<String> names,String remove, String token) throws Exceptions.TheParameterDoesNOtExist;

    void addCustomer(ArrayList<String> names, String username, String token) throws Exceptions.TheParameterDoesNOtExist;

    void addProduct(ArrayList<String> names, String productName, String token) throws Exceptions.TheParameterDoesNOtExist;

    void addSubCategories(ArrayList<String> names, Category subCategory, String token) throws Exceptions.FieldsExistWithSameName;


}
