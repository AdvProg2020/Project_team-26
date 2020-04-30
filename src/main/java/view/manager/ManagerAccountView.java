package view.manager;

import controller.Exceptions;
import controller.account.ShowUserController;
import controller.account.UserInfoController;
import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import view.*;
import view.ViewManager;

import java.util.*;
import java.util.regex.Matcher;

/**
 * i didnt create a package for commands that provides just one command list
 */

public class ManagerAccountView extends View implements ViewI {
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
    public View run() {
        while (manager.getIsUserLoggedin()) {
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
        return null;
    }

    protected void edit(Matcher matcher) {
        matcher.find();
        System.out.println("please enter the " + matcher.group(1));
        String fieldForEdit = manager.inputOutput.nextLine();
        try {
            infoController.changeInfo(matcher.group(1), fieldForEdit, manager.getTocken());
        } catch (Exceptions.InvalidFiledException invalid) {
            invalid.getMessage();
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
        Map<String, String> info = controller.getUserInfo(manager.getTocken());
        for (Map.Entry<String, String> detail : info.entrySet()) {
            System.out.print(detail.getKey() + ":" + detail.getValue());
        }
    }

    protected void goToProductsMenu(Matcher matcher) {

    }
}
