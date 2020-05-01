package view.manager;

import controller.account.ShowUserController;
import controller.category.CategoryController;
import controller.category.ShowCategoryController;
import controller.discount.PromoController;
import view.ViewManager;
import view.main.MainPageView;
import view.main.MainPageViewValidCommands;
import view.manager.category.ManageCategoryForManagerViewI;
import view.manager.discount.discountForManagerViewI;
import view.manager.request.ManageRequestForManagerViewI;
import view.manager.users.ManageUsersForManager;
import view.View;
import view.products.all.AllProductViewI;

import java.util.ArrayList;
import java.util.List;
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
    ManageUsers("manage\\s+users", new ManageUsersForManager(ValidCommandsForManagerAccount.manager, new ShowUserController())) {
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
    ViewAllDiscountCodes("view\\s+discount\\s+codes", new discountForManagerViewI(ValidCommandsForManagerAccount.manager, new PromoController(), new ShowUserController())) {
        @Override
        public void goToFunction(ManagerAccountView page) {
        }
    },
    ManageRequestForManager("manage\\s+requests", new ManageRequestForManagerViewI(ValidCommandsForManagerAccount.manager)) {
        @Override
        public void goToFunction(ManagerAccountView page) {
        }
    },
    ManageCategories("manage\\s+categories", new ManageCategoryForManagerViewI(ValidCommandsForManagerAccount.manager, new CategoryController(), new ShowCategoryController())) {
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
    ShowProducts("products", new AllProductViewI(MainPageViewValidCommands.manager)) {
        @Override
        public void goToFunction(ManagerAccountView page) {

        }
    };

    private final Pattern commandPattern;
    private final View view;
    private static ViewManager manager;
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

    public abstract void goToFunction(ManagerAccountView page);

    @Override
    public String toString() {
        return this.value;
    }
}
