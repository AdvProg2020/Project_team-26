package view.customer;

import view.ViewManager;
import view.customer.orders.OrdersView;
import view.View;
import view.main.MainPageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CustomerValidCommand {
    EditTheFiled("edit\\s+(.*)", null) {
        @Override
        public void goToFunction(CustomerView page) {
            page.editTheField(Pattern.compile(EditTheFiled.toString()).matcher(page.getInput()));
        }
    },

    ViewBalanceToBuyer("view\\s+balance", null) {
        @Override
        public void goToFunction(CustomerView page) {
            page.balance();
        }
    },

    ViewCart("view\\s+cart", null) {
        @Override
        public void goToFunction(CustomerView page) {
            page.cart();
        }
    },
    ViewDiscountCodesToBuyer("view\\s+discount\\s+codes", null) {
        @Override
        public void goToFunction(CustomerView page) {
            page.promoCodes();

        }
    },
    ViewOrdersForBuyer("view\\s+orders", new OrdersView(CustomerValidCommand.manager)) {
        @Override
        public void goToFunction(CustomerView page) {
        }
    };
    private Pattern commandPattern;
    private View view;
    private CustomerView function = null;
    public static ViewManager manager;

    CustomerValidCommand(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

    public abstract void goToFunction(CustomerView page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public void setManager(ViewManager manager) {
        CustomerValidCommand.manager = manager;
    }

    public View getView() {
        return view;
    }
}
