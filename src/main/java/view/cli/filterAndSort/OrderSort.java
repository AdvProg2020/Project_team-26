package view.cli.filterAndSort;

import view.cli.ViewManager;

public class OrderSort extends Sort {

    public OrderSort(ViewManager manager) {
        super(manager);
    }

    protected void init() {
        sortField.put(1, "date");
        fieldNameForSort = "date";
        isAscending = true;
    }
}
