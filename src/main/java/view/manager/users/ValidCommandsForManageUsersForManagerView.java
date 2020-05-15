package view.manager.users;

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
            page.deleteUser(Pattern.compile(DeleteUser.toString()).matcher(page.getInput()));
        }
    },
    CreateManagerProfile("create\\s+manager\\s+profile") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            page.createManagerProfile();
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            if (page.getManager().getIsUserLoggedIn()) {
                page.logOut();
                return;
            }
            page.getManager().printError();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            page.help();
        }
    }, ShowAll("show all") {
        @Override
        public void goToFunction(ManageUsersForManager page) {
            page.showAll();
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
