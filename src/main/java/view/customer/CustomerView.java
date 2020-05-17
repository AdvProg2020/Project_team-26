package view.customer;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import controller.interfaces.discount.IPromoController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import view.*;
import view.cart.CartView;
import view.customer.orders.OrdersIView;
import view.filterAndSort.PromoSort;

import java.util.ArrayList;
import java.util.EnumSet;

public class CustomerView extends View {
    private EnumSet<CustomerValidCommand> validCommands;
    private UserView userView;
    private ArrayList<String> editableFields;
    private IUserInfoController infoController;
    private IShowUserController userController;
    private IPromoController promoController;
    private PromoSort customerFilterAndSort;


    public CustomerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(CustomerValidCommand.class);
        userView = UserView.getInstance();
        editableFields = new ArrayList<>();
        customerFilterAndSort = new PromoSort(manager);
        infoController = (IUserInfoController) manager.getController(ControllerContainer.Controller.UserInfoController);
        userController = (IShowUserController) manager.getController(ControllerContainer.Controller.ShowUserController);
        promoController = (IPromoController) manager.getController(ControllerContainer.Controller.ProductController);
    }

    private void initialEditFields() {
        userView.initialEditFields(editableFields, manager, userController);
    }

    @Override
    public void run() {
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (CustomerValidCommand command : validCommands) {
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

    protected void editTheField() {
        initialEditFields();
        userView.edit(editableFields, manager, infoController);
    }

    protected void promoCodes() {
        try {
            promoController.getAllPromoCodeForCustomer(customerFilterAndSort.getFieldNameForSort(), customerFilterAndSort.
                            isAscending(),
                    manager.getToken()).forEach(
                    promo -> manager.inputOutput.println("promo with code : " + promo.getPromoCode()
                            + " with max : " + promo.getMaxDiscount() + " and percent " + promo.getPercent() +
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
        customerFilterAndSort.run();
    }

    protected void filtering() {
        customerFilterAndSort.run();
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void help() {
        validCommands.forEach(validCommand -> manager.inputOutput.println(validCommand.toString()));
    }
}
