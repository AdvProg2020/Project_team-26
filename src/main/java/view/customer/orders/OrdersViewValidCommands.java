package view.customer.orders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum OrdersViewValidCommands {
    ShowOrdersWithIdToBuyer("show\\s+order\\s+(\\d+)") {
        @Override
        public void goToFunction(OrdersIView page) {
            page.showOrdersWithIdToBuyer(Pattern.compile(ShowOrdersWithIdToBuyer.toString()).matcher(page.getInput()));
        }
    },
    RateTheProductWithItsId("rate\\s+(\\d+)\\s+([1-5]{1})") {
        @Override
        public void goToFunction(OrdersIView page) {
            page.rateTheProductWithItsId(Pattern.compile(RateTheProductWithItsId.toString()).matcher(page.getInput()));
        }
    }, Sorting("sorting") {
        @Override
        public void goToFunction(OrdersIView page) {
            page.sorting();
        }
    }, Filtering("filtering") {
        @Override
        public void goToFunction(OrdersIView page) {
            page.filtering();
        }
    }, Help("help") {
        @Override
        public void goToFunction(OrdersIView page) {
            page.help();
        }
    };
    private final Pattern commandPattern;
    private final String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }


    public abstract void goToFunction(OrdersIView page);

    OrdersViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }

    @Override
    public String toString() {
        return value;
    }
}
