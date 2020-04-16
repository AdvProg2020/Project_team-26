package view.products.all.sort;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SortingViewValidCommands {
    ShowAvailableSorts("show\\s+available\\s+sorts"),
    Sort("sort\\s+(.*)"),
    CurrentSort("current\\s+sort"),
    DisableSort("disable\\s+sort");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    SortingViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
