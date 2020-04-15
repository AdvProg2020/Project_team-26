package view.main;

import view.*;
import view.ViewManager;

import java.util.EnumSet;

public class MainPageView extends View {
    EnumSet<MainPageViewValidCommands> validCommand;

    MainPageView() {
        validCommand = EnumSet.allOf(MainPageViewValidCommands.class);
    }

    @Override
    public void run(ViewManager manager) {
        return ;
    }
}
