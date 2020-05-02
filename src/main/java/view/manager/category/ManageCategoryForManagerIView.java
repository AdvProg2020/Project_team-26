package view.manager.category;

import controller.Exceptions;
import controller.category.CategoryController;
import controller.interfaces.category.ICategoryController;
import model.Category;
import model.CategoryFeature;
import model.Product;
import view.*;
import view.main.MainPageView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCategoryForManagerViewI extends View implements ViewI {
    EnumSet<ManageCategoryForManagerViewValidCommands> validCommands;
    private ICategoryController controller;
    private IShowCategoryController detailController;
    private ArrayList<String> parentName;


    public ManageCategoryForManagerViewI(ViewManager managerView, ICategoryController controller, IShowCategoryController detailController) {
        super(managerView);
        validCommands = EnumSet.allOf(ManageCategoryForManagerViewValidCommands.class);
        this.controller = controller;
        this.detailController = detailController;
        parentName = new ArrayList<>();
    }

    @Override
    public View run() {
        while (manager.getIsUserLoggedin()) {
            showAll();
            if ((super.input = (manager.inputOutput.nextLine()).trim()).matches("back"))
                break;
            for (ManageCategoryForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    private void showAll() {
        Category[] categories = detailController.getAllCategories(parentName, manager.getTocken());
        for (Category category : categories) {
            show(category);
        }
    }

    private void show(Category category) {
        manager.inputOutput.println("category name is:" + category.getName());
        manager.inputOutput.println("attributes of this category is:");
        showAttribute(category.getFeatures());
        showProducts(category.getProducts());
    }

    private void showAttribute(List<CategoryFeature> categoryFeatures) {
        for (CategoryFeature categoryFeature : categoryFeatures) {
            manager.inputOutput.println("name:" + categoryFeature.getFeatureName());
            manager.inputOutput.println("features:" + categoryFeature.getFeatureValue());
        }
    }

    protected void viewSubCategories(Matcher name) {
        name.find();
        List<Category> categories = detailController.getCategory(name.group(1), manager.getTocken()).getSubCategory();
        for (Category category : categories) {
            show(category);
        }
    }

    private void showProducts(List<Product> products) {
        for (Product product : products) {
            manager.inputOutput.println("name:" + product.getName());
        }
    }


    protected Category addCategoryForManager(Matcher matcher) {
        matcher.find();
        String name = matcher.group(1);
        try {
            controller.addCategory(parentName, matcher.group(1), manager.getTocken());
            createCategory();
        } catch (Exceptions.FieldsExistWithSameName fieldsExistWithSameName) {
            fieldsExistWithSameName.getMessage();
        }
        return detailController.getCategory(name, manager.getTocken());
    }

    private void createCategory() {
        manager.inputOutput.println("type attribute");
        controller.addAttribute(parentName, manager.inputOutput.nextLine(), manager.getTocken());
        addProductToCategory();
        addUsersToCategory();
        addSubCategories();
    }

    private void addSubCategories() {
        manager.inputOutput.println("enter the subCategories and remember to type end to finish entering subCategories");
        String input = manager.inputOutput.nextLine();
        while (!input.matches("end")) {
            Category category = addCategoryForManager(Pattern.compile("create (.*)").matcher("create " + input));
            try {
                controller.addSubCategories(parentName, category, manager.getTocken());
            } catch (Exceptions.FieldsExistWithSameName fieldsExistWithSameName) {
                fieldsExistWithSameName.printStackTrace();
            }
            input = manager.inputOutput.nextLine();
        }
    }

    private void addProductToCategory() {
        manager.inputOutput.println("enter the products and remember to type end to finish entering products");
        String input = manager.inputOutput.nextLine();
        while (!input.matches("end")) {
            try {
                controller.addProduct(parentName, input, manager.getTocken());
            } catch (Exceptions.TheParameterDoesNOtExist theParameterDoesNOtExist) {
                theParameterDoesNOtExist.getMessage();
            }
            input = manager.inputOutput.nextLine();
        }
    }

    private void addUsersToCategory() {
        manager.inputOutput.println("enter the users and remember to type end to finish entering users");
        String input = manager.inputOutput.nextLine();
        while (!input.matches("end")) {
            try {
                controller.addCustomer(parentName, input, manager.getTocken());
            } catch (Exceptions.TheParameterDoesNOtExist theParameterDoesNOtExist) {
                theParameterDoesNOtExist.getMessage();
            }
            input = manager.inputOutput.nextLine();
        }
    }


    protected void editCategoryForManager(Matcher matcher) {
        matcher.find();
        ArrayList<String> changes = new ArrayList<>();
        changes.add(matcher.group(1));
        makeEditPart(changes);
        try {
            controller.editCategory(parentName, changes, manager.getTocken());
        } catch (Exceptions.TheParameterDoesNOtExist | Exceptions.FieldsExistWithSameName error) {
            error.getMessage();
        }
        changeSubCategories(matcher.group(1));
    }

    private void makeEditPart(ArrayList<String> changes) {
        if (changeLists(changes))
            return;
        manager.inputOutput.println("type new one");
        changes.add(manager.inputOutput.nextLine());
        return;
    }

    private boolean changeLists(ArrayList<String> changes) {
        if (changes.get(0).matches("users")) {
            manager.inputOutput.println("type [remove|add] [username]");
            changes.add(manager.inputOutput.nextLine());
            return true;
        }
        if (changes.get(0).matches("subCategories")) {
            manager.inputOutput.println("type remove [subcategory]");
            changes.add(manager.inputOutput.nextLine());
            return true;
        }
        return false;
    }

    private void changeSubCategories(String parentName) {
        manager.inputOutput.println("if you want to add or edit the subcategory type [add] | [edit [name]] otherwise none");
        String command = manager.inputOutput.nextLine();
        if (command.matches("add")) {
            return;
        }
        if (command.matches("edit (.*)")) {
            ManageCategoryForManagerViewI newPage = new ManageCategoryForManagerViewI(manager, new CategoryController(), new ShowCategoryController());
            Matcher matcher = Pattern.compile("edit (.*)").matcher(command);
            matcher.find();
            newPage.parentName.add(matcher.group(1));
            newPage.run();
        }
    }

    protected void RemoveCategoryForManager(Matcher matcher) {
        try {
            controller.removeACategory(parentName, matcher.group(1), manager.getTocken());
        } catch (Exceptions.TheParameterDoesNOtExist theParameterDoesNOtExist) {
            theParameterDoesNOtExist.getMessage();
        }

    }

    protected void logOut() {
        new MainPageView(manager).logout(manager.getTocken());
    }

    protected void help(boolean isLoggedIn) {
        manager.inputOutput.println("add [category]\nremove [category]\nnote that if you want to edit the sub category you have to select category" +
                " \nedit [category]\nview sub [category]\nhelp");
        if (isLoggedIn)
            manager.inputOutput.println("logout");
    }


}
