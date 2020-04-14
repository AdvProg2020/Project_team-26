package view.buyer.orders;

import view.*;

import java.util.EnumSet;
import java.util.List;

public class OrdersView extends View {
    EnumSet<OrdersViewValidCommands> validCommands;

    public OrdersView() {
        validCommands = EnumSet.allOf(OrdersViewValidCommands.class);
    }

    @Override
    public void run(ViewManager manager) {

        return ;
    }
}
