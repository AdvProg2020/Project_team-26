package view.cli.products.all;

import controller.interfaces.category.ICategoryController;
import controller.interfaces.product.IProductController;
import exception.InvalidIdException;
import model.Category;
import view.cli.ControllerContainer;
import view.cli.View;
import view.cli.ViewManager;
import view.cli.filterAndSort.ProductFilterAndSort;
import view.cli.offs.AllOffView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class AllProductView extends View {
    private EnumSet<AllProductsViewValidCommands> validCommands;
    private IProductController productController;
    private ICategoryController categoryController;
    private int currentCategory;
    private ProductFilterAndSort productFilterAndSort;


    public AllProductView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(AllProductsViewValidCommands.class);
        currentCategory = 1;
        productFilterAndSort = new ProductFilterAndSort(manager);
        categoryController = (ICategoryController) manager.getController(ControllerContainer.Controller.CategoryController);
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
    }

    @Override
    public void run() {
        manager.inputOutput.println("products menu : ");
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            isDone = false;
            for (AllProductsViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("invalid command.");
        }
    }

    protected void categoriesOfProducts() {
        try {
            List<Category> categoryList = categoryController.getAllCategories(currentCategory, manager.getToken());
            for (Category category : categoryList) {
                manager.inputOutput.println("name is " +
                        category.getName() + " with id " + category.getId());
                manager.inputOutput.println("feature names : ");
                category.getFeatures().forEach(categoryFeature -> manager.inputOutput.println(categoryFeature.getFeatureName()));
            }
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    protected void showAllProducts() {
        try {
            categoryController.getAllProductWithFilter(productFilterAndSort.getFilterForController(), productFilterAndSort.getFieldNameForSort(),
                    productFilterAndSort.isAscending(), currentCategory, manager.getToken())
                    .forEach(product -> manager.inputOutput.println(
                            "name is : " + product.getName() + "id is: " + product.getId()));
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    protected void subcategory(Matcher matcher) {
        matcher.find();
        String id = matcher.group(1);
        if (manager.checkTheInputIsIntegerOrLong(id, false)) {
            try {
                int nextId = Integer.parseInt(id);
                categoryController.getExceptionOfIfCategoryExist(nextId, manager.getToken());
                AllProductView allProductView = new AllProductView(this.manager);
                allProductView.setCurrentCategory(nextId);
                allProductView.run();
            } catch (InvalidIdException e) {
                manager.inputOutput.println(e.getMessage());
            }
            return;
        }
        manager.inputOutput.println("the id is invalid format.");
    }

    protected void singleProductView(Matcher matcher) {
        manager.singleProductPage(matcher);
    }


    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void login() {
        manager.loginInAllPagesOptional(super.input);
    }

    protected void register() {
        manager.registerInAllPagesOptional(super.input);
    }

    protected void filter() {
        productFilterAndSort.run();
    }

    protected void sort() {
        productFilterAndSort.run();
    }

    protected void off() {
        AllOffView allOffView = new AllOffView(manager);
        allOffView.run();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("offs");
        commandList.add("view categories");
        commandList.add("filtering");
        commandList.add("sub [id]");
        commandList.add("sorting");
        commandList.add("show products");
        commandList.add("show product [productId]");
        if (manager.getIsUserLoggedIn()) {
            commandList.add("logout");
        } else {
            commandList.add("login [username]");
            commandList.add("create account [manager|buyer|seller] [username]");
        }
        commandList.forEach(i -> manager.inputOutput.println(i));
    }

    private void setCurrentCategory(int currentCategory) {
        this.currentCategory = currentCategory;
    }
}
