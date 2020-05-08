package view.manager.users;

import controller.account.AuthenticationController;
import controller.account.UserInfoController;
import controller.interfaces.account.IShowUserController;
import view.*;
import view.main.AuthenticationValidCommands;
import view.main.AuthenticationView;
import view.main.MainPageView;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageUsersForManager extends View implements IView {
    EnumSet<ValidCommandsForManageUsersForManagerView> validCommands;
    private IShowUserController controller;

    public ManageUsersForManager(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ValidCommandsForManageUsersForManagerView.class);
        this.controller = controller;
    }

    @Override
    public View run() {
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back")) {
            for (ValidCommandsForManageUsersForManagerView command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }


    protected void logOut() {
        new MainPageView(manager).logout(manager.getTocken());
    }

}
