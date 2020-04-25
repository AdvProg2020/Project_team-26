package view.cart;

import view.View;
import view.main.MainPageView;
import view.products.single.SingleProductView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CartViewValidCommand {
    ShowAllProducts("show\\s+products") {
        @Override
        public void goToFunction(CartView page) {
            page.showAllProducts();
        }
    },
    ShowProductWithId("view\\s+(.*)") {
        @Override
        public void goToFunction(CartView page) {
            page.showProductWithId(Pattern.compile(ShowProductWithId.toString()).matcher(page.getInput()));
        }
    },
    IncreaseNumberOfProductForBuyerWithId("increase\\s+(.*)") {
        @Override
        public void goToFunction(CartView page) {
            page.increaseNumberOfProductForBuyerWithId(Pattern.compile(IncreaseNumberOfProductForBuyerWithId.toString()).matcher(page.getInput()));
        }
    },
    DecreaseNumberOfProductForBuyerWithId("decrease\\s+(.*)") {
        @Override
        public void goToFunction(CartView page) {
            page.decreaseNumberOfProductForBuyerWithId(Pattern.compile(DecreaseNumberOfProductForBuyerWithId.toString()).matcher(page.getInput()));
        }
    },
    ShowTotalPriceToBuyer("show\\s+total\\s+price") {
        @Override
        public void goToFunction(CartView page) {
            page.showTotalPriceToBuyer();
        }
    },
    Purchase("purchase") {
        /**

         */
        @Override
        public void goToFunction(CartView page) {
            page.purchase();
        }
    },
    ;
    private final Pattern commandPattern;
    private CartView function = null;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(CartView page);

    CartViewValidCommand(String output) {
        this.commandPattern = Pattern.compile(output);
    }

}
