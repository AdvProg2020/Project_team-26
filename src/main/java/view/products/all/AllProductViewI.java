package view.products.all;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class AllProductViewI extends View  implements ViewI {
    EnumSet<AllProductsViewValidCommands> validCommands;

    public AllProductViewI(ViewManager manager) {
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
