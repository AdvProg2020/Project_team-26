package view.manager.discount;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.discount.IPromoController;
import view.*;
import view.main.MainPageView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;

public class discountForManagerView extends View implements IView {
    EnumSet<discountForManagerViewValidCommands> validCommands;
    private IPromoController controller;
    private IShowUserController userController;

    public discountForManagerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(discountForManagerViewValidCommands.class);
        this.controller = controller;
        this.userController = userController;
    }

    @Override
    public View run() {
        showAll();
        while (manager.getIsUserLoggedin()) {
            if ((super.input = (manager.inputOutput.nextLine()).trim()).matches("back"))
                break;
            for (discountForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    protected void showAll() {

    }

    private void show() {

    }

    protected void editDiscountCodeWithItsCode(Matcher matcher) {

    }

    private ArrayList<String> edit() {
        manager.inputOutput.println("which field you want to change");
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(manager.inputOutput.nextLine());
        if (inputs.get(0).matches("users")) {
            manager.inputOutput.println("type [remove|add] [username]");
            inputs.add(manager.inputOutput.nextLine());
            return inputs;
        }
        manager.inputOutput.println("type new one");
        inputs.add(manager.inputOutput.nextLine());
        return inputs;
    }

    protected void removeDiscountCodeWithItsCode(Matcher matcher) {
        matcher.find();

    }

    protected void viewDiscountCodeWithItsCode(Matcher matcher) {


    }

    protected void logOut() {
        new MainPageView(manager).logout(manager.getToken());
    }

    protected void help(boolean isLoggedIn) {
        manager.inputOutput.println("view discount code [code]\nedit discount code [code]\nremove discount code [code]\nhelp");
        if (isLoggedIn)
            manager.inputOutput.println("logout");
    }
}
