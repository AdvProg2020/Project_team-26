package view.cli.customer;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import controller.interfaces.discount.IPromoController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import view.cli.ControllerContainer;
import view.cli.UserView;
import view.cli.View;
import view.cli.ViewManager;
import view.cli.cart.CartView;
import view.cli.customer.orders.OrdersIView;
import view.cli.filterAndSort.PromoSort;
import view.cli.offs.AllOffView;
import view.cli.products.all.AllProductView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CustomerView extends View {
    private EnumSet<CustomerValidCommand> validCommands;
    private UserView userView;
    private ArrayList<String> editableFields;
    private IUserInfoController infoController;
    private IShowUserController userController;
    private IPromoController promoController;
    private PromoSort promoSort;


    public CustomerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(CustomerValidCommand.class);
        userView = UserView.getInstance();
        editableFields = new ArrayList<>();
        promoSort = new PromoSort(manager);
        infoController = (IUserInfoController) manager.getController(ControllerContainer.Controller.UserInfoController);
        userController = (IShowUserController) manager.getController(ControllerContainer.Controller.ShowUserController);
        promoController = (IPromoController) manager.getController(ControllerContainer.Controller.PromoController);
        initialEditFields();
    }

    private void initialEditFields() {
        userView.initialEditFields(editableFields, manager, userController);
    }

    @Override
    public void run() {
        manager.inputOutput.println("customer menu :");
        boolean isDone;
        while ((!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (CustomerValidCommand command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    protected void editTheField() {
        userView.edit(editableFields, manager, infoController);
    }

    protected void promoCodes() {
        try {
            promoController.getAllPromoCodeForCustomer(promoSort.getFieldNameForSort(), promoSort.
                            isAscending(),
                    manager.getToken()).forEach(
                    promo -> manager.inputOutput.println("promo with code : " + promo.getPromoCode()
                            + " with max : " + promo.getMaxDiscount() + "\nand percent " + promo.getPercent() +
                            " started at " + promo.getStartDate().toString() + " end : " + promo.getEndDate().toString())
            );
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void viewPersonalInfo() {
        userView.viewPersonalInfo(manager, userController);
    }

    protected void balance() {
        userView.balance(manager, infoController);
    }

    protected void cart() {
        CartView cartIView = new CartView(manager);
        cartIView.run();
    }

    protected void orders() {
        OrdersIView orderView = new OrdersIView(manager);
        orderView.run();
    }

    protected void sorting() {
        promoSort.run();
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("offs");
        commandList.add("view personal info");
        commandList.add("products");
        commandList.add("edit");
        commandList.add("view cart");
        commandList.add("view orders");
        commandList.add("view balance");
        commandList.add("view discount codes");
        commandList.add("logout");
        commandList.add("sorting");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }

    protected void product() {
        AllProductView allProductView = new AllProductView(manager);
        allProductView.run();
    }

    protected void off() {
        AllOffView allOffView = new AllOffView(manager);
        allOffView.run();
    }
}
