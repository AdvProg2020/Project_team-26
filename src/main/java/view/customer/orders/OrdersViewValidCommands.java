package view.customer.orders;

import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum OrdersViewValidCommands {
    ShowOrdersWithIdToBuyer("show\\s+order\\s+(.*)"),
    RateTheProductWithItsId("rate\\s+(.*) ([1-5]{1})");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    OrdersViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
