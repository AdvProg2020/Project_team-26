package View.BuyeyAccountView.CartView;

import View.*;

import java.util.EnumSet;

public class CartView extends View {
    EnumSet<CartViewValidCommand> validCommands;

    public CartView() {
        validCommands = EnumSet.allOf(CartViewValidCommand.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
