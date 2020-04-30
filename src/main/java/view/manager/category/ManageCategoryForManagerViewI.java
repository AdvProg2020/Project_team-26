package view.manager.category;

import view.*;
import view.main.MainPageView;

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
        while (manager.getIsUserLoggedin()) {
            if ((super.input = (manager.scan.nextLine()).trim()).matches("back"))
                break;
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
        matcher.find();


    }

    protected void EditCategoryForManager(Matcher matcher) {

    }

    protected void RemoveCategoryForManager(Matcher matcher) {

    }

    protected void logOut() {
        new MainPageView(manager).logout(manager.getTocken());
    }

    protected void help(boolean isLoggedIn) {
        System.out.println("add [category]\nremove [category]\nnote that if you want to edit the sub category you have to select category" +
                " \nedit [category]\nhelp");
        if (isLoggedIn)
            System.out.println("logout");

    }


}
