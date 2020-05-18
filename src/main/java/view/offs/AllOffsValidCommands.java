package view.offs;

import view.main.MainPageView;
import view.products.single.SingleProductView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllOffsValidCommands {
    ShowProductWithId("^show\\s+product\\s+(\\d+)$") {
        @Override
        public void goToFunction(AllOffView page) {
            page.showProductWithId(Pattern.compile(ShowProductWithId.toString()).matcher(page.getInput()));
        }
    },
    Sorting("^sorting$") {
        @Override
        public void goToFunction(AllOffView page) {
            page.sort();
        }
    },
    Filtering("^filtering$") {
        @Override
        public void goToFunction(AllOffView page) {
            page.filter();
        }
    },
    ShowProducts("^products$") {
        @Override
        public void goToFunction(AllOffView page) {
            page.product();
        }
    },

    Help("^help$") {
        @Override
        public void goToFunction(AllOffView page) {
            page.help();
        }
    },
    Logout("^logout$") {
        @Override
        public void goToFunction(AllOffView page) {
            if (page.getManager().getIsUserLoggedIn()) {
                page.logOut();
                return;
            }
            page.getManager().inputOutput.println("you are mot logged in");
        }
    },
    CreateAccount("^create\\s+account\\s+(buyer|seller|manager)\\s+(.*)") {
        @Override
        public void goToFunction(AllOffView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.register();
                return;
            }
            page.getManager().inputOutput.println("first logout");
        }
    },
    LoginAccount("^login\\s+(.*)") {
        @Override
        public void goToFunction(AllOffView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.login();
                return;
            }
            page.getManager().inputOutput.println("first logout");
        }
    }, ShowAll("^show all$") {
        @Override
        public void goToFunction(AllOffView page) {
            page.showAll();
        }
    };
    private final Pattern commandPattern;
    private String value;


    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    public abstract void goToFunction(AllOffView page);

    AllOffsValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        this.value = output;
    }


    @Override
    public String toString() {
        return value;
    }
}
