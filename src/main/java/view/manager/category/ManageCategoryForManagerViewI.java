package view.manager.category;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class ManageCategoryForManagerViewI extends View implements ViewI {
    EnumSet<ManageCategoryForManagerViewValidCommands> validCommands;

    public ManageCategoryForManagerViewI(ViewManager managerView) {
        super(managerView);
        validCommands = EnumSet.allOf(ManageCategoryForManagerViewValidCommands.class);
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (ManageCategoryForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    protected void addCategoryForManager(Matcher matcher) {

    }

    protected void EditCategoryForManager(Matcher matcher) {

    }

    protected void RemoveCategoryForManager(Matcher matcher) {

    }


}
