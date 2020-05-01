package view.manager.category;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageCategoryForManagerViewValidCommands {
    EditCategoryForManager("edit\\s+(.*)") {
        @Override
        public void goToFunction(ManageCategoryForManagerIView page) {
            page.editCategoryForManager(Pattern.compile(EditCategoryForManager.toString()).matcher(page.getInput()));
        }
    },
    AddCategoryForManager("add\\s+(.*)") {
        @Override
        public void goToFunction(ManageCategoryForManagerIView page) {
            page.addCategoryForManager(Pattern.compile(AddCategoryForManager.toString()).matcher(page.getInput()));
        }
    },
    RemoveCategoryForManager("remove\\s+(.*)") {
        @Override
        public void goToFunction(ManageCategoryForManagerIView page) {
            page.RemoveCategoryForManager(Pattern.compile(RemoveCategoryForManager.toString()).matcher(page.getInput()));
        }
    }, Logout("logout") {
        @Override
        public void goToFunction(ManageCategoryForManagerIView page) {
            if (page.getManager().getIsUserLoggedin()) {
                page.logOut();
                return;
            }
            page.getManager().printError();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(ManageCategoryForManagerIView page) {
            page.help(page.getManager().getIsUserLoggedin());
        }
    };
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    ManageCategoryForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

    public abstract void goToFunction(ManageCategoryForManagerIView page);

    public List<String> commands(boolean isLoggedIn) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }

}
