package view.terminal.seller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SellerAccountViewValidCommands {

    ViewPersonalInfo("view\\s+personal\\s+info") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.viewPersonalInfo();
        }
    },
    EditTheFiled("edit") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.edit();
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
    RemoveProductWithProductId("remove\\s+product\\s+(\\d+)") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.removeProduct(Pattern.compile(RemoveProductWithProductId.toString()).matcher(page.getInput()));
        }

    },
    ShowAllCategoriesForSeller("show\\s+categories") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.allCategories();
        }

    }, ShowSubCategoriesForSeller("sub (\\d+)") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.subCategory(Pattern.compile(ShowSubCategoriesForSeller.toString()).matcher(page.getInput()));
        }

    },
    ViewAllOffsForSeller("view\\s+offs") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.viewPersonalInfo();
        }

    }, Logout("logout") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.logOut();
        }
    },
    ViewBalanceForSeller("view\\s+balance") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.balance();
        }

    },
    Help("help") {
        @Override
        public void goToFunction(SellerAccountIView page) {
            page.help();
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
