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

    public MainPageView(ViewManager manager) {
        super(manager);
        this.manager = manager;
        commands = EnumSet.allOf(MainPageViewValidCommands.class);
        super.input = new String();
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
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
        AuthenticationView auth = new AuthenticationView(manager,super.input);
        auth.run();
    }

    protected void back() {

    }

    protected void help(boolean isLoggedIn) {

    }

    protected void logout() {
        System.out.println("hxvhhdvhsdhJKhuhvduhsKHVUHUIhvkjKJBuvhuihvnKUUDHuhvkKv");

    }

    public void printError() {

    }
}
