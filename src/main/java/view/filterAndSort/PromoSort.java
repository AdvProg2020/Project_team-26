package view.filterAndSort;

import view.ViewManager;

import java.util.regex.Matcher;

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
