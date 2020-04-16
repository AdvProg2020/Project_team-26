package view.manager.discount;

import view.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;

public class discountForManagerView extends View {
    EnumSet<discountForManagerViewValidCommands> validCommands;

    public discountForManagerView() {
        validCommands = EnumSet.allOf(discountForManagerViewValidCommands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    private void editDiscountCodeWithItsCode(Matcher matcher) {

    }

    private void removeDiscountCodeWithItsCode(Matcher matcher) {

    }

    private void viewDiscountCodeWithItsCode(Matcher matcher) {

    }
}
