package View.BuyeyAccountView.ViewOrders;

import View.*;

import java.util.EnumSet;

public class OrdersView extends View {
    EnumSet<OrdersViewValidCommands> validCommands;

    public OrdersView() {
        validCommands = EnumSet.allOf(OrdersViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
