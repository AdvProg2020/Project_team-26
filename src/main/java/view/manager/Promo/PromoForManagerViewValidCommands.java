package view.manager.Promo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PromoForManagerViewValidCommands {
    ViewDiscountCodeWithItsCode("view\\s+discount\\+code\\+(\\d+)") {
        @Override
        public void goToFunction(PromoForManagerView page) {
            page.viewDiscountCodeWithItsCode(Pattern.compile(ViewDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    },
    EditDiscountCodeWithItsCode("edit\\s+discount\\+code\\+(\\d+)") {
        @Override
        public void goToFunction(PromoForManagerView page) {
            page.editDiscountCodeWithItsCode(Pattern.compile(EditDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    },
    RemoveDiscountCodeWithItsCode("remove\\s+discount\\s+code\\s+(\\d+)") {
        @Override
        public void goToFunction(PromoForManagerView page) {
            page.removeDiscountCodeWithItsCode(Pattern.compile(RemoveDiscountCodeWithItsCode.toString()).matcher(page.getInput()));
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(PromoForManagerView page) {
            page.logOut();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(PromoForManagerView page) {
            page.help();
        }
    }, Sort("sorting") {
        @Override
        public void goToFunction(PromoForManagerView page) {
            page.sorting();
        }
    };
    private final Pattern commandPattern;
    private final String value;
    @Override
    public String toString() {
        return value;
    }

    public abstract void goToFunction(PromoForManagerView page);

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    PromoForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }


}
