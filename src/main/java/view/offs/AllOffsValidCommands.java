package view.offs;

import view.View;
import view.ViewManager;
import view.manager.ValidCommandsForManagerAccount;
import view.offs.filter.FilterView;
import view.offs.sort.SortView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllOffsValidCommands {
    ShowProductWithId("show\\s+product\\s+(.*)", null){
        @Override
        public void goToFunction(AllOffsView page) {
            page.showProductWithId(Pattern.compile(ShowProductWithId.toString()).matcher(page.getInput()));
        }
    },
    Sorting("sorting", new SortView(AllOffsValidCommands.manager)){
        @Override
        public void goToFunction(AllOffsView page) {

        }
    },
    Filtering("filtering", new FilterView(AllOffsValidCommands.manager)){
        @Override
        public void goToFunction(AllOffsView page) {

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

    public abstract void goToFunction(AllOffsView page);

    AllOffsValidCommands(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

}
