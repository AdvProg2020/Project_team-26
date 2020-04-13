package view.allOffsView;

import view.*;

import java.util.EnumSet;

public class AllOffsView extends View {
    EnumSet<AllOffsValidCommands> validCommands;

    public AllOffsView() {
        validCommands = EnumSet.allOf(AllOffsValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
