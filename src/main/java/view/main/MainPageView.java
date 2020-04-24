package view.main;

import view.*;
import view.ViewManager;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Matcher;

public class MainPageView extends View implements view{
    Set<MainPageViewValidCommands> validCommand;

    public MainPageView() {
        validCommand = EnumSet.allOf(MainPageViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    private void authorizing() {

    }

    private void back() {

    }

    private void help(ArrayList<String> CommandsFormat) {

    }

    private void showOffs(Matcher matcher) {

    }

    private void showProducts(Matcher matcher) {

    }

    private void logout(Matcher matcher) {

    }
}
