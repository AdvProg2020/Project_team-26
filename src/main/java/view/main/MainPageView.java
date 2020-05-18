package view.main;

import controller.account.AuthenticationController;
import exception.InvalidTokenException;
import exception.NotLoggedINException;
import model.Role;
import view.*;

import view.ViewManager;
import view.cart.CartView;
import view.customer.CustomerView;
import view.manager.ManagerAccountView;
import view.offs.AllOffView;
import view.products.all.AllProductView;
import view.seller.SellerAccountIView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MainPageView extends View {
    private EnumSet<MainPageViewValidCommands> commands;
    private AuthenticationController controller;

    public MainPageView(ViewManager manager) {
        super(manager);
        this.manager = manager;
        commands = EnumSet.allOf(MainPageViewValidCommands.class);
        super.input = new String();
        controller = (AuthenticationController) manager.getController(ControllerContainer.Controller.AuthenticationController);
    }

    @Override
    public void run() {
        manager.inputOutput.println("main page :");
        boolean isFound;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("exit|back")) {
            isFound = false;
            for (MainPageViewValidCommands command : commands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isFound = true;
                    break;
                }
            }
            if (!isFound)
                manager.inputOutput.println("invalid command pattern");
        }
    }

    protected void authorizing() {
        AuthenticationView auth = new AuthenticationView(manager, super.input);
        auth.run();
    }

    protected void help(boolean isLoggedIn) {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back|exit");
        commandList.add("offs");
        commandList.add("products");
        commandList.add("view cart");
        commandList.add("personal page");
        if (isLoggedIn) {
            commandList.add("logout");
        } else {
            commandList.add("login [username]");
            commandList.add("create account [manager|buyer|seller] [username]");
        }
        commandList.forEach(i -> manager.inputOutput.println(i));
    }

    public void logout() {
        try {
            controller.logout(manager.getToken());
            manager.setUserLoggedIn(false);
            manager.inputOutput.println("logged out.");
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
            return;
        }
    }

    protected void cart() {
        CartView cartIView = new CartView(manager);
        cartIView.run();
    }

    protected void personalPage() {
        Role role = manager.getRoleOfUser();
        if (role != null) {
            switch (role) {
                case ADMIN: {
                    ManagerAccountView managerAccountView = new ManagerAccountView(manager);
                    managerAccountView.run();
                    return;
                }
                case SELLER: {
                    SellerAccountIView sellerAccountIView = new SellerAccountIView(manager);
                    sellerAccountIView.run();
                    return;
                }
                case CUSTOMER: {
                    CustomerView customerIView = new CustomerView(manager);
                    customerIView.run();
                    return;
                }
            }
        }
    }

    protected void product() {
        AllProductView allProductView = new AllProductView(manager);
        allProductView.run();
    }

    protected void off() {
        AllOffView allOffView = new AllOffView(manager);
        allOffView.run();
    }
}
