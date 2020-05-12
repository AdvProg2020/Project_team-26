package view.offs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllOffsValidCommands {
    ShowProductWithId("show\\s+product\\s+(.*)") {
        @Override
        public void goToFunction(AllOffView page) {
            page.showProductWithId(Pattern.compile(ShowProductWithId.toString()).matcher(page.getInput()));
        }
    },
    Sorting("sorting") {
        @Override
        public void goToFunction(AllOffView page) {
            page.sort();
        }
    },
    Filtering("filtering") {
        @Override
        public void goToFunction(AllOffView page) {
            page.filter();
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
