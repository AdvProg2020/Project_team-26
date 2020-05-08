package view.main;

import controller.account.AuthenticationController;
import exception.InvalidTokenException;
import exception.NotLoggedINException;
import view.*;

import view.ViewManager;

import java.util.EnumSet;

public class MainPageView extends View implements IView {
    private EnumSet<MainPageViewValidCommands> commands;
    private AuthenticationController controller;

    public MainPageView(ViewManager manager) {
        super(manager);
        this.manager = manager;
        commands = EnumSet.allOf(MainPageViewValidCommands.class);
        super.input = new String();
    }

    @Override
    public View run() {
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("exit|back")) {
            for (MainPageViewValidCommands command : commands) {
                if ((command.getStringMatcher(super.input).find())) {
                    if (command.getView() != null) {
                        command.setManager(this.manager);
                        command.getView().run();
                    } else
                        command.goToFunction(this);
                }
            }
        }
        return null;
    }

    protected void authorizing() {
        AuthenticationView auth = new AuthenticationView(manager, super.input);
        auth.run();
    }

    protected void help(boolean isLoggedIn) {

    }

    public void logout(String token) {
        try {
            controller.logout(token);
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
        manager.setUserLoggedIn(false);
    }

    public void printError() {
        manager.inputOutput.println("invalid command pattern");
    }
}
