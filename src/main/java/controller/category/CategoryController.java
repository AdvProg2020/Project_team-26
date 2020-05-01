package controller.category;

import controller.Exceptions;
import controller.interfaces.category.ICategoryController;

import java.util.ArrayList;

public class CategoryController implements ICategoryController {
    @Override
    public void addCategory(ArrayList<String> parents, String newCategoryName, String token) throws Exceptions.FieldsExistWithSameName {

    }

    @Override
    public void editCategory(ArrayList<String> names, ArrayList<String> changes, String token) throws Exceptions.TheParameterDoesNOtExist, Exceptions.FieldsExistWithSameName {

    }

    @Override
    public void addAttribute(ArrayList<String> names, String attribute, String token) {

    }

    @Override
    public void removeACategory(ArrayList<String> names, String remove, String token) throws Exceptions.TheParameterDoesNOtExist {

    }

    @Override
    public void addCustomer(ArrayList<String> names, String username, String token) throws Exceptions.TheParameterDoesNOtExist {

    }

    @Override
    public void addProduct(ArrayList<String> names, String productName, String token) throws Exceptions.TheParameterDoesNOtExist {

    }

    @Override
    public void addSubCategories(ArrayList<String> names, CategoryForView subCategory, String token) throws Exceptions.FieldsExistWithSameName {

    }
}
