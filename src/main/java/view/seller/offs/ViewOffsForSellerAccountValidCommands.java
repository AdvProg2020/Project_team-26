package view.seller.offs;

import view.View;
import view.seller.products.ManageProductForSellerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ViewOffsForSellerAccountValidCommands {
    ViewOffWithIdForSeller("view\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageOffForSeller page) {
            page.showOff(Pattern.compile(ViewOffWithIdForSeller.toString()).matcher(page.getInput()));
        }
    },
    EditOffWithIdForSeller("edit\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageOffForSeller page) {
            page.showOff(Pattern.compile(EditOffWithIdForSeller.toString()).matcher(page.getInput()));
        }
    },
    AddOffForSeller("add\\s+off") {
        @Override
        public void goToFunction(ManageOffForSeller page) {
            page.addOff();
        }
    },
    ShowAll("show all") {
        @Override
        public void goToFunction(ManageOffForSeller page) {
            page.showAll();
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(ManageOffForSeller page) {
            page.logOut();
        }
    },
    Sorting("sorting") {
        @Override
        public void goToFunction(ManageOffForSeller page) {
            page.sorting();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(ManageOffForSeller page) {
            page.help();
        }
    };
    private final Pattern commandPattern;
    private final String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ViewOffsForSellerAccountValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }

    public abstract void goToFunction(ManageOffForSeller page);

    @Override
    public String toString() {
        return this.value;
    }

}
