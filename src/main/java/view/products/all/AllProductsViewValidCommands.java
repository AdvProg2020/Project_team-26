package view.products.all;

import view.filterAndSort.*;

import view.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories") {////

        @Override
        public void goToFunction(AllProductView page) {
            page.categoriesOfProducts();

        }
    },
    ViewSubCategory("sub\\s+(.*)") {////

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
    ShowProductsWithId("show\\s+product\\s+(.*)") {
        @Override
        public void goToFunction(AllProductView page) {
            page.singleProductView(Pattern.compile(ShowProductsWithId.toString()).matcher(page.getInput()));
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(AllProductView page) {
            page.logOut();
        }
    }, CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)") {
        @Override
        public void goToFunction(AllProductView page) {
            page.register();
        }
    },
    LoginAccount("login\\s+(.*)") {
        @Override
        public void goToFunction(AllProductView page) {
            page.login();
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

