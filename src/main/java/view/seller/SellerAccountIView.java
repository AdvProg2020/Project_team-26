package view.seller;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import controller.interfaces.category.ICategoryController;
import controller.interfaces.order.IOrderController;
import controller.interfaces.product.IProductController;
import exception.*;
import model.*;
import view.*;
import view.filterAndSort.OrderSort;
import view.seller.offs.ManageOffForSeller;
import view.seller.products.ManageProductForSellerView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;

public class SellerAccountIView extends View {
    private EnumSet<SellerAccountViewValidCommands> validCommands;
    private ArrayList<String> editableFields;
    private IUserInfoController infoController;
    private IShowUserController userController;
    private IProductController productController;
    private ICategoryController categoryController;
    private UserView userView;
    private IOrderController orderController;
    private OrderSort orderSort;
    private User thisUser;


    public SellerAccountIView(ViewManager managerView) {
        super(managerView);
        validCommands = EnumSet.allOf(SellerAccountViewValidCommands.class);
        userView = UserView.getInstance();
        editableFields = new ArrayList<>();
        infoController = (IUserInfoController) manager.getController(ControllerContainer.Controller.UserInfoController);
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
        userController = (IShowUserController) manager.getController(ControllerContainer.Controller.ShowUserController);
        categoryController = (ICategoryController) manager.getController(ControllerContainer.Controller.CategoryController);
        orderController = (IOrderController) manager.getController(ControllerContainer.Controller.OrderController);
        try {
            thisUser = userController.getUserByToken(manager.getToken());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    @Override
    public void run() {
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (SellerAccountViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    private void initialEditFields() {
        userView.initialEditFields(editableFields, manager, userController);
    }

    protected void allCategories() {
        try {
            categoryController.getAllCategories(0, manager.getToken()).forEach(i -> manager.inputOutput.println(i.getName() + " with id " + i.getId()));
        } catch (InvalidIdException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void subCategory(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1))) {
            try {
                categoryController.getAllCategories(Integer.parseInt(matcher.group(1)), manager.getToken()).
                        forEach(i -> manager.inputOutput.println(i.getName() + " with id " + i.getId()));
            } catch (InvalidIdException e) {
                manager.setTokenFromController(e.getMessage());
            }
        }
    }

    protected void companyInfo() {
        try {
            manager.inputOutput.println(infoController.getCompanyName(manager.getToken()));
        } catch (InvalidTokenException | NoSuchField e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        }
    }

    protected void history() {//todo
        try {
            for (Order order : orderController.getOrdersWithFilter(orderSort.getFieldNameForSort(), orderSort.isAscending(), manager.getToken())) {
                manager.inputOutput.println("the user " + order.getCustomer().getFullName() + "with id :" +
                        "at : " + order.getDate().toString());

            }
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }

    }

    protected void manageProducts() {
        ManageProductForSellerView manageProductForSellerView = new ManageProductForSellerView(manager, thisUser);
        manageProductForSellerView.run();
    }

    protected void offs() {
        ManageOffForSeller manageOffForSeller = new ManageOffForSeller(manager, (Seller) thisUser);
        manageOffForSeller.run();
    }

    protected void viewPersonalInfo() {
        userView.viewPersonalInfo(manager, userController);
    }

    protected void edit() {
        initialEditFields();
        userView.edit(editableFields, manager, infoController);
    }


    protected void addProduct() {
        try {
            this.thisUser = userController.getUserByToken(manager.getToken());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
            return;
        }
        Product product = makeProduct();
        ProductSeller productSeller = makeProductSeller();
        try {
            productController.createProduct(product, manager.getToken());
        } catch (ObjectAlreadyExistException e) {
            manager.inputOutput.println(e.getMessage() + "\nif you want to add yourself to it seller type yes or no");
            if (manager.inputOutput.nextLine().matches("yes"))
                addSeller(productSeller, (Product) e.getObject());
        } catch (NotSellerException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    private void addSeller(ProductSeller productSeller, Product product) {
        if (productSeller == null)
            return;
        try {
            productController.addSeller(product.getId(), productSeller, manager.getToken());
        } catch (NotSellerException | NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    private Product makeProduct() {
        Product product = new Product();
        manager.inputOutput.println("enter the name");
        product.setName(manager.inputOutput.nextLine());
        manager.inputOutput.println("enter the brand");
        product.setName(manager.inputOutput.nextLine());
        manager.inputOutput.println("enter the category");
        product.setCategory(setCategory(manager.inputOutput.nextLine()));
        manager.inputOutput.println("enter the attribute");
        product.setDescription(manager.inputOutput.nextLine());
        return product;
    }

    private ProductSeller makeProductSeller() {
        try {
            thisUser = userController.getUserByToken(manager.getToken());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
            return null;
        }
        manager.inputOutput.println("enter the price");
        String price = manager.inputOutput.nextLine();
        manager.inputOutput.println("enter the number");
        String numbers = manager.inputOutput.nextLine();
        while (true) {
            if (manager.checkTheInputIsIntegerOrLong(price)) {
                if (manager.checkTheInputIsIntegerOrLong(numbers))
                    break;
                else {
                    manager.inputOutput.println("please enter integer for number");
                    numbers = manager.inputOutput.nextLine();
                }
            } else {
                manager.inputOutput.println("please enter integer for price");
                price = manager.inputOutput.nextLine();
            }
        }
        ProductSeller productSeller = new ProductSeller();
        productSeller.setSeller((Seller) thisUser);
        productSeller.setPrice(Long.parseLong(price));
        productSeller.setRemainingItems(Integer.parseInt(numbers));
        return productSeller;
    }

    private Category setCategory(String name) {
        if (name.matches(""))
            return null;
        do {
            try {
                return categoryController.getCategoryByName(name, manager.getToken());
            } catch (InvalidIdException e) {
                manager.inputOutput.println(e.getMessage() + "\n enter another name or back for empty");
                name = manager.inputOutput.nextLine();
            }
        }
        while (!name.matches("back"));
        return null;
    }

    protected void removeProduct(Matcher matcher) {
        matcher.find();
        String id = matcher.group(1);
        if (manager.checkTheInputIsIntegerOrLong(id)) {
            try {
                productController.removeProduct(Integer.parseInt(id), manager.getToken());
            } catch (InvalidIdException | NoAccessException e) {
                manager.inputOutput.println(e.getMessage());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
            } catch (NotLoggedINException e) {
                manager.inputOutput.println(e.getMessage());
                manager.loginInAllPagesEssential();
            }
            return;
        }
        manager.inputOutput.println("enter a number as id.\ntry again");
    }

    protected void balance() {
        userView.balance(manager, infoController);
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }


    protected void sorting() {
        orderSort.run();
    }


    protected void help() {
        validCommands.forEach(validCommand -> manager.inputOutput.println(validCommand.toString()));
    }


}
