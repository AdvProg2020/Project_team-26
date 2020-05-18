package view.filterAndSort;

import view.ViewManager;

import java.util.regex.Matcher;

public class OrderSort extends Sort {

    public OrderSort(ViewManager manager) {
        super(manager);
    }

    protected void init() {
        sortField.put(1, "date");
    }
}
