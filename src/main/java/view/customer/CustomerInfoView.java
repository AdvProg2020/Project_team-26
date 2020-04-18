package view.customer;

import view.customer.orders.OrdersView;
import view.View;
import view.cart.CartView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CustomerInfoView {
    EditTheFiled("edit\\s+(.*)", null),

    ViewCart("view\\s+cart", null),

    ViewOrdersForBuyer("view\\s+orders", new OrdersView()),
    ViewBalanceToBuyer("view\\s+balance", null),
    ViewDiscountCodesToBuyer("view\\s+discount\\s+codes", null);
    private final Pattern commandPattern;
    private final View view;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    CustomerInfoView(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

}
