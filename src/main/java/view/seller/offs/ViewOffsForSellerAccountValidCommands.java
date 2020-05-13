package view.seller.offs;

import view.View;
import view.seller.products.ManageProductForSellerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ViewOffsForSellerAccountValidCommands {
    ViewOffWithIdForSeller("view\\s+(.*)"){
        @Override
        public void goToFunction(ManageOffForSeller page) {

        }
    },
    EditOffWithIdForSeller("edit\\s+(.*)"){
        @Override
        public void goToFunction(ManageOffForSeller page) {

        }
    },
    AddOffForSeller("add\\s+off"){
        @Override
        public void goToFunction(ManageOffForSeller page) {

        }
    },
    ShowAll("show all"){
        @Override
        public void goToFunction(ManageOffForSeller page) {

        }
    };
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ViewOffsForSellerAccountValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

    public abstract void goToFunction(ManageOffForSeller page);

}
