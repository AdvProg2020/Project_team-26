package view.offs.filter;

import view.View;
import view.ViewManager;

import java.util.EnumSet;

public class FilterView extends View {
    EnumSet<FilterViewValidCommands> validCommands;
    public FilterView(){
        validCommands = EnumSet.allOf(FilterViewValidCommands.class);
    }
    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
