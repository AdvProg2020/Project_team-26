package view.manager.users;

import controller.interfaces.account.IShowUserController;
import view.*;
import view.main.MainPageView;

import java.util.EnumSet;

public class ManageUsersForManager extends View implements IView {
    EnumSet<ValidCommandsForManageUsersForManagerView> validCommands;
    private IShowUserController controller;

    public ManageUsersForManager(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ValidCommandsForManageUsersForManagerView.class);
        this.controller = controller;
    }

    @Override
    public void run() {
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            for (ValidCommandsForManageUsersForManagerView command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
    }


    protected void logOut() {
        new MainPageView(manager).logout(manager.getToken());
    }

}
