package view.offs.sort;
import view.*;

import view.View;
import view.ViewManager;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class SortView extends View implements view{
    EnumSet<SortingViewValidCommands> validCommands;

    public SortView() {
        validCommands = EnumSet.allOf(SortingViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    private void ShowAvailableSorts(Matcher matcher) {

    }

    private void Sort(Matcher matcher) {

    }

    private void CurrentSort(Matcher matcher) {

    }

    private void DisableSort(Matcher matcher) {

    }
}
