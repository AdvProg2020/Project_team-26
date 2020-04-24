package view.offs;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class AllOffsView extends View implements view {
    EnumSet<AllOffsValidCommands> validCommands;

    public AllOffsView() {
        validCommands = EnumSet.allOf(AllOffsValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    private void showProductWithId(Matcher matcher) {

    }

    private void sorting(Matcher matcher) {

    }

    private void filtering(Matcher matcher) {

    }

}
