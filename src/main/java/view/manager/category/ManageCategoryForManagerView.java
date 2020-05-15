package view.manager.category;

import controller.interfaces.category.ICategoryController;
import exception.*;
import model.Category;
import model.CategoryFeature;
import model.FeatureType;
import view.ControllerContainer;
import view.View;
import view.ViewManager;
import view.filterAndSort.CategorySort;
import view.products.all.AllProductsViewValidCommands;

import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class ManageCategoryForManagerView extends View {
    private int currentCategory;
    private ICategoryController categoryController;
    private   CategorySort categorySort;
    private  EnumSet<ManageCategoryForManagerViewValidCommands> validCommands;

    public ManageCategoryForManagerView(ViewManager managerView) {
        super(managerView);
        validCommands = EnumSet.allOf(ManageCategoryForManagerViewValidCommands.class);
        currentCategory = 0;
        categorySort = new CategorySort(manager);
        categoryController = (ICategoryController) manager.getController(ControllerContainer.Controller.CategoryController);
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
            if (isDone)
                manager.inputOutput.println("the format is invalid");
        }
    }

    public void setCurrentCategory(int id) {
        currentCategory = id;
    }

    private void showAll() {
        try {
            categoryController.getAllCategoriesWithFilter(categorySort.getFieldNameForSort(), categorySort.isAscending()
                    , currentCategory, manager.getToken()).forEach(category -> manager.inputOutput.println(category.getName() + "with id: "
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

    protected void sorting() {
        categorySort.run();
    }

    protected void RemoveCategoryForManager(Matcher matcher) {
        matcher.find();
        try {
            categoryController.removeACategory(Integer.parseInt(matcher.group(1)), currentCategory, manager.getToken());
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
        //todo
    }

    protected void editCategoryForManager(Matcher matcher) {
        //todo
    }
}
