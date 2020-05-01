package view.products.all;

import view.*;
import view.products.all.filter.FilterIView;
import view.products.all.sort.SortView;

import java.util.regex.Pattern;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories", null) {////

        @Override
        public void goToFunction(AllProductIView page) {
            page.categoriesOfProducts();

        }
    },
    Filtering("filtering", new FilterIView(AllProductsViewValidCommands.manager)) {
        @Override
        public void goToFunction(AllProductIView page) {

        }
    },
    Sorting("sorting", new SortView(AllProductsViewValidCommands.manager)) {
        @Override
        public void goToFunction(AllProductIView page) {

        }
    },
    ShowAllProducts("show\\s+products", null) {
        @Override
        public void goToFunction(AllProductIView page) {
            page.showAllProducts();
        }
    },
    ShowProductsWithId("show\\s+product\\s+(.*)", null) {
        @Override
        public void goToFunction(AllProductIView page) {
            page.showProductsWithId(Pattern.compile(ShowProductsWithId.toString()).matcher(page.getInput()));
        }
    },
    ;
    private final String input;
    private final View view;
    private static ViewManager manager;

    public abstract void goToFunction(AllProductIView page);

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

