package view.manager.request;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class ManageRequestForManagerViewI extends View implements ViewI {
    EnumSet<ManageRequestForManagerViewValidCommands> validCommands;

    public ManageRequestForManagerViewI(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ManageRequestForManagerViewValidCommands.class);
    }

    @Override
    public View run() {
        while (manager.getIsUserLoggedin()) {
            if ((super.input = (manager.scan.nextLine()).trim()).matches("back"))
                break;
            for (ManageRequestForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    protected void detailOfRequest(Matcher matcher) {

    }

    protected void declineTheRequest(Matcher matcher) {

    }

    protected void acceptTheRequest(Matcher matcher) {

    }

}
