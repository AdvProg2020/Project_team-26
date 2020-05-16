package view.manager.request;

import controller.interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.*;
import model.RequestType;
import view.*;
import view.filterAndSort.RequestSort;
import view.main.MainPageView;
import view.manager.users.ValidCommandsForManageUsersForManagerView;

import javax.persistence.criteria.CriteriaBuilder;
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
        } catch (NoAccessException  e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
        }catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }
    private void showSingleOffRequest(){

    }

    protected void detailTheProductRequest(Matcher matcher) {
        try {
            ProductRequest productRequest = requestController.getProductRequestById(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NoAccessException  e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
        }catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void detailTheProductSellerRequest(Matcher matcher) {
        try {
            ProductSellerRequest productSellerRequest = requestController.getProductSellerRequestById(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
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
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
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
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
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
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }


    protected void acceptTheOffRequest(Matcher matcher) {
        try {
            requestController.acceptOffRequest(Integer.parseInt(matcher.group(1)), manager.getToken());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
            return;
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
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
            return;
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
            manager.inputOutput.println(e.getMessage() + "\nif you want to login enter yes");
            if (manager.inputOutput.nextLine().equals("yes"))
                manager.loginInAllPagesEssential();
            return;
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void logOut() {
        new MainPageView(manager).logout();
    }

    protected void help() {

    }

}
