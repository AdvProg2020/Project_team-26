package view.products.all;

import controller.interfaces.product.ISearchAndFilterAndSort;
import controller.interfaces.product.IShowProductController;
import exception.AlreadyLoggedInException;
import model.Product;
import view.*;
import view.main.MainPageView;

import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class AllProductView extends View {
    EnumSet<AllProductsViewValidCommands> validCommands;
    ISearchAndFilterAndSort showOptionsController;
    IShowProductController productController;


    public AllProductView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(AllProductsViewValidCommands.class);
    }

    @Override
    public View run() throws AlreadyLoggedInException {
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            for (AllProductsViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    if (command.getView() != null) {
                        command.setManager(this.manager);
                        command.getView().run();
                    } else
                        command.goToFunction(this);
                }
            }
        }
        return null;
    }

    protected void showAllProducts() {
        List<Product> products = showOptionsController.getProductsBasedOnFiltersAndSortsForOnePage(manager.getToken());
        for (Product product : products) {
            manager.inputOutput.println("name is" + product.getName() + "id is:" + product.getId());
        }
    }

    protected void categoriesOfProducts() {
        /*
        what should we show;
         */


    }

    protected void showSubcategories() {
        /*


         */
    }

    protected void singleProductView(Matcher matcher) {
        matcher.find();


    }


    protected void logOut() {
        new MainPageView(manager).logout(manager.getToken());
    }

    protected void printError() {

    }
}
