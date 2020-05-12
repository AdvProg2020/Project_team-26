package view.cart;

import controller.cart.CartController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NotEnoughProductsException;
import model.Cart;
import view.*;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.Map;
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
            manager.inputOutput.println(e.getMessage());
            return;
        }
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (CartViewValidCommand command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
    }

    public void showAllProducts() {
        manager.inputOutput.println("note for buying you have to choose seller id");
        cart.getProductsWithSort().forEach((productSeller, integer) -> manager.inputOutput.println(
                "product name " + productSeller.getProduct().getName() +
                        "product id :" + productSeller.getProduct().getId()
                        + " seller id : " + productSeller.getId() +
                        " with price" + productSeller.getPrice()
                        + " with amount" + integer));
    }


    public void showProductWithId(Matcher matcher) {
        manager.singleProductView(matcher);
    }

    public void chanageInNumber(Matcher matcher, int increaseOrDecrease) {
        matcher.find();
        if (manager.checkTheInputIsInteger(matcher.group(1))) {
            int id = Integer.parseInt(matcher.group(1));
            int amount = 0;//todo
            try {
                cartController.addOrChangeProduct(id, amount + increaseOrDecrease, manager.getToken());
            } catch (InvalidIdException | InvalidTokenException | NotEnoughProductsException e) {
                manager.inputOutput.println(e.getMessage());
            }
        }

    }


    public void showTotalPriceToBuyer() {
        long price = cartController.getToTalPrice(this.cart, manager.getToken());
        manager.inputOutput.println("total price is: " + price);
    }

    public void purchase() {

    }

    public void sort() {

    }
}
