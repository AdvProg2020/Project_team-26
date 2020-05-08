package view.products.all;

import view.*;
import view.products.all.filter.FilterView;
import view.products.all.sort.SortView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories", null) {////

        @Override
        public void goToFunction(AllProductView page) {
            page.categoriesOfProducts();

        }
    },
    ViewSubCategory("view\\s+sub\\s+(.*)", null) {////

        @Override
        public void goToFunction(AllProductView page) {
            page.showSubcategories();

        }
    },
    Filtering("filtering", new FilterView(AllProductsViewValidCommands.manager)) {
        @Override
        public void goToFunction(AllProductView page) {

        }
    },
    Sorting("sorting", new SortView(AllProductsViewValidCommands.manager)) {
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
            if (page.getManager().getIsUserLoggedin()) {
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

    public abstract void goToFunction(AllProductView page);

    @Override
    public String toString() {
        return this.value;
    }
}

