package view.manager.category;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageCategoryForManagerViewValidCommands {
    EditCategoryForManager("edit\\s+(.*)") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.editCategoryForManager(Pattern.compile(EditCategoryForManager.toString()).matcher(page.getInput()));
        }
    },
    AddCategoryForManager("add\\s+(.*)") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.addCategoryForManager(Pattern.compile(AddCategoryForManager.toString()).matcher(page.getInput()));
        }
    },
    RemoveCategoryForManager("remove\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.RemoveCategoryForManager(Pattern.compile(RemoveCategoryForManager.toString()).matcher(page.getInput()));
        }
    }, Logout("logout") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.logOut();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.help();
        }
    },
    ViewSubCategories("view\\s+sub\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.viewSubCategories(Pattern.compile(ViewSubCategories.toString()).matcher(page.getInput()));
        }
    },
    ShowAll("show all") {
        @Override
        public void goToFunction(ManageCategoryForManagerView page) {
            page.showAll();
        }

    };
    private final Pattern commandPattern;
    private final String value;

    @Override
    public String toString() {
        return value;
    }

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    ManageCategoryForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }

    public abstract void goToFunction(ManageCategoryForManagerView page);


}
