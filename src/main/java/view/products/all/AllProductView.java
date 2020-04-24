package view.products.all;

import view.*;
import view.products.all.sort.SortView;

import java.util.EnumSet;

public class AllProductView extends View  implements view{
    EnumSet<AllProductsViewValidCommands> validCommands;

    public AllProductView() {
        validCommands = EnumSet.allOf(AllProductsViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    private void showAllProducts() {

    }

    private void categoriesOfProducts() {

    }

    private void filtering() {

    }

    private void sorting() {

    }

    private void showProductsWithId() {

    }
}
