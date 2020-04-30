package view.manager.discount;

import controller.Exceptions;
import controller.discount.PromoDetailsController;
import controller.interfaces.account.IShowUserController;
import controller.interfaces.discount.IPromoController;
import view.*;
import view.main.MainPageView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;

public class discountForManagerViewI extends View implements ViewI {
    EnumSet<discountForManagerViewValidCommands> validCommands;
    private IPromoController controller;
    private IShowUserController userController;

    public discountForManagerViewI(ViewManager manager, IPromoController controller, IShowUserController userController) {
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
        PromoDetailsController[] promoCodes = controller.getAllPromoCode(manager.getTocken());
        for (PromoDetailsController promoCode : promoCodes) {
            show(promoCode);
        }
    }

    private void show(PromoDetailsController promo) {
        System.out.println("promo code:" + promo.getPromoCode(manager.getTocken()));
        System.out.println("start date:" + promo.getStartDate(manager.getTocken()).toString());//
        System.out.println("end date:" + promo.getEndDate(manager.getTocken()).toString());//
        System.out.println("percent of discount:" + promo.getPercent(manager.getTocken()));
        System.out.println("maximum discount:" + promo.getMaxDiscount(manager.getTocken()));
        System.out.println("users:");
        int[] customersId = promo.getCustomersIds();
        for (int id : customersId) {
            System.out.println("username:" + userController.getUserById(id,
                    manager.getTocken()).getUsername(manager.getTocken()));
        }
    }

    protected void editDiscountCodeWithItsCode(Matcher matcher) {
        matcher.find();
        try {
            String id = controller.getPromoCodeTemplate(matcher.group(1), manager.getTocken()).getPromoCode(manager.getTocken());
            ArrayList<String> changes = edit();
            controller.editPromoCode(id, changes, manager.getTocken());
        } catch (Exceptions.TheParameterDoesNOtExist error) {
            error.getMessage();
        }
    }

    private ArrayList<String> edit() {
        System.out.println("which field you want to change");
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(manager.inputOutput.nextLine());
        if (inputs.get(0).matches("users")) {
            System.out.println("type [remove|add] [username]");
            inputs.add(manager.inputOutput.nextLine());
            return inputs;
        }
        System.out.println("type new one");
        inputs.add(manager.inputOutput.nextLine());
        return inputs;
    }

    protected void removeDiscountCodeWithItsCode(Matcher matcher) {
        matcher.find();
        try {
            controller.removePromoCode(matcher.group(1), manager.getTocken());
        } catch (Exceptions.TheParameterDoesNOtExist promoCodeDoesntExist) {
            promoCodeDoesntExist.getMessage();
        }
    }

    protected void viewDiscountCodeWithItsCode(Matcher matcher) {
        matcher.find();
        try {
            PromoDetailsController promo = controller.getPromoCodeTemplate(matcher.group(1), manager.getTocken());
            show(promo);
        } catch (Exceptions.TheParameterDoesNOtExist promoCodeDoesntExist) {
            promoCodeDoesntExist.getMessage();
        }

    }

    protected void logOut() {
        new MainPageView(manager).logout(manager.getTocken());
    }

    protected void help(boolean isLoggedIn) {
        System.out.println("view discount code [code]\nedit discount code [code]\nremove discount code [code]\nhelp");
        if (isLoggedIn)
            System.out.println("logout");
    }
}
