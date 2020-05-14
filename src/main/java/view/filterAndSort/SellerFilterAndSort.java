package view.filterAndSort;

import view.ViewManager;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class SellerFilterAndSort extends FilterAndSort {
    protected Map<String, String> filterCategoryForController;
    protected Map<String, String> filterOrderForController;

    public SellerFilterAndSort(ViewManager manager) {
        super(manager);
        filterCategoryForController = new HashMap<>();
        filterOrderForController = new HashMap<>();
    }

    @Override
    protected void init() {
        /**@
         * for orders
         */
        filterFields.put(1, "date");
        filterFields.put(2, "total price");
        filterFields.put(3, "product name");
        filterFields.put(4, "username");
        filterFields.put(5, "category name");
        filterFields.put(6,"category feature");
        sortField.put(1, "date");
        sortField.put(2, "total price");
        /**@
         * for category
         */


    }

    protected void filterWithAvailableFilter(Matcher matcher) {
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
        if (chose < 5) {
            filterOrderForController.put(filterFields.get(chose), value);
            return;
        }
        filterCategoryForController.put(filterFields.get(chose), value);
    }

    protected void disableSelectedFilter(Matcher matcher) {
        matcher.find();
        int chose = Integer.parseInt(matcher.group(1)) - 1;
        if (chose >= filterFields.size()) {
            manager.inputOutput.println("enter the number exist in list");
            return;
        }
        if (filterOrderForController.containsKey(filterFields.get(chose))) {
            filterForController.remove(filterFields.get(chose));
            return;
        }
        if (filterCategoryForController.containsKey(filterFields.get(chose)))
            filterCategoryForController.remove(filterFields.get(chose));
    }

    protected void showAvailableFilter() {
        filterFields.forEach((number, filed) -> manager.inputOutput.println("" + number + ". " + filed));
    }

    protected void showAvailableSort() {
        manager.inputOutput.println("these are for sorting ordering.");
        for (Map.Entry<Integer, String> entry : sortField.entrySet()) {
            if (entry.getKey() == 3)
                manager.inputOutput.println("these are for sorting categories.");
            manager.inputOutput.println(entry.getKey() + ". " + entry.getValue());
        }

    }

    protected void showCurrentFilters() {
        manager.inputOutput.println("these are for filtering ordering.");
        for (Map.Entry<Integer, String> entry : filterFields.entrySet()) {
            if (entry.getKey() == 6)
                manager.inputOutput.println("these are for filtering categories.");
            manager.inputOutput.println(entry.getKey() + ". " + entry.getValue());
        }
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

    public Map<String, String> getFilterCategoryForController() {
        return filterCategoryForController;
    }

    public Map<String, String> getFilterOrderForController() {
        return filterOrderForController;
    }
}
