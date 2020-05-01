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
            page.increaseNumberOfProductForBuyerWithId(Pattern.compile(IncreaseNumberOfProductForBuyerWithId.toString()).matcher(page.getInput()));
        }
    },
    DecreaseNumberOfProductForBuyerWithId("decrease\\s+(.*)") {
        @Override
        public void goToFunction(CartIView page) {
            page.decreaseNumberOfProductForBuyerWithId(Pattern.compile(DecreaseNumberOfProductForBuyerWithId.toString()).matcher(page.getInput()));
        }
    },
    ShowTotalPriceToBuyer("show\\s+total\\s+price") {
        @Override
        public void goToFunction(CartIView page) {
            page.showTotalPriceToBuyer();
        }
    },
    Purchase("purchase") {
        /**

         */
        @Override
        public void goToFunction(CartIView page) {
            page.purchase();
        }
    },
    ;
    private final Pattern commandPattern;
    private CartIView function = null;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(CartIView page);

    CartViewValidCommand(String output) {
        this.commandPattern = Pattern.compile(output);
    }

}
