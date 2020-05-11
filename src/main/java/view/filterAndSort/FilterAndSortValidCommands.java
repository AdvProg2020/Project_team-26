package view.filterAndSort;

import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FilterAndSortValidCommands {
    ShowAvailableFilter("show\\s+available\\s+filters") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.showAvailableFilter(Pattern.compile(ShowAvailableFilter.toString()).matcher(page.getInput()));

        }
    },
    FilterWithAvailableFilter("filter\\s+(.*)") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.filterWithAvailableFilter(Pattern.compile(FilterWithAvailableFilter.toString()).matcher(page.getInput()));

        }
    },
    ShowCurrentFilters("disable\\s+filter\\s+(.*)") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.showCurrentFilters();

        }
    },
    DisableASelectedFilters("disable\\s+filter\\s+(.*)") {
        @Override
        public void goToFunction(FilterAndSort page) {
            page.disableSelectedFilter(Pattern.compile(DisableASelectedFilters.toString()).matcher(page.getInput()));
        }
    },
    ShowAvailableSorts("show\\s+available\\s+sorts") {
        @Override
        public void goToFunction(FilterAndSort page) {
        //    page.ShowAvailableSorts();

        }
    },
    Sort("sort\\s+(.*)") {
        @Override
        public void goToFunction(FilterAndSort page) {
           // page.Sort(Pattern.compile(Sort.toString()).matcher(page.getInput()));
        }
    },
    CurrentSort("current\\s+sort") {
        @Override
        public void goToFunction(FilterAndSort page) {
            //page.currentSort();
        }
    },
    DisableSort("disable\\s+sort") {
        @Override
        public void goToFunction(FilterAndSort page) {
         //   page.disableSort(Pattern.compile(DisableSort.toString()).matcher(page.getInput()));
        }
    };

    private final Pattern commandPattern;

    public abstract void goToFunction(FilterAndSort page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }


    FilterAndSortValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
