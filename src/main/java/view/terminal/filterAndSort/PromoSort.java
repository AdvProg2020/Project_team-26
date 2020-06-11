package view.terminal.filterAndSort;

import view.terminal.ViewManager;

public class PromoSort extends Sort {
    public PromoSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        sortField.put(1, "starDate");
        sortField.put(2, "endDate");
        sortField.put(3, "percent");
        sortField.put(4, "maxDiscount");
        sortField.put(5, "max_valid_use");
        fieldNameForSort = "maxValidUse";
        isAscending = true;
    }
}
