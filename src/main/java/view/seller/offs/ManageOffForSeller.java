package view.seller.offs;

import controller.interfaces.discount.IOffController;
import controller.interfaces.product.IProductController;
import exception.*;
import model.*;
import view.ControllerContainer;
import view.View;
import view.ViewManager;
import view.filterAndSort.OffSort;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class ManageOffForSeller extends View {
    private EnumSet<ViewOffsForSellerAccountValidCommands> validCommands;
    private IOffController offController;
    private IProductController productController;
    boolean isPercent;
    private OffSort offSort;
    private Seller seller;

    public ManageOffForSeller(ViewManager managerView, Seller seller) {
        super(managerView);
        validCommands = EnumSet.allOf(ViewOffsForSellerAccountValidCommands.class);
        offSort = new OffSort(manager);
        offController = (IOffController) manager.getController(ControllerContainer.Controller.OffController);
        productController = (IProductController) manager.getController(ControllerContainer.Controller.ProductController);
        this.seller = seller;
    }

    @Override
    public void run() {
        manager.inputOutput.println("seller off menu");
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
            if (!isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    protected void showAll() {
        try {
            for (Off off : offController.getAllOfForSellerWithFilter(
                    offSort.getFieldNameForSort()
                    , offSort.isAscending(), manager.getToken())) {
                manager.inputOutput.println("off id :" + off.getId());
                manager.inputOutput.println("start :" + off.getStartDate().toString());
                off.getItems().forEach(item -> manager.inputOutput.println(item.getProductSeller().getProduct().getName() + " with id" +
                        item.getProductSeller().getId() + " with price " + item.getPriceInOff()));
                manager.inputOutput.println("end :" + off.getEndDate().toString());
            }
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        }
    }

    protected void addOff() {
        Off off = new Off();
        Date start = manager.createDate();
        if (start == null)
            return;
        Date end = manager.createDate();
        if (end == null)
            return;
        off.setStartDate(start);
        off.setEndDate(end);
        off.setSeller(this.seller);
        List<OffItem> offItemList = off.getItems();
        try {
            addOffItem(offItemList);
            offController.createNewOff(off, manager.getToken());
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        }
    }

    private void addOffItem(List<OffItem> offItems) {
        long priceForProduct = 0;
        int percentForPrice = 0;
        while (true) {
            manager.inputOutput.println("enter the product id for adding and if you want to finish adding enter finish or back");
            String productId = manager.inputOutput.nextLine();
            if (productId.matches("back"))
                return;
            if (manager.checkTheInputIsIntegerOrLong(productId, false)) {
                try {
                    ProductSeller productSeller = productController.getProductSellerByIdAndSellerId(Integer.parseInt(productId), manager.getToken());
                    priceForProduct = inputPrice();
                    if (isPercent) {
                        percentForPrice = (int) priceForProduct;
                        priceForProduct = (long) (productSeller.getPrice() * (100 - percentForPrice) / 100);
                    }
                    offItems.add(new OffItem(productSeller, priceForProduct));
                } catch (InvalidIdException | NoObjectIdException | NoAccessException e) {
                    manager.inputOutput.println(e.getMessage());
                } catch (InvalidTokenException e) {
                    manager.setTokenFromController(e.getMessage());
                } catch (NotLoggedINException e) {
                    manager.inputOutput.println(e.getMessage());
                    manager.loginInAllPagesEssential();
                }
            }
        }
    }

    /*private void addProductToPff(Off off) {
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
    }*/

    private long inputPrice() {
        while (true) {
            manager.inputOutput.println("enter the price or percent [d%]");
            String price = manager.inputOutput.nextLine();
            if (price.contains("%")) {
                String[] input = price.split("%");
                if (manager.checkTheInputIsDouble(input[0]))
                    isPercent = true;
                return (int) Double.parseDouble(input[0]);
            }
            if (manager.checkTheInputIsIntegerOrLong(price, true)) {
                isPercent = false;
                return Long.parseLong(price);
            }
        }
    }

    protected void editOff() {//todo

    }

    protected void showOff(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1), false)) {
            try {
                Off off = offController.getOff(Integer.parseInt(matcher.group(1)), manager.getToken());
                manager.inputOutput.println("off id : " + off.getId());
                manager.inputOutput.println("start : " + off.getStartDate().toString());
                off.getItems().forEach(item -> manager.inputOutput.println(item.getProductSeller().getProduct().getName() + " with id" +
                        item.getProductSeller().getId() + " with price : " + item.getPriceInOff()));
                manager.inputOutput.println(" end : " + off.getEndDate().toString());
            } catch (InvalidIdException e) {
                manager.inputOutput.println(e.getMessage());
            }
        }
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }


    protected void sorting() {
        offSort.run();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("view [offId]");
        commandList.add("edit [offId]");
        commandList.add("add off");
        commandList.add("sorting");
        commandList.add("show all");
        commandList.add("logout");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }

}
