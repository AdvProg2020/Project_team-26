package view.cart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CartViewValidCommand {
    ShowAllProducts("show\\s+products") {
        @Override
        public void goToFunction(CartIView page) {
            page.showAllProducts();
        }
    },
    ShowProductWithId("view\\s+(.*)") {
        @Override
        public void goToFunction(CartIView page) {
            page.showProductWithId(Pattern.compile(ShowProductWithId.toString()).matcher(page.getInput()));
        }
    },
    IncreaseNumberOfProductForBuyerWithId("increase\\s+(.*)") {
        @Override
        public void goToFunction(CartIView page) {
            page.changeInNumber(Pattern.compile(IncreaseNumberOfProductForBuyerWithId.toString()).matcher(page.getInput()), +1);
        }
    },
    DecreaseNumberOfProductForBuyerWithId("decrease\\s+(.*)") {
        @Override
        public void goToFunction(CartIView page) {
            page.changeInNumber(Pattern.compile(DecreaseNumberOfProductForBuyerWithId.toString()).matcher(page.getInput()), -1);
        }
    },
    ShowTotalPriceToBuyer("show\\s+total\\s+price") {
        @Override
        public void goToFunction(CartIView page) {
            page.showTotalPriceToBuyer();
        }
    },
    Purchase("purchase") {
        @Override
        public void goToFunction(CartIView page) {
            page.purchase();
        }
    }, Logout("logout") {
        @Override
        public void goToFunction(CartIView page) {
            page.logOut();
        }
    }, CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)") {
        @Override
        public void goToFunction(CartIView page) {
            page.register();
        }
    },
    LoginAccount("login\\s+(.*)") {
        @Override
        public void goToFunction(CartIView page) {
            page.login();
        }
    };
    private final Pattern commandPattern;
    private String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(CartIView page);

    CartViewValidCommand(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
