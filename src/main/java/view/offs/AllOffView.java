package view.offs;

import controller.interfaces.discount.IOffController;
import controller.interfaces.product.IProductController;
import exception.InvalidIdException;
import model.Product;
import view.*;
import view.filterAndSort.ProductFilterAndSort;
import view.products.all.AllProductsViewValidCommands;
import view.products.single.SingleProductView;

import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class AllOffView extends View implements IView {
    private EnumSet<AllOffsValidCommands> validCommands;
    private ProductFilterAndSort productFilterAndSort;
    private IOffController offController;

    public AllOffView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(AllOffsValidCommands.class);
    }

    @Override
    public void run() {
        showAll();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            isDone = false;
            for (AllOffsValidCommands command : validCommands) {
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

    private void showAll() {
        List<Product> productList = offController.getAllProductWithOff(productFilterAndSort.getSortAndFilter(), toString());
        for (Product product : productList) {
            manager.inputOutput.println("name " + product.getName() + " with id:" + product.getId());
            product.getSellerList().forEach(i -> manager.inputOutput.println("seller :" + i.getSeller().getFullName() +
                    "with price " + i.getPrice()));
        }
    }

    protected void showProductWithId(Matcher matcher) {
        manager.singleProductView(matcher);
    }

    protected void sort() {
        productFilterAndSort.run();
    }

    protected void filter() {
        productFilterAndSort.run();
    }

    protected void printError() {

    }


}
