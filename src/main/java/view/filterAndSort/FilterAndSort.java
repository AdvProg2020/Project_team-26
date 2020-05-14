package view.filterAndSort;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public abstract class FilterAndSort extends View {
    protected Map<String, String> filterForController;
    private EnumSet<FilterAndSortValidCommands> validCommands;
    protected String fieldNameForSort;
    protected boolean isAscending;
    protected Map<Integer, String> filterFields;
    protected Map<Integer, String> sortField;

    public FilterAndSort(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(FilterAndSortValidCommands.class);
        filterForController = new HashMap<>();
        filterFields = new HashMap<>();
        sortField = new HashMap<>();
        this.fieldNameForSort = new String();
    }

    @Override
    public void run() {
    }

    protected abstract void init();

    public String getFieldNameForSort() {
        return fieldNameForSort;
    }

    public boolean isAscending() {
        return isAscending;
    }

    protected void disableSelectedFilter(Matcher matcher) {
        matcher.find();
        int chose = Integer.parseInt(matcher.group(1)) - 1;
        if (chose >= filterFields.size()) {
            manager.inputOutput.println("enter the number exist in list");
            return;
        }
        manager.inputOutput.println("enter the filtering you want depending on the filed(for date MM/DD/YY) ");
        manager.inputOutput.println("if you want to have more filter or it is a BAZE seperate them by - like" +
                "3900-6700");
        String value;
        value = manager.inputOutput.nextLine();
        filterForController.put(filterForController.get(chose), value);
    }

    protected void filterWithAvailableFilter(Matcher matcher) {
        matcher.find();
        int chose = Integer.parseInt(matcher.group(1)) - 1;
        if (chose >= filterFields.size()) {
            manager.inputOutput.println("enter the number exist in list");
            return;
        }
        if (filterForController.containsKey(filterFields.get(chose)))
            filterForController.remove(filterFields.get(chose));
    }

    protected void showAvailableFilter() {
        filterFields.forEach((number, filed) -> manager.inputOutput.println("" + number + ". " + filed));
    }

    protected void showCurrentFilters() {
        filterForController.forEach((field, value) -> manager.inputOutput.println("" + field + ". " + value));
    }

    protected void showAvailableSort() {
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

    public Map<String, String> getFilterForController() {
        return filterForController;
    }
}
