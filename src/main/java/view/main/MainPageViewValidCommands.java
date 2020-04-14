package view.main;

import view.offs.AllOffsView;
import view.products.all.AllProductView;
import view.help.HelpView;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainPageViewValidCommands {
    CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)"),
    LoginAccount("login\\s+(.*)"),
    Back("back"),
    Exit("Exit"),
    ShowProducts("products"),
    ShowOffs("offs"),
    Help("help");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    MainPageViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }
}
