package view.customer.orders;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class OrdersView extends View implements view {
    EnumSet<OrdersViewValidCommands> validCommands;

    public OrdersView() {
        validCommands = EnumSet.allOf(OrdersViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {

        return null;
    }

    private void rateTheProductWithItsId(Matcher matcher) {

    }

    private void showOrdersWithIdToBuyer(Matcher matcher) {

    }
}
