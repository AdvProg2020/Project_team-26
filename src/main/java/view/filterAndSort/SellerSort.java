package view.filterAndSort;

import view.ViewManager;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class SellerSort extends Sort {

    public SellerSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        /**@
         * for orders
         */
        sortField.put(1, "date");
        sortField.put(2, "total price");
        /**@
         * for category
         */


    }


    protected void showAvailableSort() {
        manager.inputOutput.println("these are for sorting ordering.");
        for (Map.Entry<Integer, String> entry : sortField.entrySet()) {
            if (entry.getKey() == 3)
                manager.inputOutput.println("these are for sorting categories.");
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
}
