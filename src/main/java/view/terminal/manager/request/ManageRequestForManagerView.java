package view.terminal.manager.request;

import controller.interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.*;
import model.RequestType;
import view.terminal.ControllerContainer;
import view.terminal.View;
import view.terminal.ViewManager;
import view.terminal.filterAndSort.RequestSort;
import view.terminal.main.MainPageView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class ManageRequestForManagerView extends View {
    private EnumSet<ManageRequestForManagerViewValidCommands> validCommands;
    private IRequestController requestController;
    private RequestSort requestSort;

    public ManageRequestForManagerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ManageRequestForManagerViewValidCommands.class);
        requestController = (IRequestController) manager.getController(ControllerContainer.Controller.RequestController);
        requestSort = new RequestSort(manager);
    }

    @Override
    public void run() {
        manager.inputOutput.println("request manage page :");
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (ManageRequestForManagerViewValidCommands command : validCommands) {
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

    protected void showAllOff() {
        try {
            requestController.getAllOffRequests(requestSort.getFieldNameForSort(), requestSort.isAscending(),
                    manager.getToken()).forEach(i -> manager.inputOutput.println("id : " + i.getId() + " type : " +
                    getType(i.getRequestTpe())));
        } catch (NoAccessException | InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
        }
    }

    protected void showAllProduct() {
        try {
            requestController.getAllProductRequests(requestSort.getFieldNameForSort(), requestSort.isAscending(),
                    manager.getToken()).forEach(i -> manager.inputOutput.println("id : " + i.getId() + " type : " +
                    getType(i.getRequestTpe())));
        } catch (NoAccessException | InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
        }

    }

    protected void showAllProductSeller() {
        try {
            requestController.getAllProductSellerRequests(requestSort.getFieldNameForSort(), requestSort.isAscending(),
                    manager.getToken()).forEach(i -> manager.inputOutput.println("id : " + i.getId() + " type : " +
                    getType(i.getRequestType())));
        } catch (NoAccessException | InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
        }
    }

    private String getType(RequestType requestTpe) {
        switch (requestTpe) {
            case ADD:
                return "add request";
            case EDIT:
                return "edit request";
            case DELETE:
                return "delete request";
        }
        return "";

    }

    protected void detailTheOffRequest(Matcher matcher) {
        try {
            OffRequest offRequest = requestController.getOffRequestById(Integer.parseInt(matcher.group(1)), manager.getToken());
            showSingleOffRequest(offRequest);
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    private void showSingleOffRequest(OffRequest offRequest) {
        switch (offRequest.getRequestTpe()) {
            case ADD:
                showDeleteOrAddOff(offRequest, "add");
                break;
            case EDIT:
                showEditOff(offRequest);
                break;
            case DELETE:
                showDeleteOrAddOff(offRequest, "delete");
                break;
        }
    }

    private void showSingleProductSellerRequest(ProductSellerRequest productSellerRequest) {
        switch (productSellerRequest.getRequestType()) {
            case ADD:
                showDeleteOrAddProductSellerRequest(productSellerRequest, "add");
                break;
            case EDIT:
                showEditProductSellerRequest(productSellerRequest);
                break;
            case DELETE:
                showDeleteOrAddProductSellerRequest(productSellerRequest, "remove");
                break;
        }
    }

    private void showEditProductSellerRequest(ProductSellerRequest productSellerRequest) {
        manager.inputOutput.println(productSellerRequest.getSeller().getUsername() + " wants to : "
                + " change into : \n" + " price : " + productSellerRequest.getProductSeller().getPrice());
        manager.inputOutput.println(" amount : " + productSellerRequest.getProductSeller().getRemainingItems());
        manager.inputOutput.println(" name : " + productSellerRequest.getProduct().getName());
        manager.inputOutput.println(" category : " + productSellerRequest.getProduct().getCategory().getName());
        manager.inputOutput.println(" description : " + productSellerRequest.getProduct().getDescription());
        manager.inputOutput.println(" brand : " + productSellerRequest.getProduct().getBrand());
    }

    private void showEditOff(OffRequest offRequest) {
        manager.inputOutput.println(offRequest.getSeller().getUsername() + " wants to : "
                + " change into : \n" + " start : " + offRequest.getStartDate().toString());
        manager.inputOutput.println(" end : " + offRequest.getEndDate().toString());
        manager.inputOutput.println("products :");
        offRequest.getItems().forEach(offItemRequest -> manager.inputOutput.println(offItemRequest.getProductSeller().getProduct().getName() +
                " with price " + offItemRequest.getPriceInOff()));
    }

    private void showEditProduct(ProductRequest productRequest) {
        manager.inputOutput.println(productRequest.getRequestedBy().getUsername() + " wants to : "
                + " change into : \n" + " name : " + productRequest.getName());
        manager.inputOutput.println(" category : " + productRequest.getCategory().getName());
        manager.inputOutput.println(" brand : " + productRequest.getBrand());
        manager.inputOutput.println(" category : " + productRequest.getCategory().getName());
        manager.inputOutput.println(" description : " + productRequest.getProduct().getDescription());
    }

    private void showDeleteOrAddProductSellerRequest(ProductSellerRequest productSellerRequest, String type) {
        manager.inputOutput.println("seller : " + productSellerRequest.getSeller().getFullName() +
                "(" + productSellerRequest.getSeller().getId() + ")" +
                "wants to " + type + " himself : " + (type.equals("add") ? "to " : "from ") + productSellerRequest.getProduct().getId());
    }


    private void showSingleProductRequest(ProductRequest productRequest) {
        switch (productRequest.getRequestTpe()) {
            case ADD:
                showDeleteOrAddProduct(productRequest, "add");
            case EDIT:
                showEditProduct(productRequest);
            case DELETE:
                showDeleteOrAddProduct(productRequest, "delete");
        }
    }


    private void showDeleteOrAddOff(OffRequest offRequest, String type) {
        manager.inputOutput.println("seller : " + offRequest.getSeller().getFullName() +
                "(" + offRequest.getSeller().getId() + ")" +
                "wants to " + type + " off : " + offRequest.getId());
        if (type.equals("add")) {
            manager.inputOutput.println("from : " + offRequest.getStartDate() + "" +
                    " until " + offRequest.getEndDate());
        }
        manager.inputOutput.println("products are : ");
        offRequest.getItems().forEach(i -> manager.inputOutput.println(i.getProductSeller().getProduct().getName() + " with total price +" + i.getPriceInOff()));
    }


    private void showDeleteOrAddProduct(ProductRequest productRequest, String type) {
        manager.inputOutput.println(productRequest.getRequestedBy().getUsername() +
                " with id : " + productRequest.getRequestedBy().getId() + "\nwants to " + type + " : ");
        manager.inputOutput.println(productRequest.getName() + " with category : " + productRequest.getCategory() +
                "with brand : " + productRequest.getBrand());
    }

    protected void detailTheProductRequest(Matcher matcher) {
        try {
            ProductRequest productRequest = requestController.getProductRequestById(Integer.parseInt(matcher.group(1)), manager.getToken());
            showSingleProductRequest(productRequest);
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void detailTheProductSellerRequest(Matcher matcher) {
        try {
            ProductSellerRequest productSellerRequest = requestController.getProductSellerRequestById(Integer.parseInt(matcher.group(1)), manager.getToken());
            showSingleProductSellerRequest(productSellerRequest);
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void declineTheOffRequest(Matcher matcher) {
        try {
            requestController.rejectOffRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void declineTheProductRequest(Matcher matcher) {
        try {
            requestController.rejectProductRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void declineTheProductSellerRequest(Matcher matcher) {
        try {
            requestController.rejectProductSellerRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }


    protected void acceptTheOffRequest(Matcher matcher) {
        try {
            requestController.acceptOffRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void acceptTheProductRequest(Matcher matcher) {
        try {
            requestController.acceptProductRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void acceptTheProductSellerRequest(Matcher matcher) {
        try {
            requestController.acceptProductSellerRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void logOut() {
        new MainPageView(manager).logout();
    }

    protected void sorting() {
        requestSort.run();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("show all");
        commandList.add("show products request");
        commandList.add("show sellers request");
        commandList.add("detail off [id]");
        commandList.add("detail products [id]");
        commandList.add("detail product seller [id]");
        commandList.add("accept off [id]");
        commandList.add("accept product [id]");
        commandList.add("accept product seller [id]");
        commandList.add("decline off [id]");
        commandList.add("decline product [id]");
        commandList.add("decline product seller [id]");
        commandList.add("logout");
        commandList.add("sorting");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }
}
