package view.offs.filter;
import view.*;

import view.View;
import view.ViewManager;

import javax.swing.*;
import java.util.EnumSet;
import java.util.regex.Matcher;

public class FilterView extends View implements view {
    EnumSet<FilterViewValidCommands> validCommands;
    public FilterView(){
        validCommands = EnumSet.allOf(FilterViewValidCommands.class);
    }
    @Override
    public View run(ViewManager manager) {
        return null;
    }
    private void disableSelectedFilter(Matcher matcher) {

    }

    private void filterWithAvailableFilter(Matcher matcher) {

    }
    private void showAvailableFilter(Matcher matcher) {

    }
    private void showCurrentFilters(Matcher matcher) {

    }
}
