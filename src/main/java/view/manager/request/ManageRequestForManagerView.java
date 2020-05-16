package view.manager.request;

import controller.interfaces.request.IRequestController;
import model.ProductSellerRequest;
import model.RequestTpe;
import view.*;
import view.filterAndSort.RequestSort;
import view.main.MainPageView;
import view.manager.users.ValidCommandsForManageUsersForManagerView;

import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class ManageRequestForManagerView extends View {
    private  EnumSet<ManageRequestForManagerViewValidCommands> validCommands;
    private   IRequestController requestController;
    private  RequestSort requestSort;

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
        requestController.getAllOffRequests(requestSort.getFieldNameForSort(), requestSort.isAscending(),
                manager.getToken()).forEach(i -> manager.inputOutput.println("id : " + i.getId() + " type : " +
                getType(i.getRequestTpe())));

    }

    protected void showAllProduct() {
        requestController.getAllProductRequests(requestSort.getFieldNameForSort(), requestSort.isAscending(),
                manager.getToken()).forEach(i -> manager.inputOutput.println("id : " + i.getId() + " type : " +
                getType(i.getRequestTpe())));

    }

    protected void showAllProductSeller() {
        requestController.getAllProductSellerRequests(requestSort.getFieldNameForSort(), requestSort.isAscending(),
                manager.getToken()).forEach(i -> manager.inputOutput.println("id : " + i.getId() + " type : " +
                getType(i.getRequestTpe())));
    }

    private String getType(RequestTpe requestTpe) {
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
        requestController.rejectOffRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }

    protected void detailTheProductRequest(Matcher matcher) {
        requestController.rejectProductRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }

    protected void detailTheProductSellerRequest(Matcher matcher) {
        requestController.rejectProductSellerRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }

    protected void declineTheOffRequest(Matcher matcher) {
        requestController.rejectOffRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }

    protected void declineTheProductRequest(Matcher matcher) {
        requestController.rejectProductRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }

    protected void declineTheProductSellerRequest(Matcher matcher) {
        requestController.rejectProductSellerRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }


    protected void acceptTheOffRequest(Matcher matcher) {
        requestController.acceptOffRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }

    protected void acceptTheProductRequest(Matcher matcher) {
        requestController.acceptProductRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }

    protected void acceptTheProductSellerRequest(Matcher matcher) {
        requestController.acceptProductSellerRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
    }

    protected void logOut() {
        new MainPageView(manager).logout();
    }

    protected void help() {
        //todo
    }

}
