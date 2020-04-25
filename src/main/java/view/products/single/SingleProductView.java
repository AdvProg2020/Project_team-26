package view.products.single;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class SingleProductView extends View implements view {
    EnumSet<SingleProductViewValidCommands> validCommands;

    public SingleProductView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(SingleProductViewValidCommands.class);
    }

    @Override
    public View run() {
        return null;
    }

    protected void addToTheCart() {

    }

    protected void selectAUserForBuyingFrom(Matcher matcher) {

    }

    protected void attributeOfProduct() {

    }

    protected void compareToProductWithId(Matcher matcher) {

    }

    protected void commentsForThisProduct() {

    }

    protected void offs() {

    }

    protected void showProductInOffPage(Matcher matcher) {

    }

    protected void digest() {

    }

    protected void changeInfo(Matcher matcher, boolean isItManager) {

    }
}
