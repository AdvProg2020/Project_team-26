package view.filterAndSort;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public abstract class FilterAndSort extends View {
    private Map<String, String> filter;
    private EnumSet<FilterAndSortValidCommands> validCommands;
    private String fieldNameForSort;
    private boolean isAscending;
    protected boolean isDefault;

    public FilterAndSort(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(FilterAndSortValidCommands.class);
        filter = new HashMap<>();
        this.fieldNameForSort = new String();
    }

    @Override
    public void run() {
    }

    public String getFieldNameForSort() {
        return fieldNameForSort;
    }

    public boolean isAscending() {
        return isAscending;
    }

    abstract protected void disableSelectedFilter(Matcher matcher);

    abstract protected void filterWithAvailableFilter(Matcher matcher);

    abstract protected void showAvailableFilter(Matcher matcher);

    abstract protected void showCurrentFilters();

    public Map<String, String> getFilter(){
        return filter;

    }
}
