package view.products.single;

import controller.interfaces.cart.ICartController;
import model.Product;
import view.*;
import view.main.AuthenticationView;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleProductView extends View {
    EnumSet<SingleProductViewValidCommands> validCommands;
    private Product product;
    private ICartController cartController;

    public SingleProductView(ViewManager manager, Product product) {
        super(manager);
        validCommands = EnumSet.allOf(SingleProductViewValidCommands.class);
        this.product = product;
    }

    @Override
    public View run() {
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            for (SingleProductViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                }
            }
        }
        return null;
    }

    protected void addToTheCart() {

    }

    protected String selectAUserForBuyingFrom() {
        manager.inputOutput.println("which seller you want to buy");
        return manager.inputOutput.nextLine();
    }

    protected void attributeOfProduct() {
    }

    protected void compareToProductWithId(Matcher matcher) {

    }

    protected void commentsForThisProduct() {
        manager.inputOutput.println("title");
        String[] comment = new String[2];
        comment[0] = manager.inputOutput.nextLine();
        manager.inputOutput.println("comments");
        comment[1] = manager.inputOutput.nextLine();
    }

    protected void digest() {
        manager.inputOutput.println("name is:" + product.getName());
        manager.inputOutput.println("product is:" + product);
        manager.inputOutput.println("brand is:" + product.getBrand());
        manager.inputOutput.println("this product:" + product.getDescription());
        // TODO: manager.inputOutput.println("Category is:" + product.get());
        // TODO: manager.inputOutput.println("rate is:" + controller.getA(product, manager.getTocken()));
        // TODO: manager.showList(product.getSellerList(product, manager.getTocken()));
        // TODO: manager.showList(controller.getComments(product, manager.getTocken()));
    }

    protected void changeInfo(Matcher matcher, boolean isItManager) {


    }
}
