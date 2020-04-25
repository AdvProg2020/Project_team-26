package view.manager.discount;

import view.*;
import view.manager.category.ManageCategoryForManagerViewValidCommands;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class discountForManagerView extends View implements view {
    EnumSet<discountForManagerViewValidCommands> validCommands;

    public discountForManagerView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(discountForManagerViewValidCommands.class);
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (discountForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    protected void editDiscountCodeWithItsCode(Matcher matcher) {

    }

    protected void removeDiscountCodeWithItsCode(Matcher matcher) {

    }

    protected void viewDiscountCodeWithItsCode(Matcher matcher) {

    }
}
