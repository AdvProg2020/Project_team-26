package view.filterAndSort;

import view.ViewManager;

import java.util.regex.Matcher;

public class PromoSort extends Sort {
    public PromoSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        sortField.put(1, "start date");
        sortField.put(2, "end date");
        sortField.put(3, "discount percent");
        sortField.put(4, "max discount");
        sortField.put(5, "max valid use");
    }
}
