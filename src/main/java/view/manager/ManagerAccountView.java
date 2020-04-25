package view.manager;

import view.*;
import view.ViewManager;
import view.main.MainPageViewValidCommands;

import java.util.EnumSet;
import java.util.regex.Matcher;

/**
 * i didnt create a package for commands that provides just one command list
 */

public class ManagerAccountView extends View implements view {
    EnumSet<ValidCommandsForManagerAccount> validCommand;

    ManagerAccountView(ViewManager manager) {
        super(manager);
        validCommand = EnumSet.allOf(ValidCommandsForManagerAccount.class);
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
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

    }

    protected void createPromoCode() {

    }

    protected void manageAllProductForManager() {

    }

    protected void removeProduct(Matcher matcher) {

    }


    protected void viewAllPromoCodes(Matcher matcher) {

    }

    protected void viewPersonalInfo(Matcher matcher) {

    }

    protected void goToProductsMenu(Matcher matcher) {

    }

    protected void manageCategories(Matcher matcher) {

    }

    protected void manageRequestForManager(Matcher matcher) {

    }

    protected void manageUsers(Matcher matcher) {

    }

}
