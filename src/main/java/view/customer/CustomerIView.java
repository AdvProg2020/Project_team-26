package view.customer;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import controller.interfaces.discount.IPromoController;
import exception.AlreadyLoggedInException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import view.*;
import view.cart.CartIView;
import view.customer.orders.OrdersIView;
import view.filterAndSort.CustomerFilterAndSort;
import view.seller.products.ManageProductForSellerViewValidCommands;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;

public class CustomerIView extends View implements IView {
    EnumSet<CustomerValidCommand> validCommands;
    UserView userView;
    ArrayList<String> editableFields;
    IUserInfoController infoController;
    IShowUserController userController;
    IPromoController promoController;
    CustomerFilterAndSort customerFilterAndSort;


    public CustomerIView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(CustomerValidCommand.class);
        userView = UserView.getInstance();
        editableFields = new ArrayList<>();
        customerFilterAndSort = new CustomerFilterAndSort(manager);
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
            promoController.getAllPromoCodeForCustomer(customerFilterAndSort.getFilterForController(),
                    customerFilterAndSort.getFieldNameForSort(), customerFilterAndSort.isAscending(),
                    manager.getToken()).forEach(
                    promo -> manager.inputOutput.println("promo with code : " + promo.getPromoCode()
                            + " with max : " + promo.getMaxDiscount() + " and percent " + promo.getPercent() +
                            " started at " + promo.getStartDate().toString() + " end : " + promo.getEndDate().toString())
            );
        } catch (NotLoggedINException e) {
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
        CartIView cartIView = new CartIView(manager);
        cartIView.run();
    }

    protected void orders() {
        OrdersIView orderView = new OrdersIView(manager);
        orderView.run();
    }
}
