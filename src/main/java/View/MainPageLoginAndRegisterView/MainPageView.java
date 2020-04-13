package View.MainPageLoginAndRegisterView;

import View.*;
import View.Help.HelpViewCommands;
import View.ViewManager;

import java.util.EnumSet;

public class MainPageView extends View {
    EnumSet<MainPageViewValidCommands> validCommand;

    MainPageView() {
        validCommand = EnumSet.allOf(MainPageViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
