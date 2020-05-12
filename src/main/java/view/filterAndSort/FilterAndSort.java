package view.filterAndSort;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public abstract class FilterAndSort extends View {
    Map<String, String> sortAndFilter;
    EnumSet<FilterAndSortValidCommands> validCommands;

    public FilterAndSort(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(FilterAndSortValidCommands.class);
        sortAndFilter = new HashMap<>();
    }

    @Override
    public void run() {
    }

    abstract protected void disableSelectedFilter(Matcher matcher);

    abstract protected void filterWithAvailableFilter(Matcher matcher);

    abstract protected void showAvailableFilter(Matcher matcher);

    abstract protected void showCurrentFilters();

    abstract public Map<String, String> getSortAndFilter();
}
