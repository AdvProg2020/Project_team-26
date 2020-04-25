package view.manager.request;

import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageRequestForManagerViewValidCommands {
    DetailOfRequest("details\\s+(.*)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.detailOfRequest(Pattern.compile(DetailOfRequest.toString()).matcher(page.getInput()));
        }
    },

    AcceptTheRequest("accept\\s+(.*)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.acceptTheRequest(Pattern.compile(AcceptTheRequest.toString()).matcher(page.getInput()));
        }
    },

    DeclineTheRequest("decline\\s+(.*)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.declineTheRequest(Pattern.compile(DeclineTheRequest.toString()).matcher(page.getInput()));
        }
    };
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(ManageRequestForManagerView page);

    ManageRequestForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

    public List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }


}
