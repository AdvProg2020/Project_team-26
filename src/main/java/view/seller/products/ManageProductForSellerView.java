package view.seller.products;

import exception.*;
import controller.interfaces.category.ICategoryController;
import controller.interfaces.order.IOrderController;
import controller.interfaces.product.IProductController;
import model.Category;
import model.Product;
import model.ProductSeller;
import model.User;
import view.*;
import view.filterAndSort.ProductFilterAndSort;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class ManageProductForSellerView extends View {
    private EnumSet<ManageProductForSellerViewValidCommands> validCommands;
    private IProductController productController;
    private ProductFilterAndSort productFilterAndSort;
    private IOrderController orderController;
    ICategoryController categoryController;
    private User thisUser;


    public ManageProductForSellerView(ViewManager managerView, User thisUser) {
        super(managerView);
        validCommands = EnumSet.allOf(ManageProductForSellerViewValidCommands.class);
        this.thisUser = thisUser;
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
        orderController = (IOrderController) manager.getController(ControllerContainer.Controller.OrderController);
        productFilterAndSort = new ProductFilterAndSort(manager);
    }

    @Override
    public void run() {
        showAll();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (ManageProductForSellerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("invalid input");
        }
    }


    protected void edit(Matcher matcher) {
        Product product = null;
        try {
            product = productController.getProductById(Integer.parseInt(matcher.group(1)), manager.getToken()).clone();
            ProductSeller productSeller = productController.getProductSellerByIdAndSellerId(product.getId(), manager.getToken()).clone();
            String field = "";
            while (!field.matches("back|finish")) {
                manager.inputOutput.println("type field you want to edit,[name,price,description,brand,category,number]");
                field = manager.inputOutput.nextLine();
                switch (field) {
                    case "name":
                        changeStringField(field, product);
                        break;
                    case "description":
                        changeStringField(field, product);
                        break;
                    case "brand":
                        changeStringField(field, product);
                        break;
                    case "number":
                        changeNumber(field, product, productSeller);
                        break;
                    case "category":
                        changeCategory(product);
                        break;
                    case "price":
                        changeNumber(field, product, productSeller);
                        break;
                }
            }
            productController.editProduct(product.getId(), product, manager.getToken());
        } catch (NoObjectIdException | NoAccessException | InvalidIdException | NotSellerException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        }
    }

    private void changeCategory(Product product) {
        String name = manager.inputOutput.nextLine();
        try {
            Category category = categoryController.getCategoryByName(name, manager.getToken());
            product.setCategory(category);
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        }
    }

    private void changeNumber(String field, Product product, ProductSeller productSeller) {
        manager.inputOutput.println("enter replace for : " + field);
        String replace = manager.inputOutput.nextLine();
        if (manager.checkTheInputIsIntegerOrLong(replace, false)) {
            if (product.getSellerList().contains(productSeller))
                product.getSellerList().remove(productSeller);
            else {
                manager.inputOutput.println("you are not this product seller");
                return;
            }
            switch (field) {
                case "number":
                    productSeller.setRemainingItems(Integer.parseInt(replace));
                    break;
                case "price":
                    productSeller.setPrice(Long.parseLong(replace));
            }
            product.getSellerList().add(productSeller);
        }
    }

    private void changeStringField(String field, Product product) {
        manager.inputOutput.println("type replace : " + field);
        String replace = manager.inputOutput.nextLine();
        switch (field) {
            case "name":
                product.setName(replace);
                break;
            case "brand":
                product.setBrand(replace);
                break;
            case "description":
                product.setDescription(replace);
                break;
        }
    }

    protected void showAll() {
        productController.getAllProductWithFilterForSellerId(thisUser.getId(), productFilterAndSort.getFilterForController(),
                productFilterAndSort.getFieldNameForSort(), productFilterAndSort.isAscending()
                , manager.getToken()).forEach(product -> manager.inputOutput.println(
                product.getName() + "with id: " + product.getId() + "in category :" + product.getCategory().getName()));
    }

    protected void showWithId(Matcher matcher) {
        manager.singleProductView(matcher);
    }

    protected void showBuyer(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1), false)) {
            List<User> userList = orderController.getProductBuyerByProductId(Integer.parseInt(matcher.group(1)), manager.getToken());
            userList.forEach(i -> manager.inputOutput.println(i.getFullName()));
            return;
        }
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void viewRequestStatus() {
        //todo
    }

    protected void sorting() {
        productFilterAndSort.run();
    }

    protected void filtering() {
        productFilterAndSort.run();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("view [productId]");
        commandList.add("view buyers [productId]");
        commandList.add("edit [productId]");
        commandList.add("sorting");
        commandList.add("filtering");
        commandList.add("show all");
        commandList.add("logout");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }

}
