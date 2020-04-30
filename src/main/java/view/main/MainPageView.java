package view.main;

import controller.Exceptions;
import controller.account.AuthenticationController;
import view.*;

import view.ViewManager;

import java.util.EnumSet;

public class MainPageView extends View implements ViewI {
    private EnumSet<MainPageViewValidCommands> commands;
    private AuthenticationController controller;
    private boolean isBack;

    public MainPageView(ViewManager manager) {
        super(manager);
        this.manager = manager;
        commands = EnumSet.allOf(MainPageViewValidCommands.class);
        super.input = new String();
        controller = new AuthenticationController();
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit|back")) {
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
        AuthenticationView auth = new AuthenticationView(manager, super.input, new AuthenticationController());
        auth.run();
    }

    protected void help(boolean isLoggedIn) {

    }

    public void logout(String token) {
        try {
            controller.logout(token);
            manager.setUserLoggedIn(false);
        } catch (Exceptions.UnSuccessfulLogout unSuccessfulLogout) {
            unSuccessfulLogout.getMessage();
        }
    }

    public void printError() {
        System.out.println("invalid command pattern");
    }
}
