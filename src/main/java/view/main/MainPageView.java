package view.main;

import view.*;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class MainPageView extends View {
    EnumSet<MainPageViewValidCommands> validCommand;

    public MainPageView() {
        validCommand = EnumSet.allOf(MainPageViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    private void back(Matcher matcher) {

    }

    private void help(Matcher matcher) {

    }

    private void showOffs(Matcher matcher) {

    }

    private void showProducts(Matcher matcher) {

    }
}
