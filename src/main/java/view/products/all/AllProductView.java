package view.products.all;

import view.*;
import view.products.all.sort.SortView;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class AllProductView extends View  implements view{
    EnumSet<AllProductsViewValidCommands> validCommands;

    public AllProductView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(AllProductsViewValidCommands.class);
    }

    @Override
    public View run() {
        return null;
    }

    protected void showAllProducts() {

    }

    protected void categoriesOfProducts() {

    }
    protected void showProductsWithId(Matcher matcher) {

    }
}
