package view.seller.products;

import view.View;
import view.seller.SellerAccountIView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageProductForSellerViewValidCommands {
    ViewProductWithId("show\\s+product\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageProductForSellerView page) {
            page.showWithId(Pattern.compile(ViewProductWithId.toString()).matcher(page.getInput()));
        }
    },
    ViewBuyersOfProductWithId("view\\s+buyers\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageProductForSellerView page) {
            page.showBuyer(Pattern.compile(ViewBuyersOfProductWithId.toString()).matcher(page.getInput()));
        }
    },
    EditProductWithId("edit") {
        @Override
        public void goToFunction(ManageProductForSellerView page) {
            page.edit();
        }
    },
    ShowAllProduct("show all") {
        @Override
        public void goToFunction(ManageProductForSellerView page) {
            page.showAll();
        }
    }, Logout("logout") {
        @Override
        public void goToFunction(ManageProductForSellerView page) {
            page.logOut();
        }
    },
    Sorting("sorting") {
        @Override
        public void goToFunction(ManageProductForSellerView page) {
            page.sorting();
        }
    },
    Filtering("filtering") {
        @Override
        public void goToFunction(ManageProductForSellerView page) {
            page.filtering();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(ManageProductForSellerView page) {
            page.help();
        }
    };
    private final Pattern commandPattern;

    public abstract void goToFunction(ManageProductForSellerView page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ManageProductForSellerViewValidCommands(String input) {
        commandPattern = Pattern.compile(input);
    }


}
