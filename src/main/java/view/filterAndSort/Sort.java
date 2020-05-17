package view.filterAndSort;

import view.View;
import view.ViewManager;

import java.util.*;
import java.util.regex.Matcher;

public abstract class Sort extends View {
    private EnumSet<SortValidCommands> validCommands;
    protected String fieldNameForSort;
    protected boolean isAscending;
    protected Map<Integer, String> sortField;

    public Sort(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(SortValidCommands.class);
        sortField = new HashMap<>();
        this.fieldNameForSort = new String();
    }

    @Override
    public void run() {
        showAvailableSort();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            isDone = false;
            for (SortValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    protected abstract void init();

    public String getFieldNameForSort() {
        return fieldNameForSort;
    }

    public boolean isAscending() {
        return isAscending;
    }

    protected void showAvailableSort() {
        manager.inputOutput.println("sort are : ");
        sortField.forEach((field, value) -> manager.inputOutput.println("" + field + ". " + value));
    }

    protected void showCurrentSort() {
        manager.inputOutput.println(fieldNameForSort);
    }

    protected void disableSelectedSort() {
        fieldNameForSort = new String();
    }


    protected void sortWithAvailableSort(Matcher matcher) {
        matcher.find();
        int chose = Integer.parseInt(matcher.group(1)) - 1;
        if (chose >= sortField.size()) {
            manager.inputOutput.println("enter the number exist in list");
            return;
        }
        fieldNameForSort = sortField.get(chose);
        manager.inputOutput.println("do you want to sort ascending. if type yes it will be ascending" +
                "and if no otherwise");
        if (manager.inputOutput.nextLine().trim().equalsIgnoreCase("yes")) {
            isAscending = true;
            return;
        }
        isAscending = false;
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("show available sorts");
        commandList.add("sort [number]");
        commandList.add("current sort");
        commandList.add("disable sort");
        if (manager.getIsUserLoggedIn())
            commandList.add("logout");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }
}
