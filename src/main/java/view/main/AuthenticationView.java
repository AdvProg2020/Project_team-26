package view.main;

import view.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationView extends View implements view {
    private String input;

    AuthenticationView(ViewManager manager, String command) {
        super(manager);
        input = command;
    }

    @Override
    public View run() {
        if (input.matches(AuthenticationValidCommands.LoginAccount.toString()))
            login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher(input));
        else if (input.matches(AuthenticationValidCommands.CreateAccount.toString()))
            register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher(input));
        return null;
    }

    private void login(Matcher matcher) {

    }

    private void register(Matcher matcher) {

    }
}
