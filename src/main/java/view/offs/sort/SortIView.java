package view.offs.sort;
import view.*;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class SortIView extends View implements IView {
    EnumSet<SortingViewValidCommands> validCommands;

    public SortIView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(SortingViewValidCommands.class);
    }

    @Override
    public View run() {
        return null;
    }

    protected void ShowAvailableSorts() {

    }

    protected void Sort(Matcher matcher) {

    }

    protected void currentSort() {

    }

    protected void disableSort(Matcher matcher) {

    }
}