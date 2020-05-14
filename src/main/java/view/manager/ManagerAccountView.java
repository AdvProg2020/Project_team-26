package view.manager;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import exception.*;
import view.*;
import view.ViewManager;

import java.util.*;
import java.util.regex.Matcher;

/**
 * i didnt create a package for commands that provides just one command list
 */

public class ManagerAccountView extends View {
    EnumSet<ValidCommandsForManagerAccount> validCommand;
    private IShowUserController controller;
    private IUserInfoController infoController;

    ManagerAccountView(ViewManager manager, IShowUserController controller, IUserInfoController infoController) {
        super(manager);
        validCommand = EnumSet.allOf(ValidCommandsForManagerAccount.class);
        this.controller = controller;
        this.infoController = infoController;
    }

    @Override
    public void run() {
        while (manager.getIsUserLoggedIn()) {
            if ((super.input = (manager.inputOutput.nextLine()).trim()).matches("back"))
                break;
            for (ValidCommandsForManagerAccount command : validCommand) {
                if ((command.getStringMatcher(super.input).find())) {
                    if (command.getView() != null) {
                        command.setManager(this.manager);
                        command.getView().run();
                    } else
                        command.goToFunction(this);
                }
            }
        }
    }

    protected void edit(Matcher matcher) {
        matcher.find();
        manager.inputOutput.println("please enter the " + matcher.group(1));
        String fieldForEdit = manager.inputOutput.nextLine();
        try {
            infoController.changeInfo(matcher.group(1), fieldForEdit, manager.getToken());
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (InvalidAuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (NoSuchField noSuchField) {
            noSuchField.printStackTrace();
        }
    }

    protected void createPromoCode() {

    }

    protected void manageAllProductForManager() {


    }

    protected void removeProduct(Matcher matcher) {

    }


    protected void viewAllPromoCodes(Matcher matcher) {

    }

    protected void viewPersonalInfo() {

    }

    protected void goToProductsMenu(Matcher matcher) {

    }
}
