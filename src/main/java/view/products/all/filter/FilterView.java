package view.products.all.filter;

import controller.interfaces.product.ISearchAndFilterAndSort;
import view.*;
import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class FilterView extends View implements IView {
    EnumSet<FilterViewValidCommands> validCommands;
    ISearchAndFilterAndSort controller;

    public FilterView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(FilterViewValidCommands.class);
    }

    @Override
    public View run() {
        while (true) {
            if ((super.input = (manager.inputOutput.nextLine()).trim()).matches("back"))
                break;
            for (FilterViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    protected void disableSelectedFilter(Matcher matcher) {
        matcher.find();
    }

    protected void filterWithAvailableFilter(Matcher matcher) {
        matcher.find();
    }

    protected void showAvailableFilter() {
        manager.showList(controller.getAvailableFilter(manager.getTocken()));
    }

    protected void showCurrentFilters() {
        manager.showList(controller.getCurrentActiveFilters(manager.getTocken()));
    }
}
