package view.products.all;

import interfaces.category.ICategoryController;
import interfaces.product.IProductController;
import exception.InvalidIdException;
import view.*;
import view.filterAndSort.ProductFilterAndSort;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class AllProductView extends View {
    private EnumSet<AllProductsViewValidCommands> validCommands;
    private  IProductController productController;
    private    ICategoryController categoryController;
    private   int currentCategory;
    private  ProductFilterAndSort productFilterAndSort;


    public AllProductView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(AllProductsViewValidCommands.class);
        currentCategory = 0;
        productFilterAndSort = new ProductFilterAndSort(manager);
        categoryController = (ICategoryController) manager.getController(ControllerContainer.Controller.CategoryController);
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
    }

    @Override
    public void run() {
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
            if (isDone)
                printError();
        }
    }

    protected void categoriesOfProducts() {
        try {
            categoryController.getAllCategoriesWithFilter(productFilterAndSort.getFieldNameForSort(), productFilterAndSort.isAscending(), currentCategory, manager.getToken()).forEach(category -> manager.inputOutput.println("name is " +
                    category.getName() + " with id " + category.getId()));
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    protected void showAllProducts() {
        try {
            categoryController.getAllProductWithFilter(productFilterAndSort.getFilterForController(), productFilterAndSort.getFieldNameForSort(), productFilterAndSort.isAscending(), currentCategory, manager.getToken()).forEach(product -> manager.inputOutput.println(
                    "name is" + product.getName() + "id is:" + product.getId()));
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    protected void subcategory(Matcher matcher) {
        matcher.find();
        String id = matcher.group(1);
        if (manager.checkTheInputIsInteger(id)) {
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
        manager.singleProductView(matcher);
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

    protected void printError() {

    }

    protected void filter() {
        productFilterAndSort.run();
    }

    protected void sort() {
        productFilterAndSort.run();
    }

    public void setCurrentCategory(int currentCategory) {
        this.currentCategory = currentCategory;
    }
}
