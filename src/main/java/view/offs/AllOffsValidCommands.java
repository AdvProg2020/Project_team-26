package view.offs;

import view.View;
import view.ViewManager;
import view.offs.filter.FilterViewI;
import view.offs.sort.SortViewI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllOffsValidCommands {
    ShowProductWithId("show\\s+product\\s+(.*)", null){
        @Override
        public void goToFunction(AllOffsViewI page) {
            page.showProductWithId(Pattern.compile(ShowProductWithId.toString()).matcher(page.getInput()));
        }
    },
    Sorting("sorting", new SortViewI(AllOffsValidCommands.manager)){
        @Override
        public void goToFunction(AllOffsViewI page) {

        }
    },
    Filtering("filtering", new FilterViewI(AllOffsValidCommands.manager)){
        @Override
        public void goToFunction(AllOffsViewI page) {

        }
    };
    private final Pattern commandPattern;
    private final View view;
    private static ViewManager manager;

    public void setManager(ViewManager manager) {
        AllOffsValidCommands.manager = manager;
    }
    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(AllOffsViewI page);

    AllOffsValidCommands(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

}
