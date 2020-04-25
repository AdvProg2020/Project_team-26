package view.manager.users;

import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ValidCommandsForManageUsersForManagerView {
    ViewUser("view\\s+(.*)") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            page.viewUser(Pattern.compile(ViewUser.toString()).matcher(page.getInput()));
        }
    },
    DeleteUser("delete\\s+user\\s+(.*)") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            page.viewUser(Pattern.compile(ViewUser.toString()).matcher(page.getInput()));
        }
    },
    CreateManagerProfile("create\\s+manager\\s+profile") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            page.viewUser(Pattern.compile(ViewUser.toString()).matcher(page.getInput()));
        }
    },
    ManageAllProduct("manage\\s+all\\s+products") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            page.viewUser(Pattern.compile(ViewUser.toString()).matcher(page.getInput()));
        }
    },
    Edit("edit\\s+(.*)") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            page.viewUser(Pattern.compile(ViewUser.toString()).matcher(page.getInput()));
        }
    };

    private final Pattern commandPattern;

    public abstract void goToFunction(ManageUsersForManager page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    ValidCommandsForManageUsersForManagerView(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
