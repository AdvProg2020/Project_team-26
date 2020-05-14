package view.filterAndSort;

import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FilterAndSortValidCommands {
    ShowAvailableFilter("show\\s+available\\s+filters") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.showAvailableFilter();

        }
    },
    FilterWithAvailableFilter("filter\\s+(\\d+)") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.filterWithAvailableFilter(Pattern.compile(FilterWithAvailableFilter.toString()).matcher(page.getInput()));

        }
    },
    ShowCurrentFilters("current\\s+filters") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.showCurrentFilters();

        }
    },
    DisableASelectedFilters("disable\\s+filter\\s+(\\d+)") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.disableSelectedFilter(Pattern.compile(DisableASelectedFilters.toString()).matcher(page.getInput()));
        }
    },
    ShowAvailableSorts("show\\s+available\\s+sorts") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.showAvailableSort();

        }
    },
    Sort("sort\\s+(\\d+)") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.sortWithAvailableSort(Pattern.compile(Sort.toString()).matcher(page.getInput()));
        }
    },
    CurrentSort("current\\s+sort") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.showCurrentSort();
        }
    },
    DisableSort("disable\\s+sort") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.disableSelectedSort();
        }
    };

    private final Pattern commandPattern;
    private final String value;

    public abstract void goToFunction(FilterAndSort page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }


    FilterAndSortValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }

    @Override
    public String toString() {
        return value;
    }
}
