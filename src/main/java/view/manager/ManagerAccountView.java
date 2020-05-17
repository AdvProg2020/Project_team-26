package view.manager;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import controller.interfaces.discount.IPromoController;
import controller.interfaces.product.IProductController;
import exception.*;
import model.Promo;
import view.*;
import view.ViewManager;
import view.manager.category.ManageCategoryForManagerView;
import view.manager.Promo.PromoForManagerView;
import view.manager.request.ManageRequestForManagerView;
import view.manager.users.ManageUsersForManager;
import view.offs.AllOffView;
import view.products.all.AllProductView;

import java.util.*;

/**
 * i didnt create a package for commands that provides just one command list
 */

public class ManagerAccountView extends View {
    private EnumSet<ValidCommandsForManagerAccount> validCommands;
    private IUserInfoController infoController;
    private ArrayList<String> editableFields;
    private IShowUserController userController;
    private IProductController productController;
    private IPromoController promoController;
    private UserView userView;

    public ManagerAccountView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ValidCommandsForManagerAccount.class);
        userView = UserView.getInstance();
        infoController = (IUserInfoController) manager.getController(ControllerContainer.Controller.UserInfoController);
        editableFields = new ArrayList<>();
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
        promoController = (IPromoController) manager.getController(ControllerContainer.Controller.PromoController);
        userController = (IShowUserController) manager.getController(ControllerContainer.Controller.ShowUserController);
    }

    @Override
    public void run() {
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (ValidCommandsForManagerAccount command : validCommands) {
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

    private void initialEditFields() {
        userView.initialEditFields(editableFields, manager, userController);
    }

    protected void edit() {
        initialEditFields();
        userView.edit(editableFields, manager, infoController);

    }

    protected void createPromoCode() {
        Promo promo = makePromo();
        if (promo == null)
            return;
        while (true) {
            try {
                promoController.createPromoCode(promo, manager.getToken());
            } catch (NoAccessException e) {
                manager.inputOutput.println(e.getMessage());
                return;
            } catch (NotLoggedINException e) {
                manager.inputOutput.println(e.getMessage());
                manager.loginInAllPagesEssential();
            } catch (ObjectAlreadyExistException e) {
                manager.inputOutput.println(e.getMessage() + "try another code or back");
                promo.setPromoCode(manager.inputOutput.nextLine());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
            }
            if (promo.getPromoCode().equalsIgnoreCase("back"))
                return;
        }
    }

    private String makeString(String field, boolean isDouble, boolean isLong) {
        while (true) {
            manager.inputOutput.println("enter the " + field);
            String output = manager.inputOutput.nextLine();
            if (output.equals("back"))
                return null;
            if (manager.isValidNUmber(output, isDouble, isLong)) {
                return output;
            }
            manager.inputOutput.println("enter integer");
        }
    }

    private Promo makePromo() {
        Date date;
        Promo promo = new Promo();
        manager.inputOutput.println("enter the code");
        promo.setPromoCode(manager.inputOutput.nextLine());
        String field = makeString("percent", true, false);
        if (field != null) {
            promo.setPercent(Double.parseDouble(field));
            field = makeString("max value", false, true);
            if (field != null) {
                promo.setMaxDiscount(Long.parseLong(field));
                field = makeString("max use", false, true);
                if (field != null) {
                    promo.setMaxValidUse(Integer.parseInt(field));
                    manager.inputOutput.println("start date");
                    date = dateOfPromo();
                    if (date != null) {
                        promo.setStartDate(date);
                        manager.inputOutput.println("start date");
                        date = dateOfPromo();
                        if (date != null) {
                            promo.setEndDate(date);
                            return promo;
                        }
                    }
                }
            }
        }
        return null;
    }

    private Date dateOfPromo() {
        return manager.createDate();
    }

    protected void manageAllProductForManager() {
        while (true) {
            manager.inputOutput.println("enter back or enter id to remove");
            String productId = manager.inputOutput.nextLine();
            if (productId.equalsIgnoreCase("back"))
                return;
            if (manager.checkTheInputIsIntegerOrLong(productId, false)) {
                try {
                    productController.removeProduct(Integer.parseInt(productId), manager.getToken());
                } catch (InvalidIdException | NoAccessException e) {
                    manager.inputOutput.println(e.getMessage());
                } catch (InvalidTokenException e) {
                    e.printStackTrace();
                } catch (NotLoggedINException e) {
                    manager.inputOutput.println(e.getMessage());
                    manager.loginInAllPagesEssential();
                }
            }
            manager.inputOutput.println("enter valid format id");
        }
    }

    protected void viewPersonalInfo() {
        userView.viewPersonalInfo(manager, userController);
    }

    protected void goToProductsMenu() {
        AllProductView allProductView = new AllProductView(manager);
        allProductView.run();
    }

    protected void goToOffsMenu() {
        AllOffView allOffView = new AllOffView(manager);
        allOffView.run();
    }

    protected void managerAllUsers() {
        ManageUsersForManager manageUsersForManager = new ManageUsersForManager(manager);
        manageUsersForManager.run();
    }

    protected void managerAllCategories() {
        ManageCategoryForManagerView manageCategoryForManagerView = new ManageCategoryForManagerView(manager);
        manageCategoryForManagerView.run();
    }

    protected void managerAllDiscountCode() {
        PromoForManagerView discountForManagerView = new PromoForManagerView(manager);
        discountForManagerView.run();

    }

    protected void managerAllRequest() {
        ManageRequestForManagerView manageRequestForManagerView = new ManageRequestForManagerView(manager);
        manageRequestForManagerView.run();

    }

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("offs");
        commandList.add("view personal info");
        commandList.add("products");
        commandList.add("edit");
        commandList.add("manage users");
        commandList.add("manage all products");
        commandList.add("create discount code");
        commandList.add("view discount codes");
        commandList.add("manage requests");
        commandList.add("manage categories");
        commandList.add("logout");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }
}
