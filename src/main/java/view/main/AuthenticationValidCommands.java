package view.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AuthenticationValidCommands {
    CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)"),
    LoginAccount("login\\s+(.*)"),
    Back("back"),
    Exit("Exit"),
    Help("help");
    private final Pattern commandPattern;
    //private boolean isLoggedIn;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    AuthenticationValidCommands(String output) {
        //   this.isLoggedIn = isLoggedIn;
        this.commandPattern = Pattern.compile(output);
    }

    public static List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        if (!isLoggedIn) {
            list.add("create account [type] [username]");
            list.add("login [username]");
        } else
            list.add("logout");
        return list;
    }
}
