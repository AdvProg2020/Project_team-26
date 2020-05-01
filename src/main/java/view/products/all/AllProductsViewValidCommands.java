package view.products.all;

import view.*;
import view.main.MainPageView;
import view.manager.ManagerAccountView;
import view.manager.ValidCommandsForManagerAccount;
import view.products.all.filter.FilterViewI;
import view.products.all.sort.SortView;
import view.products.single.SingleProductViewI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories", null) {////

        @Override
        public void goToFunction(AllProductViewI page) {
            page.categoriesOfProducts();

        }
    },
    ViewSubCategory("view\\s+sub\\s+(.*)", null) {////

        @Override
        public void goToFunction(AllProductViewI page) {
            page.showSubcategories();

        }
    },
    Filtering("filtering", new FilterViewI(AllProductsViewValidCommands.manager)) {
        @Override
        public void goToFunction(AllProductViewI page) {

        }
    },
    Sorting("sorting", new SortView(AllProductsViewValidCommands.manager)) {
        @Override
        public void goToFunction(AllProductViewI page) {

        }
    },
    ShowAllProducts("show\\s+products", null) {
        @Override
        public void goToFunction(AllProductViewI page) {
            page.showAllProducts();
        }
    },
    ShowProductsWithId("show\\s+product\\s+(.*)", null) {
        @Override
        public void goToFunction(AllProductViewI page) {
            page.singleProductView(Pattern.compile(ShowProductsWithId.toString()).matcher(page.getInput()));
        }
    },
    Logout("logout", null) {
        @Override
        public void goToFunction(AllProductViewI page) {
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

    public abstract void goToFunction(AllProductViewI page);

    @Override
    public String toString() {
        return this.value;
    }
}

