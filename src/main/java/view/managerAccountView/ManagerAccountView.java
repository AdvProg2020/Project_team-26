package view.managerAccountView;

import view.*;
import view.ViewManager;

import java.util.EnumSet;

/**
 * i didnt create a package for commands that provides just one command list
 */

public class ManagerAccountView extends View {
    EnumSet<ValidCommandsForManagerAccount> validCommand;

    ManagerAccountView() {
        validCommand = EnumSet.allOf(ValidCommandsForManagerAccount.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
