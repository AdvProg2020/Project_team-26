package view.customer.orders;

import view.cart.CartIView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum OrdersViewValidCommands {
    ShowOrdersWithIdToBuyer("show\\s+order\\s+(\\d+)") {
        @Override
        public void goToFunction(OrdersIView page) {
            page.showOrdersWithIdToBuyer(Pattern.compile(ShowOrdersWithIdToBuyer.toString()).matcher(page.getInput()));
        }
    },
    RateTheProductWithItsId("rate\\s+(\\d+) ([1-5]{1})") {
        @Override
        public void goToFunction(OrdersIView page) {
            page.rateTheProductWithItsId(Pattern.compile(RateTheProductWithItsId.toString()).matcher(page.getInput()));
        }
    };
    private final Pattern commandPattern;
    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(OrdersIView page);

    OrdersViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
