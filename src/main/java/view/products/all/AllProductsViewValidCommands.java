package view.products.all;

import view.filterAndSort.*;

import view.*;
import view.main.MainPageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories") {
        @Override
        public void goToFunction(AllProductView page) {
            page.categoriesOfProducts();

        }
    },
    ViewSubCategory("sub\\s+(\\d+)") {
        @Override
        public void goToFunction(AllProductView page) {
            page.subcategory(Pattern.compile(ViewSubCategory.toString()).matcher(page.getInput()));
        }
    },
    Filtering("filtering") {
        @Override
        public void goToFunction(AllProductView page) {
            page.filter();
        }
    },
    Sorting("sorting") {
        @Override
        public void goToFunction(AllProductView page) {
            page.sort();

        }
    },
    ShowAllProducts("show\\s+products") {
        @Override
        public void goToFunction(AllProductView page) {
            page.showAllProducts();
        }
    },
    ShowProductsWithId("show\\s+product\\s+(\\d+)") {
        @Override
        public void goToFunction(AllProductView page) {
            page.singleProductView(Pattern.compile(ShowProductsWithId.toString()).matcher(page.getInput()));
        }
    },
    Help("help") {
        @Override
        public void goToFunction(AllProductView page) {
            page.help();
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(AllProductView page) {
            if (page.getManager().getIsUserLoggedIn()) {
                page.logOut();
                return;
            }
            page.getManager().inputOutput.println("you are not logged in");
        }
    },
    CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)") {
        @Override
        public void goToFunction(AllProductView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.register();
                return;
            }
            page.getManager().inputOutput.println("first logout");
        }
    },
    LoginAccount("login\\s+(.*)") {
        @Override
        public void goToFunction(AllProductView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.login();
                return;
            }
            page.getManager().inputOutput.println("first logout");
        }
    }, ShowOffs("offs") {
        @Override
        public void goToFunction(AllProductView page) {
            page.off();
        }
    };
    private final Pattern commandPattern;
    private final String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    AllProductsViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        this.value = output;
    }

    public abstract void goToFunction(AllProductView page);

    @Override
    public String toString() {
        return this.value;
    }
}

