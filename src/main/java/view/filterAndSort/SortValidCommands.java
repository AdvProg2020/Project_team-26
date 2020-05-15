package view.filterAndSort;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SortValidCommands {
    ShowAvailableSorts("show\\s+available\\s+sorts") {
        @Override
        public void goToFunction(Sort page) {
            page.showAvailableSort();

        }
    },
    Sort("sort\\s+(\\d+)") {
        @Override
        public void goToFunction(Sort page) {
            page.sortWithAvailableSort(Pattern.compile(Sort.toString()).matcher(page.getInput()));
        }
    },
    CurrentSort("current\\s+sort") {
        @Override
        public void goToFunction(Sort page) {
            page.showCurrentSort();
        }
    },
    DisableSort("disable\\s+sort") {
        @Override
        public void goToFunction(Sort page) {
            page.disableSelectedSort();
        }
    };

    private final Pattern commandPattern;
    private final String value;

    public abstract void goToFunction(Sort page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }


    SortValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }

    @Override
    public String toString() {
        return value;
    }
}
