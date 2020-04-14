package view.offs.sort;

import view.*;

import java.util.EnumSet;

public class Sort extends View {
    EnumSet<SortingViewValidCommands> validCommands;

    public Sort() {
        validCommands = EnumSet.allOf(SortingViewValidCommands.class);
    }

    @Override
    public void run(ViewManager manager) {
        return ;
    }
}
