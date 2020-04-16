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

    private void createPromoCode(Matcher matcher) {

    }

    private void manageAllProductForManager(Matcher matcher) {

    }

    private void removeProduct(Matcher matcher) {

    }


    private void viewAllPromoCodes(Matcher matcher) {

    }

    private void viewPersonalInfo(Matcher matcher) {

    }

    private void goToProductsMenu(Matcher matcher) {

    }

    private void manageCategories(Matcher matcher) {

    }

    private void manageRequestForManager(Matcher matcher) {

    }

    private void manageUsers(Matcher matcher) {

    }

}
