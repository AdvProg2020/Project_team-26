package view.customer.orders;

import view.*;
import view.cart.CartViewValidCommand;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class OrdersView extends View implements view {
    EnumSet<OrdersViewValidCommands> validCommands;

    public OrdersView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(OrdersViewValidCommands.class);
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (OrdersViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    protected void rateTheProductWithItsId(Matcher matcher) {

    }

    protected void showOrdersWithIdToBuyer(Matcher matcher) {

    }
}
