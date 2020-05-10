package view.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AuthenticationValidCommands {
    CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)"),
    LoginAccount("login\\s+(.*)");
    private final Pattern commandPattern;
    private final String output;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    AuthenticationValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        this.output = output;
    }

    @Override
    public String toString() {
        return this.output;
    }
}
