package view.manager.discount;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class discountForManagerViewI extends View implements ViewI {
    EnumSet<discountForManagerViewValidCommands> validCommands;

    public discountForManagerViewI(ViewManager manager) {
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
