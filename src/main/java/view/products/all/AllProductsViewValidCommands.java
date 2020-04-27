package view.products.all;

import view.*;
import view.products.all.filter.FilterViewI;
import view.products.all.sort.SortView;

import java.util.regex.Pattern;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories", null) {////

        @Override
        public void goToFunction(AllProductViewI page) {
            page.categoriesOfProducts();

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
            page.showProductsWithId(Pattern.compile(ShowProductsWithId.toString()).matcher(page.getInput()));
        }
    },
    ;
    private final String input;
    private final View view;
    private static ViewManager manager;

    public abstract void goToFunction(AllProductViewI page);

    public static void setManager(ViewManager manager) {
        AllProductsViewValidCommands.manager = manager;
    }

    public View getView() {
        return this.view;
    }

    AllProductsViewValidCommands(String input, View view) {
        this.input = input;
        this.view = view;
    }
}

