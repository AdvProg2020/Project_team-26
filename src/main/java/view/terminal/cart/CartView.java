package view.terminal.cart;

import controller.cart.CartController;
import exception.*;
import model.Cart;

import view.terminal.ControllerContainer;
import view.terminal.View;
import view.terminal.ViewManager;
import view.terminal.offs.AllOffView;
import view.terminal.products.all.AllProductView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class CartView extends View {
    private EnumSet<CartViewValidCommand> validCommands;
    private Cart cart;
    private CartController cartController;

    public CartView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(CartViewValidCommand.class);
        cartController = (CartController) manager.getController(ControllerContainer.Controller.CartController);
    }

    @Override
    public void run() {
        try {
            cart = cartController.getCart(manager.getToken());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
            return;
        }
        manager.inputOutput.println("cart menu :");
        boolean done;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            done = false;
            for (CartViewValidCommand command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    done = true;
                    break;
                }
            }
            if (done == false)
                manager.inputOutput.println("invalid command");
        }
    }

    public void showAllProducts() {
        cart.getProduct().forEach((productSeller, integer) -> manager.inputOutput.println(
                "product name " + productSeller.getProduct().getName() +
                        "product id :" + productSeller.getId()));
    }


    public void showProductWithId(Matcher matcher) {
        manager.singleProductPage(matcher);
    }

    public void changeInNumber(Matcher matcher, int increaseOrDecrease) {
        matcher.find();
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1), false)) {
            int id = Integer.parseInt(matcher.group(1));
            int amount;
            try {
                amount = cartController.getAmountInCartBySellerId(id, manager.getToken());
                cartController.addOrChangeProduct(id, amount + increaseOrDecrease, manager.getToken());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
            } catch (NoSuchObjectException | InvalidIdException | NotEnoughProductsException e) {
                manager.inputOutput.println(e.getMessage());
            }
        }
    }


    public void showTotalPriceToBuyer() {
        long price = 0;
        try {
            price = cartController.getTotalPrice(this.cart, manager.getToken());
            manager.inputOutput.println("total price is: " + price);
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    public void purchase() {
        if (receiveInformation()) {
            discountCode();
            while (true) {
                try {
                    cartController.checkout(manager.getToken());
                    return;
                } catch (NoAccessException | NotEnoughCreditException | NotEnoughProductsException e) {
                    manager.inputOutput.println(e.getMessage());
                    return;
                } catch (NotLoggedINException e) {
                    manager.inputOutput.println(e.getMessage());
                    manager.loginInAllPagesEssential();
                } catch (InvalidTokenException e) {
                    manager.setTokenFromController(e.getMessage());
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
                manager.setTokenFromController(e.getMessage());
                return;
            } catch (InvalidPromoCodeException | PromoNotAvailableException | NoAccessException e) {
                manager.inputOutput.println(e.getMessage());
            } catch (NotLoggedINException e) {
                manager.inputOutput.println(e.getMessage());
                manager.loginInAllPagesEssential();
            }
        }
    }

    private boolean receiveInformation() {
        while (true) {
            manager.inputOutput.println("enter your address or back to cancel payment");
            String address = manager.inputOutput.nextLine();
            if (address.equalsIgnoreCase("back"))
                return false;
            try {
                cartController.setAddress(address, manager.getToken());
                return true;
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
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

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("offs");
        commandList.add("show products");
        commandList.add("products");
        commandList.add("view [productId]");
        commandList.add("increase [productId]");
        commandList.add("decrease [productId]");
        commandList.add("show total price");
        commandList.add("purchase");
        if (manager.getIsUserLoggedIn()) {
            commandList.add("logout");
        } else {
            commandList.add("login [username]");
            commandList.add("create account [manager|buyer|seller] [username]");
        }
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
