package view.cart;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
public class CartView extends View {
    EnumSet<CartViewValidCommand> validCommands;

    public CartView() {
        validCommands = EnumSet.allOf(CartViewValidCommand.class);
    }

    @Override
    public void run(ViewManager manager) {
        return ;
    }
}
