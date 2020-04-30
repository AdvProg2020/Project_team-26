package view.manager.request;

import view.View;
import view.manager.users.ManageUsersForManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageRequestForManagerViewValidCommands {
    DetailOfRequest("details\\s+(.*)") {
        @Override
        public void goToFunction(ManageRequestForManagerViewI page) {
            page.detailOfRequest(Pattern.compile(DetailOfRequest.toString()).matcher(page.getInput()));
        }
    },

    AcceptTheRequest("accept\\s+(.*)") {
        @Override
        public void goToFunction(ManageRequestForManagerViewI page) {
            page.acceptTheRequest(Pattern.compile(AcceptTheRequest.toString()).matcher(page.getInput()));
        }
    },

    DeclineTheRequest("decline\\s+(.*)") {
        @Override
        public void goToFunction(ManageRequestForManagerViewI page) {
            page.declineTheRequest(Pattern.compile(DeclineTheRequest.toString()).matcher(page.getInput()));
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(ManageRequestForManagerViewI page) {
            if (page.getManager().getIsUserLoggedin()) {
                page.logOut();
                return;
            }
            page.getManager().printError();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(ManageRequestForManagerViewI page) {
            page.help(page.getManager().getIsUserLoggedin());
        }
    };
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(ManageRequestForManagerViewI page);

    ManageRequestForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }


}
