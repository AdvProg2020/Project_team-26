package view.offs;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class AllOffsIView extends View implements IView {
    EnumSet<AllOffsValidCommands> validCommands;

    public AllOffsIView(ViewManager manager) {
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
