package view.filterAndSort;

import view.ViewManager;

import java.util.regex.Matcher;

public class PromoSort extends Sort {
    public PromoSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        sortField.put(1, "start_date");
        sortField.put(2, "end_date");
        sortField.put(3, "discount_percent");
        sortField.put(4, "max_discount");
        sortField.put(5, "max_valid_use");
        fieldNameForSort = "max_discount";
        isAscending = true;
    }
}
