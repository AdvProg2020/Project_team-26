package view.main;

import view.ViewManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainPageViewValidCommands {
    Exit("exit") {
        @Override
        public void goToFunction(MainPageView page) {

        }
    },
    ShowProducts("products") {
        @Override
        public void goToFunction(MainPageView page) {
            page.product();
        }
    },
    ShowOffs("offs") {
        @Override
        public void goToFunction(MainPageView page) {
            page.off();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(MainPageView page) {
            page.help(page.getManager().getIsUserLoggedIn());
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(MainPageView page) {
            page.logout();
        }
    },
    CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)") {
        @Override
        public void goToFunction(MainPageView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.authorizing();
                return;
            }
        }
    },
    LoginAccount("login\\s+(.*)") {
        @Override
        public void goToFunction(MainPageView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.authorizing();
                return;
            }
        }
    };

    private Pattern commandPattern;
    private String output;


    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(MainPageView page);

    MainPageViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        this.output = output;
    }


    @Override
    public String toString() {
        return this.output;
    }
}
