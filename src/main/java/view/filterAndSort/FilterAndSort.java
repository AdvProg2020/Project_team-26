package view.filterAndSort;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.Map;
import java.util.regex.Matcher;

public class FilterAndSort extends View {
    Map<String, String> sortAndFilter;
    EnumSet<FilterAndSortValidCommands> validCommands;

    public FilterAndSort(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(FilterAndSortValidCommands.class);
    }

    @Override
    public View run() {
        return null;
    }

    protected void disableSelectedFilter(Matcher matcher) {

    }

    protected void filterWithAvailableFilter(Matcher matcher) {

    }

    protected void showAvailableFilter(Matcher matcher) {

    }

    protected void showCurrentFilters() {

    }

    public Map<String, String> getSortAndFilter() {
        return sortAndFilter;
    }
}
