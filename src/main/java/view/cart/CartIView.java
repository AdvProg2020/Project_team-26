package view.cart;

import view.*;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class CartIView extends View implements IView {
    EnumSet<CartViewValidCommand> validCommands;

    public CartIView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(CartViewValidCommand.class);
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (CartViewValidCommand command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    public void showAllProducts() {
    }


    public void showProductWithId(Matcher matcher) {
    }


    public void showCart(Matcher matcher) {

    }


    public void increaseNumberOfProductForBuyerWithId(Matcher matcher) {
    }


    public void decreaseNumberOfProductForBuyerWithId(Matcher matcher) {

    }


    public void showTotalPriceToBuyer() {

    }

    public void purchase() {

    }
}
