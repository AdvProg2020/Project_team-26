package view.manager.category;

import controller.interfaces.category.ICategoryController;
import exception.*;
import controller.interfaces.product.IProductController;
import model.Category;
import model.CategoryFeature;
import model.FeatureType;
import model.Product;
import view.ControllerContainer;
import view.View;
import view.ViewManager;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class ManageCategoryForManagerView extends View {
    private int currentCategory;
    private ICategoryController categoryController;
    IProductController productController;
    private EnumSet<ManageCategoryForManagerViewValidCommands> validCommands;

    public ManageCategoryForManagerView(ViewManager managerView) {
        super(managerView);
        validCommands = EnumSet.allOf(ManageCategoryForManagerViewValidCommands.class);
        currentCategory = 0;
        categoryController = (ICategoryController) manager.getController(ControllerContainer.Controller.CategoryController);
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
    }

    @Override
    public void run() {
        boolean isDone;
        showAll();
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (ManageCategoryForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("the format is invalid");
        }
    }

    public void setCurrentCategory(int id) {
        currentCategory = id;
    }

    protected void showAll() {
        try {
            categoryController.getAllCategories(currentCategory, manager.getToken()).forEach(category -> manager.inputOutput.println(category.getName() + "with id: "
                    + category.getId()));
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
            return;
        }
    }

    protected void viewSubCategories(Matcher matcher) {
        matcher.find();
        try {
            Category category = categoryController.getCategory(Integer.parseInt(matcher.group(1)), manager.getToken());
            ManageCategoryForManagerView manageCategoryForManagerView = new ManageCategoryForManagerView(manager);
            manageCategoryForManagerView.setCurrentCategory(category.getId());
            manageCategoryForManagerView.run();
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    protected void RemoveCategoryForManager(Matcher matcher) {
        matcher.find();
        try {
            Category category = categoryController.getCategoryByName(matcher.group(1), manager.getToken());
            categoryController.removeACategory(category.getId(), currentCategory, manager.getToken());
        } catch (NoAccessException | InvalidIdException | NoObjectIdException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void addCategoryForManager(Matcher matcher) {
        matcher.find();
        Category category = makeCategory(matcher.group(1));
        while (!category.getName().equalsIgnoreCase("back")) {
            try {
                categoryController.addCategory(currentCategory, category, manager.getToken());
            } catch (InvalidIdException | NoAccessException e) {
                manager.inputOutput.println(e.getMessage());
                return;
            } catch (ObjectAlreadyExistException e) {
                manager.inputOutput.println(e.getMessage());
                manager.inputOutput.println("enter back ro type new name");
                category.setName(manager.inputOutput.nextLine());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
                return;
            }
        }
    }

    private Category makeCategory(String name) {
        Category category = new Category(name);
        manager.inputOutput.println("enter category feature name then type(double int string{deafult is string})\nenter back or finish for ending");
        List<CategoryFeature> features = category.getFeatures();
        String featureName = "";
        String type = "";
        while (!featureName.equalsIgnoreCase("back|finish") || !type.equalsIgnoreCase("back|finish")) {
            featureName = manager.inputOutput.nextLine();
            type = manager.inputOutput.nextLine();
            features.add(new CategoryFeature(name, getFeatureType(type)));
        }
        return category;
    }

    private FeatureType getFeatureType(String type) {
        switch (type) {
            case "double":
                return FeatureType.DOUBLE;
            case "int":
                return FeatureType.INTEGER;
        }
        return FeatureType.STRING;
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("edit [name]");
        commandList.add("show all");
        commandList.add("remove [name]");
        commandList.add("view sub [id]");
        commandList.add("add [name]");
        commandList.add("logout");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }

    protected void editCategoryForManager(Matcher matcher) {
        matcher.find();
        Category category = null;
        try {
            category = categoryController.getCategoryByName(matcher.group(1), manager.getToken());
            String filed = "";
            while (!filed.equalsIgnoreCase("back")) {
                manager.inputOutput.println("chose field you want to edit[add attribute,remove attribute,add product,remove product]\nenter back.");
                filed = manager.inputOutput.nextLine();
                if (filed.equalsIgnoreCase("back"))
                    return;
                switch (filed) {
                    case "add attribute":
                        changeAttribute(category, "add");
                        break;
                    case "remove attribute":
                        changeAttribute(category, "remove");
                        break;
                    case "add product":
                        changeProduct(category, "add");
                        break;
                    case "remove product":
                        changeProduct(category, "remove");
                }
            }
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    private void changeProduct(Category category, String type) {
        manager.inputOutput.println("enter the name :");
        String name = manager.inputOutput.nextLine();
        try {
            Product product = productController.getProductByName(name, manager.getToken());
            if (type.equals("remove")) {
                categoryController.removeProduct(category.getId(), product.getId(), manager.getToken());
                return;
            }
            categoryController.addProduct(category.getId(), product.getId(), manager.getToken());
        } catch (NoObjectIdException | InvalidIdException | NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }

    }

    private void changeAttribute(Category category, String type) {
        manager.inputOutput.println("enter the name:");
        String name = manager.inputOutput.nextLine();
        try {
            if (type.equals("remove")) {
                categoryController.removeAttribute(category.getId(), name, manager.getToken());
                return;
            }
            manager.inputOutput.println("enter the type: [double , int , string]");
            String feature = manager.inputOutput.nextLine();
            categoryController.addAttribute(category.getId(), name, getFeatureType(feature), manager.getToken());
        } catch (InvalidIdException | NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }
}
