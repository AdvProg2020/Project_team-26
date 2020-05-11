package view.main;

import controller.account.AuthenticationController;
import exception.AlreadyLoggedInException;
import exception.InvalidTokenException;
import exception.NotLoggedINException;
import view.*;

import view.ViewManager;

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
        controller = (AuthenticationController)manager.getControllerContainer().getController("AuthenticationController");
    }

    @Override
    public View run() {
        boolean isFound;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("exit|back")) {
            isFound = false;
            for (MainPageViewValidCommands command : commands) {
                if ((command.getStringMatcher(super.input).find())) {
                    if (command.getView() != null) {
                        command.setManager(this.manager);
                        command.getView().run();
                        isFound = true;
                        break;
                    } else {
                        command.setManager(this.manager);
                        command.goToFunction(this);
                    }
                    isFound = true;
                    break;
                }
            }
            if (!isFound)
                printError();
        }
        return null;
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

    public void printError() {
        manager.inputOutput.println("invalid command pattern");
    }
}
