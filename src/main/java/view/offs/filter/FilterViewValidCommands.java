package view.offs.filter;

import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FilterViewValidCommands {
    ShowAvailableFilter("show\\s+available\\s+filters"),
    FilterWithAvailableFilter("filter\\s+(.*)"),
    ShowCurrentFilters("disable\\s+filter\\s+(.*)"),
    DisableASelectedFilters("disable\\s+filter\\s+(.*)");

    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    FilterViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
