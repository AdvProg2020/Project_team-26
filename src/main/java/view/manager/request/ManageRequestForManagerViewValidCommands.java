package view.manager.request;

import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageRequestForManagerViewValidCommands {
    DetailOfRequest("details\\s+(.*)"),
    AcceptTheRequest("accept\\s+(.*)"),
    DeclineTheRequest("decline\\s+(.*)");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    ManageRequestForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

    public List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }


}
