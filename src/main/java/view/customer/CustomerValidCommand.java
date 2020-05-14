package view.customer;

import view.ViewManager;
import view.customer.orders.OrdersIView;
import view.View;
import view.seller.SellerAccountIView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CustomerValidCommand {
    EditTheFiled("edit") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.editTheField();
        }
    },

    ViewBalanceToBuyer("view\\s+balance") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.balance();
        }
    },

    ViewCart("view\\s+cart") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.cart();
        }
    },
    ViewDiscountCodesToBuyer("view\\s+discount\\s+codes") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.promoCodes();

        }
    },
    ViewOrdersForBuyer("view\\s+orders") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.orders();
        }
    },
    ViewPersonalInfo("view\\s+personal\\s+info") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.viewPersonalInfo();
        }
    },
    Sorting("sorting") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.sorting();
        }
    },
    Filtering("filtering") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.filtering();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(CustomerIView page) {
            page.help();
        }
    },
    Logout("logout"){
        @Override
        public void goToFunction(CustomerIView page) {
            page.logOut();
        }
    };
    private final Pattern commandPattern;
    private final String value;

    CustomerValidCommand(String output) {
        this.commandPattern = Pattern.compile(output);
        this.value = output;
    }

    public abstract void goToFunction(CustomerIView page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }


    @Override
    public String toString() {
        return this.value;
    }
}
