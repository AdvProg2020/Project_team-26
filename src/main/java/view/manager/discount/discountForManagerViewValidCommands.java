package view.manager.discount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum discountForManagerViewValidCommands {
    ViewDiscountCodeWithItsCode("view\\s+discount\\+code\\+(.*)") {
        @Override
        public void goToFunction(discountForManagerViewI page) {
            page.viewDiscountCodeWithItsCode(Pattern.compile(ViewDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    },
    EditDiscountCodeWithItsCode("edit\\s+discount\\+code\\+(.*)") {
        @Override
        public void goToFunction(discountForManagerViewI page) {
            page.editDiscountCodeWithItsCode(Pattern.compile(EditDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    },
    RemoveDiscountCodeWithItsCode("remove\\s+discount\\s+code\\s+(.*)") {
        @Override
        public void goToFunction(discountForManagerViewI page) {
            page.removeDiscountCodeWithItsCode(Pattern.compile(RemoveDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    };
    private final Pattern commandPattern;

    public abstract void goToFunction(discountForManagerViewI page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    discountForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

}
