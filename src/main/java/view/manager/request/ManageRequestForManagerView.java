package view.manager.request;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class ManageRequestForManagerView extends View {
    EnumSet<ManageRequestForManagerViewValidCommands> validCommands;
    @Override
    public View run(ViewManager manager) {
         return null;
    }
    public void detailOfRequest(Matcher matcher) {

    }
    public void declineTheRequest(Matcher matcher) {

    }
    public void acceptTheRequest(Matcher matcher) {

    }

}
