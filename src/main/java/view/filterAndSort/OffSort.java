package view.filterAndSort;

import view.ViewManager;

public class OffSort extends Sort {
    public OffSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        sortField.put(1, "start_date");
        sortField.put(2, "end_date");
        fieldNameForSort = "start_date";
        isAscending = true;

    }
}
