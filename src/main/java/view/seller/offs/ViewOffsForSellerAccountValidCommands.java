package view.seller.offs;

import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ViewOffsForSellerAccountValidCommands {
    ViewOffWithIdForSeller("view\\s+(.*)"),
    EditOffWithIdForSeller("edit\\s+(.*)"),
    AddOffForSeller("add\\s+off");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ViewOffsForSellerAccountValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }

}
