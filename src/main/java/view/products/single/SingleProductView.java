package view.products.single;

import controller.interfaces.cart.ICartController;
import controller.interfaces.product.IProductController;
import controller.interfaces.review.ICommentController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotEnoughProductsException;
import model.Product;
import model.ProductSeller;
import view.*;
import view.offs.AllOffView;
import view.products.all.AllProductView;

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
        manager.inputOutput.println("product menu :");
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
        while (true) {
            manager.inputOutput.println("enter amount you want or back");
            String amountInput = manager.inputOutput.nextLine();
            if (amountInput.equalsIgnoreCase("back"))
                return 0;
            if (manager.checkTheInputIsIntegerOrLong(amountInput, false)) {
                if (Integer.parseInt(amountInput) > 0)
                    return Integer.parseInt(amountInput);
                manager.inputOutput.println("enter positive number");
            }
        }
    }

    private int getSellerId(List<ProductSeller> productSellerList) {
        Map<Integer, ProductSeller> ids = new HashMap<>();
        int numbers = 1;
        for (ProductSeller productSeller : productSellerList) {
            ids.put(numbers, productSeller);
            manager.inputOutput.println(numbers + "." + "the product seller by name " + productSeller.getSeller().getFullName()
                    + " with price " + productSeller.getPrice());
            numbers++;
        }
        manager.inputOutput.println("enter the number of each you want to buy from or back");
        while (true) {
            String chose = manager.inputOutput.nextLine();
            if (chose.equalsIgnoreCase("back"))
                return 0;
            if (manager.checkTheInputIsIntegerOrLong(chose, false)) {
                numbers = Integer.parseInt(chose);
                if (numbers < ids.size() && numbers > 0) {
                    return ids.get(numbers).getId();
                }
                manager.inputOutput.println("choose number that exist in list");
            } else
                manager.inputOutput.println("choose number please");
        }
    }

    public void attributeOfProduct() {
        digest();
        showSellers(product.getSellerList());
        commentsForThisProduct();
    }

    protected void compareToProductWithId(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1), false)) {
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
        manager.inputOutput.println("name :" + product.getName() + " <--> " + compare.getName());
        manager.inputOutput.println("brand :" + product.getBrand() + " <--> " + compare.getBrand());
        manager.inputOutput.println("rate :" + product.getAverageRate() + " <--> " + compare.getAverageRate());
        manager.inputOutput.println("description  :" + product.getDescription() + " <--> " + compare.getDescription());
        manager.inputOutput.println("category :" + product.getCategory().getName() + " <--> " + compare.getCategory().getName());
        manager.inputOutput.println("minimum price :" + product.getCategory().getName() + " <--> " + compare.getCategory().getName());
        manager.inputOutput.println("the sellers of" + product.getName() + " are :");
        showSellers(product.getSellerList());
        manager.inputOutput.println("<--->\nthe sellers of" + compare.getName() + " are :");
        showSellers(compare.getSellerList());
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
        manager.inputOutput.println("name is:" + (product.getName() == null ? "" : product.getName()));
        manager.inputOutput.println("brand is:" + (product.getBrand() == null ? "" : product.getBrand()));
        manager.inputOutput.println("this product:" + (product.getDescription() == null ? "" : product.getName()));
        manager.inputOutput.println("minimum price :" + (product.getMinimumPrice()));
        manager.inputOutput.println("the average rate is :" + product.getAverageRate());
        manager.inputOutput.println("Category is: " + (product.getCategory().getName() == null ? "" : product.getCategory().getName()
                + " with id : " + product.getCategory().getId()));
    }

    private void showSellers(List<ProductSeller> productSellerList) {
        productSellerList.forEach(productSeller -> manager.inputOutput.println("seller name :" + productSeller.getSeller().getUsername()
                + " with id : " + productSeller.getSeller().getId() + "\nwith total amount of :" + productSeller.getRemainingItems()
                + "\nwith actual price : " + productSeller.getPrice() + " with price by existing off " + productSeller.getPriceInOff()));
    }


    protected void product() {
        AllProductView allProductView = new AllProductView(manager);
        allProductView.run();
    }

    protected void off() {
        AllOffView allOffView = new AllOffView(manager);
        allOffView.run();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("offs");
        commandList.add("products");
        commandList.add("digest");
        commandList.add("add to cart");
        commandList.add("attributes");
        commandList.add("compare [productID]");
        commandList.add("Comments");
        commandList.add("add comment");
        if (manager.getIsUserLoggedIn()) {
            commandList.add("logout");
        } else {
            commandList.add("login [username]");
            commandList.add("create account [manager|buyer|seller] [username]");
        }
        commandList.forEach(i -> manager.inputOutput.println(i));
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

}
