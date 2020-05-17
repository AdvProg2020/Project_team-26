package view.filterAndSort;

import view.ViewManager;

import java.util.regex.Matcher;

public class RequestSort extends Sort {
    public RequestSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        sortField.put(1, "date");
    }


}
