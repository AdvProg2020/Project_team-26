package view.seller.offs;

import controller.interfaces.discount.IOffController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.ObjectAlreadyExistException;
import model.Off;
import view.View;
import view.ViewManager;
import view.seller.products.ManageProductForSellerViewValidCommands;

import java.util.EnumSet;
import java.util.Map;
import java.util.regex.Matcher;

public class ManageOffForSeller extends View {
    EnumSet<ViewOffsForSellerAccountValidCommands> validCommands;
    IOffController offController;
    boolean isPercent;

    public ManageOffForSeller(ViewManager managerView) {
        super(managerView);
        validCommands = EnumSet.allOf(ViewOffsForSellerAccountValidCommands.class);
    }

    @Override
    public void run() {
        showAll();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (ViewOffsForSellerAccountValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    protected void showAll() {
      /*  for (Off off : offController.getAllOfForSellerWithFilter()) {
            manager.inputOutput.println("off id :" + off.getId());
            manager.inputOutput.println("start :" + off.getStartDate().toString());
            /*off.getItems().forEach(item -> manager.inputOutput.println(item.getProduct().getName() + " with id" +
                    item.getProduct().getId() + "with price" + item.getPriceInOff()));
            manager.inputOutput.println("end :" + off.getEndDate().toString());
        }*/
    }

    protected void addOff() {
        Off off = new Off();
        /***
         *
         * enter the date;
         */
        try {
            offController.createNewOff(off, manager.getToken());
            addProductToPff(off);
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    private void addProductToPff(Off off) {
        long priceForProduct = 0;
        int percentForPrice = 0;
        while (true) {
            manager.inputOutput.println("enter the product id for adding and if you want to finish adding enter finish or back");
            String productId = manager.inputOutput.nextLine();
            if (productId.matches("back"))
                return;
            if (manager.checkTheInputIsInteger(productId)) {
                priceForProduct = inputPrice();
                if (isPercent) {
                    percentForPrice = (int) priceForProduct;
                    priceForProduct = -1;
                }
                try {
                    offController.addProductToOff(off, Integer.parseInt(productId), priceForProduct, percentForPrice, manager.getToken());
                } catch (NoAccessException e) {
                    manager.inputOutput.println(e.getMessage());
                    return;
                } catch (ObjectAlreadyExistException | InvalidIdException e) {
                    manager.inputOutput.println(e.getMessage());
                } catch (InvalidTokenException e) {
                    manager.setTokenFromController(e.getMessage());
                }
            }
        }
    }

    private long inputPrice() {
        while (true) {
            manager.inputOutput.println("enter the price or percent");
            String price = manager.inputOutput.nextLine();
            if (price.matches("^\\d\\d%$")) {
                String[] input = price.split("%");
                isPercent = true;
                return Integer.parseInt(input[0]);
            }
            if (manager.checkTheInputIsInteger(price)) {
                isPercent = false;
                return Long.parseLong(price);
            }
        }


    }

    protected void editOff() {

    }

    protected void showOff(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsInteger(matcher.group(1))) {
            try {
                Off off = offController.getOff(Integer.parseInt(matcher.group(1)), manager.getToken());
                manager.inputOutput.println("off id :" + off.getId());
                manager.inputOutput.println("start :" + off.getStartDate().toString());
                off.getItems().forEach(item -> manager.inputOutput.println(item.getProduct().getName() + " with id" +
                        item.getProduct().getId() + "with price" + item.getPriceInOff()));
                manager.inputOutput.println("end :" + off.getEndDate().toString());
            } catch (InvalidIdException e) {
                manager.inputOutput.println(e.getMessage());
            }
        }
    }

}
