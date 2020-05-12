package view.main;

import controller.account.AuthenticationController;
import exception.InvalidTokenException;
import exception.NotLoggedINException;
import view.*;

import view.ViewManager;
import view.offs.AllOffView;
import view.products.all.AllProductView;

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
        controller = (AuthenticationController) manager.getControllerContainer().getController("AuthenticationController");
    }

    @Override
    public void run() {
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
                printError();
        }
    }

    protected void authorizing() {
        AuthenticationView auth = new AuthenticationView(manager, super.input);
        auth.run();
    }

    protected void help(boolean isLoggedIn) {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("offs");
        commandList.add("products");
        if (isLoggedIn) {
            commandList.add("logout");
        } else {
            commandList.add("login [username]");
            commandList.add("create account [manager|buyer|seller] [username]");
        }
        commandList.forEach(i -> manager.inputOutput.println(i));
    }

    public void logout(String token) {
        try {
            controller.logout(token);
            manager.setUserLoggedIn(false);
        } catch (NotLoggedINException | InvalidTokenException e) {
            manager.inputOutput.println(e.getMessage());
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

    public void printError() {
        manager.inputOutput.println("invalid command pattern");
    }
}
