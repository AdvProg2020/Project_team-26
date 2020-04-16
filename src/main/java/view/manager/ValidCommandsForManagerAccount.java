package view.manager;

import view.manager.category.ManageCategoryForManagerView;
import view.manager.discount.discountForManagerView;
import view.manager.request.ManageRequestForManagerView;
import view.manager.users.ManageUsersForManager;
import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ValidCommandsForManagerAccount {
    ViewPersonalInfo("view\\s+personal\\s+info", null),
    EditTheFiled("edit\\s+(.*)", null),
    ManageUsers("manage\\s+users", new ManageUsersForManager()),
    ManageAllProduct("manage\\s+all\\s+products", null),
    CreateDiscountCode("create\\s+discount\\s+code", null),
    ViewAllDiscountCodes("view\\s+discount\\s+codes", new discountForManagerView()),
    ManageRequestForManager("manage\\s+requests", new ManageRequestForManagerView()),
    ManageCategories("manage\\s+categories", new ManageCategoryForManagerView()),
    GoToProductsMenu("products", null);

    private final Pattern commandPattern;
    private final View view;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    ValidCommandsForManagerAccount(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

    public List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        list.add("view personal info");
        return list;
    }

}
