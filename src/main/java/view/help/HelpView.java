package view.help;

import view.ViewManager;
import view.View;

import java.util.EnumSet;

public class HelpView extends View {
    EnumSet<HelpViewCommands> validCommand;

    public HelpView() {
        validCommand = EnumSet.allOf(HelpViewCommands.class);
    }

    @Override
    public void run(ViewManager manager) {
        return ;
    }
}
