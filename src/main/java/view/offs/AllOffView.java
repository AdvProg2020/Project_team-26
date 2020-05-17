package view.offs;

import interfaces.discount.IOffController;
import model.Product;
import view.*;
import view.filterAndSort.ProductFilterAndSort;

import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class AllOffView extends View {
    private EnumSet<AllOffsValidCommands> validCommands;
    private ProductFilterAndSort productFilterAndSort;
    private IOffController offController;

    public AllOffView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(AllOffsValidCommands.class);
        productFilterAndSort = new ProductFilterAndSort(manager);
        offController = (IOffController) manager.getController(ControllerContainer.Controller.OffController);
    }

    @Override
    public void run() {
      //  showAll();
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
            if (!isDone)
                manager.inputOutput.println("invalid command pattern");
        }
    }

    private void showAll() {
        List<Product> productList = offController.getAllProductWithOff(productFilterAndSort.getFilterForController(), productFilterAndSort.getFieldNameForSort(), productFilterAndSort.isAscending(), manager.getToken());
        for (Product product : productList) {
            manager.inputOutput.println("name " + product.getName() + " with id:" + product.getId());
            product.getSellerList().forEach(i -> manager.inputOutput.println("seller :" + i.getSeller().getFullName() +
                    "with price " + i.getPrice() + " and price in off " + i.getPriceInOff()));
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


    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void login() {
        manager.loginInAllPagesOptional(super.input);
    }

    protected void register() {
        manager.registerInAllPagesOptional(super.input);
    }


}
