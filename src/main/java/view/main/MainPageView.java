package view.main;

import controller.account.AuthenticationController;
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
        controller.logout(token);
        manager.setUserLoggedIn(false);
    }

    public void printError() {
        manager.inputOutput.println("invalid command pattern");
    }
}
