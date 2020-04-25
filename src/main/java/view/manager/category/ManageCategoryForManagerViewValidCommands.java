package view.manager.category;

import view.View;
import view.ViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageCategoryForManagerViewValidCommands {
    EditCategoryForManager("edit\\s+(.*)") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.EditCategoryForManager(Pattern.compile(EditCategoryForManager.toString()).matcher(page.getInput()));
        }
    },
    AddCategoryForManager("Add\\s+(.*)") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.addCategoryForManager(Pattern.compile(AddCategoryForManager.toString()).matcher(page.getInput()));
        }
    },
    RemoveCategoryForManager("remove\\s+(.*)") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.RemoveCategoryForManager(Pattern.compile(RemoveCategoryForManager.toString()).matcher(page.getInput()));
        }
    };
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    ManageCategoryForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

    public abstract void goToFunction(ManageCategoryForManagerView page);

    public List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }

}
