package view.manager;

import view.ViewManager;
import view.cart.CartView;
import view.main.MainPageViewValidCommands;
import view.manager.category.ManageCategoryForManagerView;

import view.manager.request.ManageRequestForManagerView;
import view.View;
import view.products.all.AllProductView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ValidCommandsForManagerAccount {
    ViewPersonalInfo("^view\\s+personal\\s+info$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.viewPersonalInfo();
        }
    },
    EditTheFiled("^edit$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.edit();
        }
    },
    Logout("^logout$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            if (page.getManager().getIsUserLoggedIn()) {
                page.logOut();
                return;
            }
            page.getManager().inputOutput.println("^you are not logged in$");
        }
    },
    ManageUsers("^manage\\s+users$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.managerAllUsers();
        }
    },
    ManageAllProduct("^manage\\s+all\\s+products$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.manageAllProductForManager();
        }
    },
    CreateDiscountCode("^create\\s+discount\\s+code$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.createPromoCode();
        }
    },
    ViewAllDiscountCodes("^view\\s+discount\\s+codes$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.managerAllDiscountCode();
        }
    },
    ManagingRequestForManager("^manage\\s+requests$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.managerAllRequest();
        }
    },
    ManageCategories("^manage\\s+categories$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.managerAllCategories();
        }
    },
    GoToProductsMenu("^products$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.goToProductsMenu();
        }
    }, GoToOffsMenu("^offs$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.goToOffsMenu();
        }
    }, Help("^help$") {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.help();
        }
    };

    private final Pattern commandPattern;

    private final String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }


    ValidCommandsForManagerAccount(String output) {
        this.commandPattern = Pattern.compile(output);
        this.value = output;
    }

    public abstract void goToFunction(ManagerAccountView page);

    @Override
    public String toString() {
        return this.value;
    }
}
