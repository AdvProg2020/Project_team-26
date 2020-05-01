package view.products.single;

import model.Comment;
import model.Product;
import model.ProductSeller;
import model.Seller;
import view.*;

import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class SingleProductViewI extends View implements ViewI {
    EnumSet<SingleProductViewValidCommands> validCommands;
    private Product product;

    public SingleProductViewI(ViewManager manager, Product product) {
        super(manager);
        validCommands = EnumSet.allOf(SingleProductViewValidCommands.class);
        this.product = product;
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
        manager.inputOutput.println("name is:" + product.getName());
        manager.inputOutput.println("ID is:" + product.getId());
        manager.inputOutput.println("brand is:" + product.getBrand());
        manager.inputOutput.println("this product:" + product.getDescription());
        manager.inputOutput.println("Category is:" + product.getCategory().getCategoriesAndSub());
        manager.inputOutput.println("name is:" + product.getAverageRate());
        showSellers(product.getSellerList());
        showComments(product.getComments());
    }

    private void showComments(List<Comment> commentList) {
        int count = 0;
        for (Comment comment : commentList) {
            manager.inputOutput.println(comment.getCustomer().getFullName() + " has said:" + comment.getText());
            count++;
            if (count >= 4)
                return;
        }
    }

    private void showSellers(List<ProductSeller> sellers) {
        manager.inputOutput.println("Sellers are : ");
        for (ProductSeller seller : sellers) {
            manager.inputOutput.println(seller.getSeller().getFullName() + " with price" +
                    " :" + seller.getPrice()" with " + seller.getRemainingItems() + "items");
        }
    }

    protected void changeInfo(Matcher matcher, boolean isItManager) {

    }
}
