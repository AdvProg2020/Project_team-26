package view.manager.users;

import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ValidCommandsForManageUsersForManagerView {
    ViewUser("view\\s+(.*)"),
    DeleteUser("delete\\s+user\\s+(.*)"),
    CreateManagerProfile("create\\s+manager\\s+profile"),
    ManageAllProduct("manage\\s+all\\s+products"),
    Edit("edit\\s+(.*)");

    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    ValidCommandsForManageUsersForManagerView(String output) {
        this.commandPattern = Pattern.compile(output);
    }

    public List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
}
