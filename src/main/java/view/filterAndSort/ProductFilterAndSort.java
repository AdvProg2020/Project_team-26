package view.filterAndSort;

import view.ViewManager;

import java.util.Map;
import java.util.regex.Matcher;

public class ProductFilterAndSort extends FilterAndSort {
    public ProductFilterAndSort(ViewManager manager) {
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

    @Override
    public Map<String, String> getSortAndFilter() {
        return null;
    }
}
