package view.manager.request;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class ManageRequestForManagerView extends View implements IView {
    EnumSet<ManageRequestForManagerViewValidCommands> validCommands;

    public ManageRequestForManagerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ManageRequestForManagerViewValidCommands.class);
    }

    @Override
    public void run() {
        while (manager.getIsUserLoggedIn()) {
            if ((super.input = (manager.scan.nextLine()).trim()).matches("back"))
                break;
            for (ManageRequestForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
    }

    protected void detailOfRequest(Matcher matcher) {

    }

    protected void declineTheRequest(Matcher matcher) {

    }

    protected void acceptTheRequest(Matcher matcher) {

    }
    protected void logOut(){

    }

}
