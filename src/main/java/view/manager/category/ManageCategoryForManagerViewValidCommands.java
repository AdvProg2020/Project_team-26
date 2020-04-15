package view.manager.category;

import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageCategoryForManagerViewValidCommands {
    EditCategoryForManager("edit\\s+(.*)"),
    AddCategoryForManager("Add\\s+(.*)"),
    RemoveCategoryForManager("remove\\s+(.*)");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    ManageCategoryForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

    public List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }

}
