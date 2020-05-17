package view.manager;

import interfaces.account.IShowUserController;
import interfaces.account.IUserInfoController;
import interfaces.discount.IPromoController;
import interfaces.product.IProductController;
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

    ManagerAccountView(ViewManager manager) {
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

    private Promo makePromo() {
        Promo promo = new Promo();
        manager.inputOutput.println("enter the code");
        promo.setPromoCode(manager.inputOutput.nextLine());
        manager.inputOutput.println("enter the percent");
        String percent = manager.inputOutput.nextLine();
        String max = "";
        while (!percent.equalsIgnoreCase("back") && !max.equalsIgnoreCase("back")) {
            if (manager.checkTheInputIsDouble(percent)) {
                promo.setPercent(Double.parseDouble(percent));
                manager.inputOutput.println("enter the MaxValue");
                max = manager.inputOutput.nextLine();
                if (manager.checkTheInputIsInteger(max)) {
                    promo.setMaxDiscount(Long.parseLong(max));
                    manager.inputOutput.println("enter the max usage");
                    max = manager.inputOutput.nextLine();
                    if (manager.checkTheInputIsInteger(max)) {
                        promo.setMaxValidUse(Integer.parseInt(max));
                        manager.inputOutput.println("enter the start date");
                        promo.setStartDate(dateOfPromo());
                        manager.inputOutput.println("enter the end date");
                        promo.setEndDate(dateOfPromo());
                        return promo;
                    } else {
                        manager.inputOutput.println("enter the integer for max usage or back");
                        max = manager.inputOutput.nextLine();
                    }
                } else {
                    manager.inputOutput.println("enter the integer for max or back");
                    max = manager.inputOutput.nextLine();
                }
            } else {
                manager.inputOutput.println("enter the integer for percent or back");
                percent = manager.inputOutput.nextLine();
            }
        }
        return null;
    }

    private Date dateOfPromo() {
        //todo
        return null;
    }

    protected void manageAllProductForManager() {
        while (true) {
            manager.inputOutput.println("enter back or remove [id]");
            String productId = manager.inputOutput.nextLine();
            if (productId.equalsIgnoreCase("back"))
                return;
            if (manager.checkTheInputIsInteger(productId)) {
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
            } else
                manager.inputOutput.println("enter int as id");
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
        validCommands.forEach(validCommand -> manager.inputOutput.println(validCommand.toString()));
    }
}
