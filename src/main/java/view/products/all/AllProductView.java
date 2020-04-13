package view.products.all;

import view.*;

import java.util.EnumSet;

public class AllProductView extends View {
    EnumSet<AllProductsViewValidCommands> validCommands;

    public AllProductView() {
        validCommands = EnumSet.allOf(AllProductsViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
