package view.manager.Promo;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.discount.IPromoController;
import exception.*;
import model.Promo;
import view.*;
import view.filterAndSort.PromoSort;
import view.main.MainPageView;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class PromoForManagerView extends View implements IView {
    EnumSet<PromoForManagerViewValidCommands> validCommands;
    private IPromoController promoController;
    private IShowUserController showUserController;
    private PromoSort promoSort;

    public PromoForManagerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(PromoForManagerViewValidCommands.class);
    }

    @Override
    public void run() {
        showAll();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (PromoForManagerViewValidCommands command : validCommands) {
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

    protected void editDiscountCodeWithItsCode(Matcher matcher) {
        matcher.find();
        int promoId = Integer.parseInt(matcher.group(1));
        try {
            promoController.getPromoCodeTemplateById(promoId, manager.getToken());
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
            return;
        } catch (NotLoggedINException e) {
            manager.loginInAllPagesEssential();
            return;
        }
        String filed = "";
        while (!filed.equalsIgnoreCase("back")) {
            manager.inputOutput.println("chose field you want to edit[customers,percent,date,max ]\nenter back.");
            filed = manager.inputOutput.nextLine();
            if (filed.equalsIgnoreCase("back"))
                return;
            switch (filed) {
                case "percent":
                    fillPercent(promoId);
                    break;
                case "customers":
                    fillCustomer(promoId);
                    break;
                case "date":
                    fillDate(promoId);
                    break;
                case "max":
                    fillMax(promoId);
                    break;
            }
        }
    }

    private void fillCustomer(int promoCode) {
        while (true) {
            manager.inputOutput.println("enter id or back");
            String id = manager.inputOutput.nextLine();
            if (id.equalsIgnoreCase("back"))
                return;
            if (manager.checkTheInputIsInteger(id)) {
                manager.inputOutput.println("add or delete.");
                String action = manager.inputOutput.nextLine();
                if (action.equalsIgnoreCase("add"))
                    addCustomer(promoCode, Integer.parseInt(id));
                if (action.equalsIgnoreCase("delete"))
                    deleteCustomer(promoCode, Integer.parseInt(id));
            } else
                manager.inputOutput.println("inter valid id try again");
        }

    }

    private void deleteCustomer(int promoCode, int customerId) {
        try {
            promoController.removeCustomer(promoCode, customerId, manager.getToken());
        } catch (NoAccessException | InvalidIdException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    private void addCustomer(int promoCode, int customerId) {
        String numberOfUse = "";
        do {
            manager.inputOutput.println("enter the number of use should be integer");
            numberOfUse = manager.inputOutput.nextLine();
        } while (!manager.checkTheInputIsInteger(numberOfUse));
        try {
            promoController.addCustomer(promoCode, customerId, Integer.parseInt(numberOfUse), manager.getToken());
        } catch (NoAccessException | InvalidIdException | ObjectAlreadyExistException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    private void fillPercent(int promoCode) {
        manager.inputOutput.println("enter the percent");
        String percent = manager.inputOutput.nextLine();
        if (manager.checkTheInputIsInteger(percent)) {
            try {
                promoController.setPercent(promoCode, Integer.parseInt(percent), manager.getToken());
            } catch (InvalidIdException | NoAccessException | InvalidFormatException | InvalidDiscountPercentException e) {
                manager.inputOutput.println(e.getMessage());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
            }
        } else
            manager.inputOutput.println("invalid percent");
    }

    private void fillDate(int promoCode) {
/***
 *
 *
 * fucking date;
 */
    }

    private void fillMax(int promoCode) {
        manager.inputOutput.println("enter the Max");
        String max = manager.inputOutput.nextLine();
        if (manager.checkTheInputIsInteger(max)) {
            try {
                promoController.setMaxDiscount(promoCode, Long.parseLong(max), manager.getToken());
            } catch (InvalidIdException | NoAccessException e) {
                manager.inputOutput.println(e.getMessage());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
            }
        } else
            manager.inputOutput.println("invalid percent");

    }

    protected void removeDiscountCodeWithItsCode(Matcher matcher) {
        matcher.find();
        try {
            promoController.removePromoCode(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NoAccessException | InvalidIdException | NoObjectIdException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    protected void viewDiscountCodeWithItsCode(Matcher matcher) {
        matcher.find();
        try {
            Promo promo = promoController.getPromoCodeTemplateById(Integer.parseInt(matcher.group(1)), manager.getToken());
            manager.inputOutput.println(promo.getPromoCode() + " with id : " + promo.getId() + " with max : " + promo.getMaxValidUse() +
                    "\nwith percent : " + promo.getPercent());
            manager.inputOutput.println("its customers are : ");
            promo.getCustomers().forEach(customer -> manager.inputOutput.println(customer.getFullName() +
                    " with id : " + customer.getId()));
        } catch (InvalidIdException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();//todo
        }
    }

    protected void logOut() {
        new MainPageView(manager).logout(manager.getToken());
    }

    protected void help() {
        manager.inputOutput.println("view discount code [id]\nedit discount code [id]\nremove discount code [id]\nhelp");
        manager.inputOutput.println("logout");
    }

    protected void sorting() {
        promoSort.run();
    }
}
