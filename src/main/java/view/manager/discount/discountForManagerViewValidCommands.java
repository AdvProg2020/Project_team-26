package view.manager.discount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum discountForManagerViewValidCommands {
    ViewDiscountCodeWithItsCode("view\\s+discount\\+code\\+(.*)"),
    EditDiscountCodeWithItsCode("edit\\s+discount\\+code\\+(.*)"),
    RemoveDiscountCodeWithItsCode("remove\\s+discount\\s+code\\s+(.*)");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    discountForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

}