package view.manager.discount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DiscountForManagerViewValidCommands {
    ViewDiscountCodeWithItsCode("view\\s+discount\\+code\\+(\\d+)") {
        @Override
        public void goToFunction(DiscountForManagerView page) {
            page.viewDiscountCodeWithItsCode(Pattern.compile(ViewDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    },
    EditDiscountCodeWithItsCode("edit\\s+discount\\+code\\+(\\d+)") {
        @Override
        public void goToFunction(DiscountForManagerView page) {
            page.editDiscountCodeWithItsCode(Pattern.compile(EditDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    },
    RemoveDiscountCodeWithItsCode("remove\\s+discount\\s+code\\s+(\\d+)") {
        @Override
        public void goToFunction(DiscountForManagerView page) {
            page.removeDiscountCodeWithItsCode(Pattern.compile(RemoveDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(DiscountForManagerView page) {
            if (page.getManager().getIsUserLoggedIn()) {
                page.logOut();
                return;
            }
            page.getManager().printError();
        }
    },
    Help("help"){
        @Override
        public void goToFunction(DiscountForManagerView page) {
            page.help(page.getManager().getIsUserLoggedIn());
        }
    };
    private final Pattern commandPattern;

    public abstract void goToFunction(DiscountForManagerView page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    DiscountForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

}
