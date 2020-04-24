package view.main;

import view.*;

import view.*;
import view.ViewManager;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Matcher;

public class MainPageView extends View implements view {
    private EnumSet<MainPageViewValidCommands> commands;
    private ViewManager manager;
    private String input;

    public MainPageView(ViewManager manager) {
        this.manager = manager;
        commands = EnumSet.allOf(MainPageViewValidCommands.class);
        input = new String();
    }

    @Override
    public View run(ViewManager thisManager) {
        this.manager = thisManager;
        while (!(input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (MainPageViewValidCommands command : commands) {
                if ((command.getStringMatcher(input).find())) {
                    if (command.getView() != null)
                        command.getView().run(manager);
                    else
                        command.goToFunction(this);
                }
            }
        }
        return null;
    }

    public ViewManager getManager() {
        return this.manager;
    }

    protected void authorizing() {
        AuthenticationView auth = new AuthenticationView(this.getInput());
        auth.run(this.manager);
    }

    protected void back() {

    }

    protected void help(boolean isLoggedIn) {

    }

    protected void logout() {

    }

    public String getInput() {
        return this.input;
    }

    public void printError() {

    }
}
