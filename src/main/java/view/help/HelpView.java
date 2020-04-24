package view.help;

import view.*;

import view.ViewManager;
import view.View;

import java.util.EnumSet;

public class HelpView extends View implements view {
    EnumSet<HelpViewCommands> validCommand;

    public HelpView() {
        validCommand = EnumSet.allOf(HelpViewCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
