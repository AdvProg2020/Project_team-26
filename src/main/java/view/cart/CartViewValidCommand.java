package view.cart;

import view.main.MainPageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CartViewValidCommand {
    ShowAllProducts("show\\s+products") {
        @Override
        public void goToFunction(CartView page) {
            page.showAllProducts();
        }
    },
    ShowProductWithId("view\\s+(\\d+)") {
        @Override
        public void goToFunction(CartView page) {
            page.showProductWithId(Pattern.compile(ShowProductWithId.toString()).matcher(page.getInput()));
        }
    },
    IncreaseNumberOfProductForBuyerWithId("increase\\s+(\\d+)") {
        @Override
        public void goToFunction(CartView page) {
            page.changeInNumber(Pattern.compile(IncreaseNumberOfProductForBuyerWithId.toString()).matcher(page.getInput()), +1);
        }
    },
    DecreaseNumberOfProductForBuyerWithId("decrease\\s+(\\d+)") {
        @Override
        public void goToFunction(CartView page) {
            page.changeInNumber(Pattern.compile(DecreaseNumberOfProductForBuyerWithId.toString()).matcher(page.getInput()), -1);
        }
    },
    ShowTotalPriceToBuyer("show\\s+total\\s+price") {
        @Override
        public void goToFunction(CartView page) {
            page.showTotalPriceToBuyer();
        }
    },
    Purchase("purchase") {
        @Override
        public void goToFunction(CartView page) {
            page.purchase();
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(CartView page) {
            if (page.getManager().getIsUserLoggedIn()) {
                page.logOut();
                return;
            }
            page.getManager().inputOutput.println("you are not logged in");
        }
    },
    CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)") {
        @Override
        public void goToFunction(CartView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.register();
                return;
            }
            page.getManager().inputOutput.println("first logout");
        }
    },
    LoginAccount("login\\s+(.*)") {
        @Override
        public void goToFunction(CartView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.login();
                return;
            }
            page.getManager().inputOutput.println("first logout");
        }
    },
    Help("help") {
        @Override
        public void goToFunction(CartView page) {
            page.help();
        }
    },
    ShowProducts("products") {
        @Override
        public void goToFunction(CartView page) {
            page.product();
        }
    },
    ShowOffs("offs") {
        @Override
        public void goToFunction(CartView page) {
            page.off();
        }
    };
    private final Pattern commandPattern;
    private String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(CartView page);

    CartViewValidCommand(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
