package view.customer.orders;

import view.View;
import view.cart.CartView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum OrdersViewValidCommands {
    ShowOrdersWithIdToBuyer("show\\s+order\\s+(.*)") {
        @Override
        public void goToFunction(OrdersView page) {
            page.showOrdersWithIdToBuyer(Pattern.compile(ShowOrdersWithIdToBuyer.toString()).matcher(page.getInput()));
        }
    },
    RateTheProductWithItsId("rate\\s+(.*) ([1-5]{1})") {
        @Override
        public void goToFunction(OrdersView page) {
            page.rateTheProductWithItsId(Pattern.compile(RateTheProductWithItsId.toString()).matcher(page.getInput()));
        }
    };
    private final Pattern commandPattern;
    private CartView function = null;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(OrdersView page);

    OrdersViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
