package view.products.all.sort;

import controller.interfaces.product.ISearchAndFilterAndSort;
import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class SortView extends View {
    EnumSet<SortingViewValidCommands> validCommands;
    ISearchAndFilterAndSort controller;

    public SortView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(SortingViewValidCommands.class);
    }


    @Override
    public View run() {
        return null;
    }

    protected void ShowAvailableSorts() {
        manager.showList(controller.getAvailableSort(manager.getTocken()));

    }

    protected void Sort(Matcher matcher) {
        matcher.find();

    }

    protected void currentSort() {
        manager.showList(controller.getCurrentActiveSort(manager.getTocken()));

    }

    protected void disableSort(Matcher matcher) {
        matcher.find();

    }
}
