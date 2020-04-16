package view.offs;

import view.View;
import view.offs.filter.FilterView;
import view.offs.sort.SortView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllOffsValidCommands {
    ShowProductWithId("show\\s+product\\s+(.*)", null),
    Sorting("sorting", new SortView()),
    Filtering("filtering", new FilterView());
    private final Pattern commandPattern;
    private final View view;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    AllOffsValidCommands(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

}
