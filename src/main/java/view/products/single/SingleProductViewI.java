package view.products.single;

import controller.account.AuthenticationController;
import controller.interfaces.cart.ICartController;
import controller.interfaces.product.IShowProductController;
import controller.interfaces.review.ICommentController;
import controller.review.CommentController;
import model.Product;
import view.*;
import view.main.AuthenticationView;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleProductViewI extends View {
    EnumSet<SingleProductViewValidCommands> validCommands;
    private Product product;
    private ICartController cartController;
    IShowProductController controller;

    public SingleProductViewI(ViewManager manager, Product product) {
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
        if (!manager.getIsUserLoggedin()) {
            manager.inputOutput.println("you are not logged in please enter your username");
            String username = manager.inputOutput.nextLine();
            if (username.matches("back"))
                return;
            new AuthenticationView(manager, username, new AuthenticationController()).login(Pattern.compile("(.*)").matcher(username));
        }
        try {
            String sellerId = selectAUserForBuyingFrom();
            if (sellerId.matches("back"))
                return;
            cartController.addProduct(String.valueOf(product), manager.getTocken());
        } catch (Exceptions.TheParameterDoesNOtExist | Exceptions.InvalidFiledException e) {
            e.getMessage();
        }
    }

    protected String selectAUserForBuyingFrom() {
        manager.inputOutput.println("which seller you want to buy");
        manager.showList(controller.getSellers(product, manager.getTocken()));
        return manager.inputOutput.nextLine();
    }

    protected void attributeOfProduct() {
        manager.inputOutput.println(controller.getAttribute(product, manager.getTocken()));
    }

    protected void compareToProductWithId(Matcher matcher) {
        ICompareTwoItems compareController = new CompareTwoItems();
        String compare = compareController.compareItems(String.valueOf(product.getId()), matcher.group(1), manager.getTocken());
        manager.inputOutput.println(compare);
    }

    protected void commentsForThisProduct() {
        ICommentController commentController = new CommentController();
        manager.inputOutput.println("title");
        String[] comment = new String[2];
        comment[0] = manager.inputOutput.nextLine();
        manager.inputOutput.println("comments");
        comment[1] = manager.inputOutput.nextLine();
        commentController.addAComment(comment, product.getId(), manager.getTocken());
    }

    protected void digest() {
        manager.inputOutput.println("name is:" + product.getName());
        manager.inputOutput.println("product is:" + product);
        manager.inputOutput.println("brand is:" + product.getBrand();
        manager.inputOutput.println("this product:" + product.getDescription();
        manager.inputOutput.println("Category is:" + product.get();
        manager.inputOutput.println("rate is:" + controller.getA(product, manager.getTocken()));
        manager.showList(product.getSellerList(product, manager.getTocken()));
        manager.showList(controller.getComments(product, manager.getTocken()));
    }

    protected void changeInfo(Matcher matcher, boolean isItManager) {


    }
}
