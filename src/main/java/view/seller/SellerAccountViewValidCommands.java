package view.seller;

import view.seller.offs.offIView;
import view.seller.products.ManageProductForSellerIView;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SellerAccountViewValidCommands {

    ViewPersonalInfo("view\\s+personal\\s+info", null),
    EditTheFiled("edit\\s+(.*)", null),
    ViewCompanyInfo("view\\s+company\\s+information", null),
    ViewSalesHistoryForSeller("view\\s+sales\\s+history", null),

    ManageProduct("manage\\s+products", new ManageProductForSellerIView()),
    AddProductForSeller("add\\s+product", null),
    RemoveProductWithProductId("remove\\s+product\\s+productId", null),
    ShowAllCategoriesForSeller("show\\s+categories", null),
    ViewAllOffsForSeller("view\\s+offs", new offIView()),
    ViewBalanceForSeller("view\\s+balance", null);
    private final Pattern commandPattern;
    private final View view;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    SellerAccountViewValidCommands(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

}
