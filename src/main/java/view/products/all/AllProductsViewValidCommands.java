package view.products.all;

import view.filterAndSort.*;

import view.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories", null) {////

        @Override
        public void goToFunction(AllProductView page) {
            page.categoriesOfProducts();

        }
    },
    ViewSubCategory("sub\\s+(.*)", null) {////

        @Override
        public void goToFunction(AllProductView page) {
            page.subcategory(Pattern.compile(ViewSubCategory.toString()).matcher(page.getInput()));
        }
    },
    Filtering("filtering", AllProductsViewValidCommands.getFilterAndSort()) {
        @Override
        public void goToFunction(AllProductView page) {

        }
    },
    Sorting("sorting", AllProductsViewValidCommands.getFilterAndSort()) {
        @Override
        public void goToFunction(AllProductView page) {

        }
    },
    ShowAllProducts("show\\s+products", null) {
        @Override
        public void goToFunction(AllProductView page) {
            page.showAllProducts();
        }
    },
    ShowProductsWithId("show\\s+product\\s+(.*)", null) {
        @Override
        public void goToFunction(AllProductView page) {
            page.singleProductView(Pattern.compile(ShowProductsWithId.toString()).matcher(page.getInput()));
        }
    },
    Logout("logout", null) {
        @Override
        public void goToFunction(AllProductView page) {
            if (page.getManager().getIsUserLoggedIn()) {
                page.logOut();
                return;
            }
            page.printError();
        }
    };
    private final Pattern commandPattern;
    private final View view;
    private final String value;
    private static ViewManager manager;
    private static FilterAndSort filterAndSort;

    public void setManager(ViewManager manager) {
        AllProductsViewValidCommands.manager = manager;
    }

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    public View getView() {
        return this.view;
    }


    AllProductsViewValidCommands(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
        this.value = output;
    }

    public void setFiltering(FilterAndSort filtering) {
        AllProductsViewValidCommands.filterAndSort = filtering;
    }

    public static FilterAndSort getFilterAndSort() {
        return filterAndSort;
    }

    public static ViewManager getManager() {
        return manager;
    }

    public abstract void goToFunction(AllProductView page);

    @Override
    public String toString() {
        return this.value;
    }
}

