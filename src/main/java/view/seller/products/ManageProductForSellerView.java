package view.seller.products;

import controller.interfaces.order.IOrderController;
import controller.interfaces.product.IProductController;
import model.User;
import view.*;
import view.filterAndSort.ProductFilterAndSort;

import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class ManageProductForSellerView extends View {
    private EnumSet<ManageProductForSellerViewValidCommands> validCommands;
    private IProductController productController;
    private ProductFilterAndSort productFilterAndSort;
    private IOrderController orderController;
    private User thisUser;


    public ManageProductForSellerView(ViewManager managerView, User thisUser) {
        super(managerView);
        validCommands = EnumSet.allOf(ManageProductForSellerViewValidCommands.class);
        this.thisUser = thisUser;
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
        orderController = (IOrderController) manager.getController(ControllerContainer.Controller.OrderController);
        productFilterAndSort = new ProductFilterAndSort(manager);
    }

    @Override
    public void run() {
        showAll();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (ManageProductForSellerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (isDone)
                manager.inputOutput.println("invalid input");
        }
    }


    protected void edit() {//todo


    }

    protected void showAll() {
        productController.getAllProductWithFilterForSellerId(thisUser.getId(), productFilterAndSort.getFilterForController(),
                productFilterAndSort.getFieldNameForSort(), productFilterAndSort.isAscending()
                , manager.getToken()).forEach(product -> manager.inputOutput.println(
                product.getName() + "with id: " + product.getId() + "in category :" + product.getCategory().getName()));
    }

    protected void showWithId(Matcher matcher) {
        manager.singleProductView(matcher);
    }

    protected void showBuyer(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsInteger(matcher.group(1))) {
            List<User> userList = orderController.getProductBuyerByProductId(Integer.parseInt(matcher.group(1)), manager.getToken());
            userList.forEach(i -> manager.inputOutput.println(i.getFullName()));
            return;
        }
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }


    protected void sorting() {
        productFilterAndSort.run();
    }

    protected void filtering() {
        productFilterAndSort.run();
    }

    protected void help() {
        validCommands.forEach(validCommand -> manager.inputOutput.println(validCommand.toString()));
    }

}
