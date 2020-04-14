package view.manager;

import view.*;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

/**
 * i didnt create a package for commands that provides just one command list
 */

public class ManagerAccountView extends View {
    EnumSet<ValidCommandsForManagerAccount> validCommand;

    ManagerAccountView() {
        validCommand = EnumSet.allOf(ValidCommandsForManagerAccount.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    public void createPromoCode(Matcher matcher) {

    }

    public void manageAllProductForManager(Matcher matcher) {

    }

    public void validCommandsForManagerAccount(Matcher matcher) {

    }

    public void viewAllPromoCodes(Matcher matcher) {

    }

    public void viewPersonalInfo(Matcher matcher) {

    }

    public void goToProductsMenu(Matcher matcher) {

    }

    public void manageCategories(Matcher matcher) {

    }

    public void manageRequestForManager(Matcher matcher) {

    }

    public void manageUsers(Matcher matcher) {

    }

}
