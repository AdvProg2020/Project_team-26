package view.seller.products;

import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageProductForSellerViewValidCommands {
    ViewProductWithId("show\\s+product\\s+(.*)"),
    ViewBuyersOfProductWithId("view\\s+buyers\\s+(.*)"),
    EditProductWithId("edit\\s+(.*)"),
    AddProduct(" "),
    RemoveProduct(" "),
    ShowAllProduct(" ");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ManageProductForSellerViewValidCommands(String input) {
        commandPattern = Pattern.compile(input);
    }

}
