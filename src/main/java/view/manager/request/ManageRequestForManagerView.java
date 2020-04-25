package view.manager.request;

import view.*;
import view.manager.category.ManageCategoryForManagerViewValidCommands;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class ManageRequestForManagerView extends View implements view {
    EnumSet<ManageRequestForManagerViewValidCommands> validCommands;

    public ManageRequestForManagerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ManageRequestForManagerViewValidCommands.class);
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
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
