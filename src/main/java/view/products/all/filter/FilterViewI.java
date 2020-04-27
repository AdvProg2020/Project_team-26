package view.products.all.filter;

import view.*;
import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class FilterViewI extends View implements ViewI {
    EnumSet<FilterViewValidCommands> validCommands;

    public FilterViewI(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(FilterViewValidCommands.class);
    }

    @Override
    public View run() {
        return null;
    }

    protected void disableSelectedFilter(Matcher matcher) {

    }

    protected void filterWithAvailableFilter(Matcher matcher) {

    }

    protected void showAvailableFilter() {

    }

    protected void showCurrentFilters() {

    }
}
