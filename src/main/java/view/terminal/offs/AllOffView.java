package view.terminal.offs;

import controller.interfaces.discount.IOffController;
import model.Product;
import view.terminal.ControllerContainer;
import view.terminal.View;
import view.terminal.ViewManager;
import view.terminal.filterAndSort.ProductFilterAndSort;
import view.terminal.products.all.AllProductView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

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
        manager.inputOutput.println("offs :");
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
            if (!isDone)
                manager.inputOutput.println("invalid command pattern");
        }
    }

    protected void showAll() {
        List<Product> productList = offController.getAllProductWithOff(productFilterAndSort.getFilterForController(), productFilterAndSort.getFieldNameForSort(), productFilterAndSort.isAscending(), manager.getToken());
        for (Product product : productList) {
            manager.inputOutput.println("name : " + product.getName() + " with id : " + product.getId());
            product.getSellerList().stream().filter(i -> i.getPriceInOff() != i.getPrice()).collect(Collectors.
                    toList()).forEach(i -> manager.inputOutput.println((i.getPriceInOff() != i.getPrice())
                    ? ("seller : " + i.getSeller().getUsername() + " with id : " + i.getSeller().getId()
                    + " with price " + i.getPrice() + " and price in off " + i.getPriceInOff()) : ""));
        }
    }

    protected void showProductWithId(Matcher matcher) {
        manager.singleProductPage(matcher);
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

    protected void product() {
        AllProductView allProductView = new AllProductView(manager);
        allProductView.run();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("products");
        commandList.add("show product [id]");
        commandList.add("filtering");
        commandList.add("sorting");
        if (manager.getIsUserLoggedIn()) {
            commandList.add("logout");
        } else {
            commandList.add("login [username]");
            commandList.add("create account [manager|buyer|seller] [username]");
        }
        commandList.forEach(i -> manager.inputOutput.println(i));
    }


}