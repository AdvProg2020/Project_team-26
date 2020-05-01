package view.offs.filter;
import view.*;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class FilterIView extends View implements IView {
    EnumSet<FilterViewValidCommands> validCommands;
    public FilterIView(ViewManager manager){
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
    protected void showAvailableFilter(Matcher matcher) {

    }
    protected void showCurrentFilters() {

    }
}
