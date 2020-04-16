package view.cart;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class CartView extends View {
    EnumSet<CartViewValidCommand> validCommands;

    public CartView() {
        validCommands = EnumSet.allOf(CartViewValidCommand.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }


    private void showAllProducts(Matcher matcher) {
    }
    private void showProductWithId(Matcher matcher) {
    }

    private void showCart(Matcher matcher) {

    }

    private void increaseNumberOfProductForBuyerWithId(Matcher matcher) {
    }

    private void decreaseNumberOfProductForBuyerWithId(Matcher matcher) {

    }
    private void showTotalPriceToBuyer(Matcher matcher) {

    }
    private void purchase(Matcher matcher) {

    }
}
