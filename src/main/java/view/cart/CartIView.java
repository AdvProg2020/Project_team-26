package view.cart;

import controller.cart.CartController;
import exception.*;
import model.Cart;
import view.*;

import view.View;
import view.ViewManager;

import java.rmi.NoSuchObjectException;
import java.util.EnumSet;
import java.util.regex.Matcher;

public class CartIView extends View implements IView {
    private EnumSet<CartViewValidCommand> validCommands;
    private Cart cart;
    private CartController cartController;

    public CartIView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(CartViewValidCommand.class);
    }

    @Override
    public void run() {
        try {
            cart = cartController.showCart(manager.getToken());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
            return;
        }
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("exit")) {
            for (CartViewValidCommand command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
    }

    public void showAllProducts() {
        cart.getProductsWithSort().forEach((productSeller, integer) -> manager.inputOutput.println(
                "product name " + productSeller.getProduct().getName() +
                        "product id :" + productSeller.getId()
                        +
                        " with price" + productSeller.getPrice()
                        + " with amount" + integer.longValue()));
    }


    public void showProductWithId(Matcher matcher) {
        manager.singleProductView(matcher);
    }

    public void changeInNumber(Matcher matcher, int increaseOrDecrease) {
        matcher.find();
        if (manager.checkTheInputIsInteger(matcher.group(1))) {
            int id = Integer.parseInt(matcher.group(1));
            int amount;
            try {
                amount = cartController.getAmountInCartBySellerId(id, manager.getToken());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
                return;
            } catch (NoSuchObjectException e) {
                manager.inputOutput.println(e.getMessage());
                return;
            }
            try {
                cartController.addOrChangeProduct(id, amount + increaseOrDecrease, manager.getToken());
            } catch (InvalidIdException | NotEnoughProductsException e) {
                manager.inputOutput.println(e.getMessage());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage() + "\nnew token will be set  and then try again");
            }
        }

    }


    public void showTotalPriceToBuyer() {
        long price = 0;
        try {
            price = cartController.getToTalPrice(this.cart, manager.getToken());
            manager.inputOutput.println("total price is: " + price);
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
        }
    }

    public void purchase() {
        if (receiveInformation()) {
            discountCode();
            if (manager.loginInAllPagesEssential()) {
                while (true)
                    try {
                        cartController.checkout(manager.getToken());
                        return;
                    } catch (NoAccessException | NotEnoughCreditException | NotEnoughProductsException e) {
                        manager.inputOutput.println(e.getMessage());
                        return;
                    } catch (NotLoggedINException e) {
                        manager.inputOutput.println(e.getMessage() + "\nenter back or enter continue");
                        if (manager.inputOutput.nextLine().equals("back"))
                            return;
                        manager.loginInAllPagesEssential();
                    } catch (InvalidTokenException e) {
                        manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
                    }
            }
        }
    }

    private void discountCode() {
        while (true) {
            manager.inputOutput.println("enter your discount code or back if you dont want to use");
            String discountCode = manager.inputOutput.nextLine();
            if (discountCode.equalsIgnoreCase("back"))
                return;
            try {
                cartController.usePromoCode(discountCode, manager.getToken());
                return;
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
                return;
            } catch (InvalidPromoCodeException | PromoNotAvailableException | NoAccessException e) {
                manager.inputOutput.println(e.getMessage());
            } catch (NotLoggedINException e) {//to be deleted
                manager.inputOutput.println(e.getMessage() + "\nenter back or enter continue");
                if (manager.inputOutput.nextLine().equals("back"))
                    return;
                manager.loginInAllPagesEssential();
            }
        }
    }

    private boolean receiveInformation() {
        while (true) {
            manager.inputOutput.println("enter your address");
            String address = manager.inputOutput.nextLine();
            if (address.equalsIgnoreCase("back"))
                return false;
            manager.inputOutput.println("enter your phone number");
            String phoneNumber = manager.inputOutput.nextLine();//todo
            if (phoneNumber.equalsIgnoreCase("back"))
                return false;
            try {
                cartController.setAddress(address, manager.getToken());
                return true;
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
                //   return false;
            }
        }
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
