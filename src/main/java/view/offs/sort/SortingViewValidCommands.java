package view.offs.sort;

import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SortingViewValidCommands {
    ShowAvailableSorts("show\\s+available\\s+sorts") {
        @Override
        public void goToFunction(SortViewI page) {
            page.ShowAvailableSorts();

        }
    },
    Sort("sort\\s+(.*)") {
        @Override
        public void goToFunction(SortViewI page) {
            page.Sort(Pattern.compile(Sort.toString()).matcher(page.getInput()));
        }
    },
    CurrentSort("current\\s+sort") {
        @Override
        public void goToFunction(SortViewI page) {
            page.currentSort();
        }
    },
    DisableSort("disable\\s+sort") {
        @Override
        public void goToFunction(SortViewI page) {
            page.disableSort(Pattern.compile(DisableSort.toString()).matcher(page.getInput()));
        }
    };
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(SortViewI page);

    SortingViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
