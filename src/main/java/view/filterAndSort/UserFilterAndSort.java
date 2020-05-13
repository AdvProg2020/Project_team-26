package view.filterAndSort;

import view.ViewManager;

import java.util.regex.Matcher;

public class UserFilterAndSort extends FilterAndSort {
    public UserFilterAndSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void disableSelectedFilter(Matcher matcher) {

    }

    @Override
    protected void filterWithAvailableFilter(Matcher matcher) {

    }

    @Override
    protected void showAvailableFilter(Matcher matcher) {

    }

    @Override
    protected void showCurrentFilters() {

    }
}
