package view.manager.users;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class ManageUsersForManager extends View implements ViewI {
    EnumSet<ValidCommandsForManageUsersForManagerView> validCommands;

    public ManageUsersForManager(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ValidCommandsForManageUsersForManagerView.class);
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (ValidCommandsForManageUsersForManagerView command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    protected void deleteUser(Matcher matcher) {

    }

    protected void createManagerProfile(Matcher matcher) {

    }

    protected void edit(Matcher matcher) {

    }

    protected void viewUser(Matcher matcher) {

    }

    protected void manageAllProductForUserInManagerView(Matcher matcher) {

    }
}
