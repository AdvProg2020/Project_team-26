package view.manager.discount;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.discount.IPromoController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import view.*;
import view.filterAndSort.PromoSort;
import view.main.MainPageView;
import view.manager.ValidCommandsForManagerAccount;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;

public class DiscountForManagerView extends View implements IView {
    EnumSet<DiscountForManagerViewValidCommands> validCommands;
    private IPromoController promoController;
    private IShowUserController showUserController;
    private PromoSort promoSort;

    public DiscountForManagerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(DiscountForManagerViewValidCommands.class);
    }

    @Override
    public void run() {
        showAll();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (DiscountForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    protected void showAll() {
        try {
            promoController.getAllPromoCodeForCustomer(promoSort.getFieldNameForSort(), promoSort.isAscending()
                    , manager.getToken()).forEach(promo -> manager.inputOutput.println(promo.getPromoCode() +
                    " with id : " + promo.getId()));
        } catch (NotLoggedINException | NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }


    }

    private void show() {

    }

    protected void editDiscountCodeWithItsCode(Matcher matcher) {

    }

    /*
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
        }*/
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
