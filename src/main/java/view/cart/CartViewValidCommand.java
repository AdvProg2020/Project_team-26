package view.cart;

import view.View;
import view.products.single.SingleProductView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CartViewValidCommand {
    ShowAllProducts("show\\s+products"),
    ShowProductWithId("view\\s+(.*)"),
    IncreaseNumberOfProductForBuyerWithId("increase\\s+(.*)"),
    DecreaseNumberOfProductForBuyerWithId("decrease\\s+(.*)"),
    ShowTotalPriceToBuyer("show\\s+total\\s+price"),
    Purchase("purchase"),
    ;
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    CartViewValidCommand(String output) {
        this.commandPattern = Pattern.compile(output);
    }

}
