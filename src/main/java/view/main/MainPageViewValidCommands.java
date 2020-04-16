package view.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainPageViewValidCommands {
    Back("back"),
    Exit("Exit"),
    ShowProducts("products"),
    ShowOffs("offs"),
    Help("help");
    private final Pattern commandPattern;
    //private boolean isLoggedIn;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    MainPageViewValidCommands(String output) {
        //this.isLoggedIn = isLoggedIn;
        this.commandPattern = Pattern.compile(output);
    }

    public static List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(AuthenticationValidCommands.commands(isLoggedIn));
        list.add("products");
        list.add("offs");
        list.add("back");
        list.add("Exit");
        list.add("help");
        return list;
    }
}
