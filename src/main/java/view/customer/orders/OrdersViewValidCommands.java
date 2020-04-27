package view.customer.orders;

import view.View;
import view.cart.CartViewI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum OrdersViewValidCommands {
    ShowOrdersWithIdToBuyer("show\\s+order\\s+(.*)") {
        @Override
        public void goToFunction(OrdersViewI page) {
            page.showOrdersWithIdToBuyer(Pattern.compile(ShowOrdersWithIdToBuyer.toString()).matcher(page.getInput()));
        }
    },
    RateTheProductWithItsId("rate\\s+(.*) ([1-5]{1})") {
        @Override
        public void goToFunction(OrdersViewI page) {
            page.rateTheProductWithItsId(Pattern.compile(RateTheProductWithItsId.toString()).matcher(page.getInput()));
        }
    };
    private final Pattern commandPattern;
    private CartViewI function = null;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(OrdersViewI page);

    OrdersViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
