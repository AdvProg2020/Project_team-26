package view.terminal.filterAndSort;

import view.terminal.ViewManager;

public class OffSort extends Sort {
    public OffSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        sortField.put(1, "startDate");
        sortField.put(2, "endDate");
        fieldNameForSort = "startDate";
        isAscending = true;
    }
}
