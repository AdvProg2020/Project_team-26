package view.cli.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CustomerValidCommand {
    EditTheFiled("^edit$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.editTheField();
        }
    },
    ViewBalanceToBuyer("^view\\s+balance$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.balance();
        }
    },
    ViewCart("^view\\s+cart$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.cart();
        }
    },
    ViewDiscountCodesToBuyer("^view\\s+discount\\s+codes$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.promoCodes();
        }
    },
    ViewOrdersForBuyer("^view\\s+orders$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.orders();
        }
    },
    ViewPersonalInfo("^view\\s+personal\\s+info$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.viewPersonalInfo();
        }
    },
    Sorting("^sorting$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.sorting();
        }
    },
    Help("^help$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.help();
        }
    },
    Logout("^logout$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.logOut();
        }
    },
    ShowProducts("^products$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.product();
        }
    },
    ShowOffs("^offs$") {
        @Override
        public void goToFunction(CustomerView page) {
            page.off();
        }
    };;
    private final Pattern commandPattern;
    private final String value;

    CustomerValidCommand(String output) {
        this.commandPattern = Pattern.compile(output);
        this.value = output;
    }

    public abstract void goToFunction(CustomerView page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }


    @Override
    public String toString() {
        return this.value;
    }
}
