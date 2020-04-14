package view.products.all;

import view.*;
import view.offs.filter.FilterView;
import view.products.all.sort.SortView;
import view.products.single.SingleProductView;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts("view\\s+categories", null),
    Filtering("filtering", new view.products.all.filter.FilterView()),
    Sorting("sorting", new SortView()),
    ShowAllProducts("show\\s+products", null),
    ShowProductsWithId("show\\s+product\\s+(.*)", null),
    ;
    private final String input;
    private final View view;

    AllProductsViewValidCommands(String input, View view) {
        this.input = input;
        this.view = view;
    }
}

