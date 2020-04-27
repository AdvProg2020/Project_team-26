package view.offs;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class AllOffsViewI extends View implements ViewI {
    EnumSet<AllOffsValidCommands> validCommands;

    public AllOffsViewI(ViewManager manager) {
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
