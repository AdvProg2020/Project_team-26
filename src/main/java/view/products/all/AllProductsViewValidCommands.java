package view.products.all;

import model.ShipmentState;
import view.*;
import view.products.all.filter.FilterView;
import view.products.all.sort.SortView;
import view.products.single.SingleProductView;

import java.util.regex.Pattern;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories", null) {////

        @Override
        public void goToFunction(AllProductView page) {
            page.categoriesOfProducts();

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
            page.showProductsWithId(Pattern.compile(ShowProductsWithId.toString()).matcher(page.getInput()));
        }
    },
    ;
    private final String input;
    private final View view;
    private static ViewManager manager;

    public abstract void goToFunction(AllProductView page);

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

