package view.cart;

import view.*;

import view.View;
import view.ViewManager;
import view.main.MainPageViewValidCommands;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class CartView extends View implements view {
    EnumSet<CartViewValidCommand> validCommands;

    public CartView(ViewManager manager) {
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
