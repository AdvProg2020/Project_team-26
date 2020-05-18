package view.filterAndSort;

import view.View;
import view.ViewManager;
import view.seller.SellerAccountViewValidCommands;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public abstract class FilterAndSort extends View {
    protected Map<String, String> filterForController;
    private EnumSet<FilterAndSortValidCommands> validCommands;
    protected String fieldNameForSort;
    protected boolean isAscending;
    protected Map<Integer, String> filterFields;
    protected Map<Integer, String> sortField;

    public FilterAndSort(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(FilterAndSortValidCommands.class);
        filterForController = new HashMap<>();
        filterFields = new HashMap<>();
        sortField = new HashMap<>();
        this.fieldNameForSort = new String();
        init();
    }

    @Override
    public void run() {
        manager.inputOutput.println("filter and sort menu :");
        showAvailableFilter();
        showAvailableSort();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            isDone = false;
            for (FilterAndSortValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    protected abstract void init();

    public String getFieldNameForSort() {
        return fieldNameForSort;
    }

    public boolean isAscending() {
        return isAscending;
    }

    abstract protected void disableSelectedFilter(Matcher matcher);

    abstract protected void showAvailableFilter();

    abstract protected void showCurrentFilters();

    abstract protected void showAvailableSort();

    abstract protected void showCurrentSort();

    abstract void filterWithAvailableFilter(Matcher matcher);

    abstract protected void disableSelectedSort();

    abstract protected void help();


    abstract protected void sortWithAvailableSort(Matcher matcher);

    public Map<String, String> getFilterForController() {
        return filterForController;
    }
}
