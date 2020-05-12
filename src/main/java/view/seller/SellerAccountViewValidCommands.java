package view.seller;

import view.offs.AllOffView;
import view.seller.offs.OffView;
import view.seller.products.ManageProductForSellerView;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SellerAccountViewValidCommands {

    ViewPersonalInfo("view\\s+personal\\s+info") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.viewPersonalInfo();
        }
    },
    EditTheFiled("edit\\s+(.*)") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.edit(Pattern.compile(EditTheFiled.toString()).matcher(page.getInput()));
        }

    },
    ViewCompanyInfo("view\\s+company\\s+information") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.companyInfo();
        }

    },
    ViewSalesHistoryForSeller("view\\s+sales\\s+history") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.history();
        }

    },

    ManageProduct("manage\\s+products") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.manageProducts();
        }

    },
    AddProductForSeller("add\\s+product") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.addProduct();
        }

    },
    RemoveProductWithProductId("remove\\s+product\\s+(.*)") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.removeProduct(Pattern.compile(RemoveProductWithProductId.toString()).matcher(page.getInput()));
        }

    },
    ShowAllCategoriesForSeller("show\\s+categories") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.viewPersonalInfo();
        }

    },
    ViewAllOffsForSeller("view\\s+offs") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.viewPersonalInfo();
        }

    },
    ViewBalanceForSeller("view\\s+balance") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.viewPersonalInfo();
        }

    };
    private final Pattern commandPattern;
    private final String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    SellerAccountViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }

    public abstract void goToFunction(SellerAccountIView page);


    @Override
    public String toString() {
        return value;
    }
}
