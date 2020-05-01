package view.products.all.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FilterViewValidCommands {
    ShowAvailableFilter("show\\s+available\\s+filters") {
        @Override
        public void goToFunction(FilterIView page) {
            page.showAvailableFilter();

        }
    },
    FilterWithAvailableFilter("filter\\s+(.*)") {
        @Override
        public void goToFunction(FilterIView page) {
            page.filterWithAvailableFilter(Pattern.compile(FilterWithAvailableFilter.toString()).matcher(page.getInput()));

        }
    },
    ShowCurrentFilters("disable\\s+filter\\s+(.*)") {
        @Override
        public void goToFunction(FilterIView page) {
            page.showCurrentFilters();

        }
    },
    DisableASelectedFilters("disable\\s+filter\\s+(.*)") {
        @Override
        public void goToFunction(FilterIView page) {
            page.disableSelectedFilter(Pattern.compile(DisableASelectedFilters.toString()).matcher(page.getInput()));
        }
    };

    private final Pattern commandPattern;

    public abstract void goToFunction(FilterIView page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    FilterViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
