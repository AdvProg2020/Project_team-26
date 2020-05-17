package view.manager.request;

import interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.*;
import model.RequestType;
import view.*;
import view.filterAndSort.RequestSort;
import view.main.MainPageView;

import java.util.EnumSet;
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

    }

    private void showEditOff(OffRequest offRequest) {
        //todo
    }

    private void showEditProduct(ProductRequest productRequest) {
        //todo
    }

    private void showDeleteOrAddProductSellerRequest(ProductSellerRequest productSellerRequest, String type) {
        manager.inputOutput.println("seller : " + productSellerRequest.getSeller().getFullName() +
                "(" + productSellerRequest.getSeller().getId() + ")" +
                "wants to " + type + " himself : " + productSellerRequest.getProduct().getId());
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
        offRequest.getItems().forEach(i -> manager.inputOutput.println(i.getProduct().getName() + " with total price +" + i.getPriceInOff()));
    }


    private void showDeleteOrAddProduct(ProductRequest productRequest, String type) {
        manager.inputOutput.println(productRequest.getRequestedBy().getFullName() +
                " with id : " + productRequest.getRequestedBy().getId() + "\nwants to " + type + " : ");
        manager.inputOutput.println(productRequest.getName() + "with category : " + productRequest.getCategory() +
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

    }

}
