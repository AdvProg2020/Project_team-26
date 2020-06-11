package view.terminal.filterAndSort;

import view.terminal.ViewManager;

public class RequestSort extends Sort {
    public RequestSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        sortField.put(1, "date");
        fieldNameForSort = "date";
        isAscending = true;
    }


}
