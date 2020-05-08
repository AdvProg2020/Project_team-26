package view.manager;

import controller.account.ShowUserController;
import controller.category.CategoryController;
import controller.discount.PromoController;
import view.ViewManager;
import view.main.MainPageViewValidCommands;
import view.manager.category.ManageCategoryForManagerView;
import view.manager.discount.discountForManagerView;

import view.manager.request.ManageRequestForManagerView;
import view.manager.users.ManageUsersForManager;
import view.View;
import view.products.all.AllProductView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ValidCommandsForManagerAccount {
    ViewPersonalInfo("view\\s+personal\\s+info", null) {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.viewPersonalInfo();
        }
    },
    EditTheFiled("edit\\s+(.*)", null) {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.edit(Pattern.compile(EditTheFiled.toString()).matcher(page.getInput()));
        }
    },
    ManageUsers("manage\\s+users", new ManageUsersForManager(ValidCommandsForManagerAccount.getManager())) {
        @Override
        public void goToFunction(ManagerAccountView page) {
        }
    },
    ManageAllProduct("manage\\s+all\\s+products", null) {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.manageAllProductForManager();
        }
    },
    CreateDiscountCode("create\\s+discount\\s+code", null) {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.createPromoCode();
        }
    },
    ViewAllDiscountCodes("view\\s+discount\\s+codes", new discountForManagerView(ValidCommandsForManagerAccount.getManager())) {
        @Override
        public void goToFunction(ManagerAccountView page) {

        }
    },
    ManagingRequestForManager("manage\\s+requests", new ManageRequestForManagerView(ValidCommandsForManagerAccount.getManager())){
        @Override
        public void goToFunction(ManagerAccountView page) {
        }
    },
    ManageCategories("manage\\s+categories", new ManageCategoryForManagerView(ValidCommandsForManagerAccount.getManager())) {
        @Override
        public void goToFunction(ManagerAccountView page) {
        }
    },
    GoToProductsMenu("products", null) {
        @Override
        public void goToFunction(ManagerAccountView page) {
            page.goToProductsMenu(Pattern.compile(GoToProductsMenu.toString()).matcher(page.getInput()));
        }
    },
    ShowProducts("products", new AllProductView(MainPageViewValidCommands.manager)) {
        @Override
        public void goToFunction(ManagerAccountView page) {

        }
    };

    private final Pattern commandPattern;
    private final View view;
    
    private final String value;

    public void setManager(ViewManager manager) {
        ValidCommandsForManagerAccount.manager = manager;
    }

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    public View getView() {
        return this.view;
    }


    ValidCommandsForManagerAccount(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.value = output;
        this.view = view;
    }

    private static ViewManager manager;
    static {
        manager = null;
    }
    private static ViewManager getManager(){
        return manager;
    }
    public abstract void goToFunction(ManagerAccountView page);

    @Override
    public String toString() {
        return this.value;
    }
}
