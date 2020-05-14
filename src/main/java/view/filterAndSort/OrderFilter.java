package view.filterAndSort;

import view.ViewManager;

import java.util.regex.Matcher;

public class OrderFilter extends FilterAndSort {

    public OrderFilter(ViewManager manager) {
        super(manager);
        init();
    }

    protected void init() {
        filterFields.put(1, "date");
        filterFields.put(2, "total price");
        filterFields.put(3, "product name");
        filterFields.put(4, "username");
        filterFields.put(5, "category name");
        sortField.put(1, "date");
        sortField.put(2, "total price");
    }
}
