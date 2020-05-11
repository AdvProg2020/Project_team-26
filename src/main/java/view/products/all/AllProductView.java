package view.products.all;

import controller.interfaces.category.ICategoryController;
import controller.interfaces.product.IProductController;
import exception.InvalidIdException;
import model.Product;
import view.*;
import view.filterAndSort.FilterAndSort;
import view.filterAndSort.ProductFilterAndSort;
import view.main.MainPageView;
import view.products.single.SingleProductView;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class AllProductView extends View {
    EnumSet<AllProductsViewValidCommands> validCommands;
    IProductController productController;
    ICategoryController categoryController;
    int currentCategory;
    ProductFilterAndSort filterAndSort;


    public AllProductView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(AllProductsViewValidCommands.class);
        currentCategory = 0;
    }

    @Override
    public View run() {
        boolean isDone = false;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            for (AllProductsViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.setManager(this.manager);
                    command.setFiltering(this.filterAndSort);
                    if (command.getView() != null)
                        command.getView().run();
                    else
                        command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (isDone)
                printError();
        }
        return null;
    }

    protected void categoriesOfProducts() {
        try {
            categoryController.getAllCategoriesWithFilter(filterAndSort.getSortAndFilter(), currentCategory, manager.getToken()).forEach(category -> manager.inputOutput.println("name is " +
                    category.getName() + " with id " + category.getId()));
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    protected void showAllProducts() {
        try {
            categoryController.getAllProductWithFilter(filterAndSort.getSortAndFilter(), currentCategory, manager.getToken()).forEach(product -> manager.inputOutput.println(
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
        matcher.find();
        String id = matcher.group(1);
        if (manager.checkTheInputIsInteger(id)) {
            int productId = Integer.parseInt(id);
            try {
                Product product = productController.getProductById(productId, manager.getToken());
                SingleProductView singleProductView = new SingleProductView(manager, product);
                singleProductView.run();
            } catch (InvalidIdException e) {
                manager.inputOutput.println(e.getMessage());
            }
            return;
        }
        manager.inputOutput.println("the id is invalid format.");
    }


    protected void logOut() {
        new MainPageView(manager).logout(manager.getToken());
    }

    protected void printError() {

    }

    public void setCurrentCategory(int currentCategory) {
        this.currentCategory = currentCategory;
    }
}
