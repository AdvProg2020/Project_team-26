package view.offs;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class AllOffsView extends View implements view {
    EnumSet<AllOffsValidCommands> validCommands;

    public AllOffsView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(AllOffsValidCommands.class);
    }

    @Override
    public View run() {
        return null;
    }

    protected void showProductWithId(Matcher matcher) {

    }
}
