package view.offs;

import view.View;
import view.ViewManager;
import view.offs.filter.FilterIView;
import view.offs.sort.SortIView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AllOffsValidCommands {
    ShowProductWithId("show\\s+product\\s+(.*)", null){
        @Override
        public void goToFunction(AllOffsIView page) {
            page.showProductWithId(Pattern.compile(ShowProductWithId.toString()).matcher(page.getInput()));
        }
    },
    Sorting("sorting", new SortIView(AllOffsValidCommands.getManager())){
        @Override
        public void goToFunction(AllOffsIView page) {

        }
    },
    Filtering("filtering", new FilterIView(AllOffsValidCommands.getManager())){
        @Override
        public void goToFunction(AllOffsIView page) {

        }
    };
    private final Pattern commandPattern;
    private final View view;
    private static ViewManager manager;

    public static void setManager(ViewManager manager) {
        AllOffsValidCommands.manager = manager;
    }
    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(AllOffsIView page);

    AllOffsValidCommands(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

    public static ViewManager getManager() {
        return manager;
    }
}
