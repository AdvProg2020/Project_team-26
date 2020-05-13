package view.seller;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import controller.interfaces.category.ICategoryController;
import controller.interfaces.order.IOrderController;
import controller.interfaces.product.IProductController;
import exception.*;
import model.Category;
import model.Order;
import model.Product;
import model.User;
import view.*;
import view.seller.offs.ManageOffForSeller;
import view.seller.products.ManageProductForSellerView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class SellerAccountIView extends View {
    EnumSet<SellerAccountViewValidCommands> validCommands;
    ArrayList<String> editableFields;
    IUserInfoController infoController;
    IShowUserController userController;
    IProductController productController;
    ICategoryController categoryController;
    UserView userView;
    IOrderController orderController;
    User thisUser;


    public SellerAccountIView(ViewManager managerView) {
        super(managerView);
        validCommands = EnumSet.allOf(SellerAccountViewValidCommands.class);
        userView = UserView.getInstance();
    }

    @Override
    public void run() {
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
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
        editableFields = new ArrayList<>();
        try {
            userController.getUserInfo(manager.getToken()).forEach((key, info) -> editableFields.add(key));
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    protected void allCategories() {
        //categoryController.getAllCategoriesWithFilter(, , , 0, manager.getToken()).forEach(i -> manager.inputOutput.println(i.getName() + " with id " + i.getId()));
    }

    protected void subCategory(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsInteger(matcher.group(1))) {
            //categoryController.getAllCategoriesWithFilter(, , , Integer.parseInt(matcher.group(1)), manager.getToken()).forEach(i -> manager.inputOutput.println(i.getName() + " with id " + i.getId()));

        }

    }

   /* protected void companyInfo() {//todo
        manager.inputOutput.println(infoController.getCompanyName(manager.getToken()));
    }*/

    protected void history() {//todo
        try {
            for (Order order : orderController.getOrders(manager.getToken())) {
                // manager.inputOutput.println("the user " + order.getCustomer().getFullName() + "with id :" +
                //   order.getCustomer().getId() + " with date " + order.get);

            }

        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
            return;
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }

    }

    protected void manageProducts() {
        ManageProductForSellerView manageProductForSellerView = new ManageProductForSellerView(manager);
        manageProductForSellerView.run();
    }

    protected void offs() {
        ManageOffForSeller manageOffForSeller = new ManageOffForSeller(manager);
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
        Product product = new Product();
        manager.inputOutput.println("enter the name");
        product.setName(manager.inputOutput.nextLine());
        manager.inputOutput.println("enter the brand");
        product.setName(manager.inputOutput.nextLine());
        manager.inputOutput.println("enter the category");
        product.setCategory(setCategory(manager.inputOutput.nextLine()));
        manager.inputOutput.println("enter the attribute");
        product.setDescription(manager.inputOutput.nextLine());
        try {
            productController.createProduct(product, manager.getToken());
        } catch (ObjectAlreadyExistException e) {
            manager.inputOutput.println(e.getMessage()+"\nif you want to add yourself to it seller type yes or no");
        } catch (NotSellerException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    private Category setCategory(String name) {
        //  categoryController.
        return null;


    }

    protected void removeProduct(Matcher matcher) {
        matcher.find();
        String id = matcher.group(1);
        if (manager.checkTheInputIsInteger(id)) {
            try {
                productController.removeProduct(Integer.parseInt(id), manager.getToken());
            } catch (InvalidIdException e) {
                manager.inputOutput.println(e.getMessage());
            }
            return;
        }
        manager.inputOutput.println("enter a number as id.\ntry again");
    }

    protected void balance() {
        userView.balance(manager, infoController);
    }

    protected void sorting() {

    }

    protected void filtering() {

    }


}
