package view.products.single;

import interfaces.cart.ICartController;
import interfaces.product.IProductController;
import interfaces.review.ICommentController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotEnoughProductsException;
import model.Product;
import model.ProductSeller;
import view.*;
import view.offs.AllOffView;

import java.util.*;
import java.util.regex.Matcher;

public class SingleProductView extends View {
    private EnumSet<SingleProductViewValidCommands> validCommands;
    private Product product;
    private ICartController cartController;
    private ICommentController commentController;
    private IProductController productController;

    public SingleProductView(ViewManager manager, Product product) {
        super(manager);
        validCommands = EnumSet.allOf(SingleProductViewValidCommands.class);
        this.product = product;
        cartController = (ICartController) manager.getController(ControllerContainer.Controller.CartController);
        commentController = (ICommentController) manager.getController(ControllerContainer.Controller.CommentController);
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
    }

    @Override
    public void run() {
        boolean isDone = false;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            for (SingleProductViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("enter valid commands");
        }
    }

    protected void addToTheCart() {
        int amount = getAmountOfProduct();
        if (amount != 0) {
            int sellerId = getSellerId(product.getSellerList());
            while (sellerId != 0 && amount != 0) {
                try {
                    cartController.addOrChangeProduct(sellerId, amount, manager.getToken());
                    return;
                } catch (InvalidIdException e) {
                    manager.inputOutput.println(e.getMessage());
                    sellerId = getSellerId(product.getSellerList());
                } catch (NotEnoughProductsException e) {
                    manager.inputOutput.println(e.getMessage());
                    amount = getAmountOfProduct();
                } catch (InvalidTokenException e) {
                    manager.inputOutput.println(e.getMessage());
                    return;
                }
            }
        }
    }


    private int getAmountOfProduct() {
        int amount = 0;
        while (amount <= 0) {
            manager.inputOutput.println("enter amount you want or back");
            String amountInput = manager.inputOutput.nextLine();
            if (amountInput.equalsIgnoreCase("back"))
                return 0;
            if (manager.checkTheInputIsInteger(amountInput)) {
                amount = Integer.parseInt(amountInput);
            }
            manager.inputOutput.println("you should enter positive number.");
        }
        return amount;
    }

    private int getSellerId(List<ProductSeller> productSellerList) {
        Map<Integer, ProductSeller> ids = new HashMap<>();
        int numbers = 1;
        for (ProductSeller productSeller : productSellerList) {
            ids.put(numbers, productSeller);
            manager.inputOutput.println("the product seller by name " + productSeller.getSeller().getFullName()
                    + " with price " + productSeller.getPrice());
            numbers++;
        }
        manager.inputOutput.println("enter the number of each you want to buy from");
        while (true) {
            String chose = manager.inputOutput.nextLine();
            if (chose.equalsIgnoreCase("back"))
                return 0;
            if (manager.checkTheInputIsInteger(chose)) {
                numbers = Integer.parseInt(chose);
                if (numbers < ids.size() && numbers > 0) {
                    return ids.get(numbers).getId();
                }
                manager.inputOutput.println("choose number that exist in list");
            } else
                manager.inputOutput.println("choose number please");
        }
    }

    protected void attributeOfProduct() {
        digest();
        //todo
        //what do you mean all of information
    }

    protected void compareToProductWithId(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsInteger(matcher.group(1))) {
            try {
                Product compareProduct = productController.getProductById(Integer.parseInt(matcher.group(1)), manager.getToken());
                showCompare(compareProduct);
            } catch (InvalidIdException e) {
                manager.inputOutput.println(e.getMessage());
            }
            return;
        }
        manager.inputOutput.println("the id is invalid format.");
    }

    private void showCompare(Product compare) {
        manager.inputOutput.println("name :" + product.getName() + " <-> " + compare.getName());
        manager.inputOutput.println("brand :" + product.getBrand() + " <-> " + compare.getBrand());
        manager.inputOutput.println("price :" + product.getMinimumPrice() + " <-> " + compare.getMinimumPrice());
        manager.inputOutput.println("rate :" + product.getAverageRate() + " <-> " + compare.getAverageRate());
        manager.inputOutput.println("category :" + product.getCategory().getName() + " <-> " + compare.getCategory().getName());
    }

    protected void commentsForThisProduct() {
        product.getComments().forEach(
                comment -> manager.inputOutput.println(comment.getCustomer().getFullName() + " has said :" + comment.getText()));
    }

    protected void addComment() {
        manager.inputOutput.println("enter title");
        String title = manager.inputOutput.nextLine();
        manager.inputOutput.println("enter text");
        String text = manager.inputOutput.nextLine();
        try {
            commentController.addAComment(text, title, product.getId(), manager.getToken());
        } catch (NoAccessException | InvalidTokenException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    protected void digest() {
        manager.inputOutput.println("name is:" + product.getName());
        manager.inputOutput.println("brand is:" + product.getBrand());
        manager.inputOutput.println("this product:" + product.getDescription());
        manager.inputOutput.println("minimum price :" + product.getMinimumPrice());
        manager.inputOutput.println("the average rate is :" + product.getAverageRate());
        manager.inputOutput.println("Category is: with name: " + product.getCategory().getName()
                + " with id : " + product.getCategory().getId());
        manager.inputOutput.println("the sellers are :");
        showSellers(product.getSellerList());
    }

    private void showSellers(List<ProductSeller> productSellerList) {
        productSellerList.forEach(productSeller -> manager.inputOutput.println("seller name :" + productSeller.getSeller().getFullName()
                + " with id : " + productSeller.getSeller().getId() + "\nwith total amount of :" + productSeller.getRemainingItems() + " with price by existing off " + productSeller.getPriceInOff()));
    }
    protected void offs(){
        AllOffView allOffView = new AllOffView(manager);
        allOffView.run();
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void login() {
        manager.loginInAllPagesOptional(super.input);
    }

    protected void register() {
        manager.registerInAllPagesOptional(super.input);
    }

    protected void help() {
        validCommands.forEach(i -> manager.inputOutput.println(i.toString()));
    }
}
