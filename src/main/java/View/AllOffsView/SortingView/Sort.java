package View.AllOffsView.SortingView;

import View.*;

import java.util.EnumSet;

public class Sort extends View {
    EnumSet<SortingViewValidCommands> validCommands;

    public Sort() {
        validCommands = EnumSet.allOf(SortingViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
