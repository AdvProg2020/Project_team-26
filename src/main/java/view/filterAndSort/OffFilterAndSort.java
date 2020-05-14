package view.filterAndSort;

import view.ViewManager;

import java.util.regex.Matcher;

public class OffFilterAndSort extends FilterAndSort {
    public OffFilterAndSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        filterFields.put(1, "date");
        filterFields.put(2, "product name");
        sortField.put(1, "date");
    }

    protected void filterWithAvailableFilter(Matcher matcher) {
        matcher.find();
        int chose = Integer.parseInt(matcher.group(1)) - 1;
        if (chose >= filterFields.size()) {
            manager.inputOutput.println("enter the number exist in list");
            return;
        }
        manager.inputOutput.println("enter the filtering you want depending on the filed(for date MM/DD/YY)");
        manager.inputOutput.println("if you want to have more filter or it is a BAZE seperate them by - like" +
                "3900-6700");
        String value = manager.inputOutput.nextLine();
        filterForController.put(filterFields.get(chose), value);
    }

    protected void disableSelectedFilter(Matcher matcher) {
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
}
