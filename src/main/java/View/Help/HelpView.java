package View.Help;

import View.ManagerAccountView.ValidCommandsForManagerAccount;
import View.ViewManager;
import View.View;

import java.util.EnumSet;

public class HelpView extends View {
    EnumSet<HelpViewCommands> validCommand;

    public HelpView() {
        validCommand = EnumSet.allOf(HelpViewCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
