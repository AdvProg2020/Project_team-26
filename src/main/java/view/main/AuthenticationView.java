package view.main;

import controller.account.Account;
import controller.interfaces.account.IAuthenticationController;
import exception.NoAccessException;
import exception.PasswordIsWrongException;
import view.View;
import view.ViewManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.graalvm.compiler.nodes.java.RegisterFinalizerNode.register;

public class AuthenticationView extends View {
    private String input;
    IAuthenticationController control;

    public AuthenticationView(ViewManager manager, String command) {
        super(manager);
        input = command;
        this.control = control;
    }

    @Override
    public View run() {
        if (input.matches(AuthenticationValidCommands.LoginAccount.toString()))
            login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher(input));
        else if (input.matches(AuthenticationValidCommands.CreateAccount.toString()))
            register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher(input));
        return null;
    }

    public void login(Matcher matcher) {
        matcher.find();
        manager.inputOutput.println("enter password or type back if you want to return to previous");
        String password = manager.inputOutput.nextLine();
        String username = matcher.group(1);
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            if (password.equals("back") || username.equals("back"))
                return;
            try {
                control.login(username, password, manager.getTocken());
                isLoggedIn = true;
                manager.setUserLoggedIn(true);
            } catch (PasswordIsWrongException e) {
                e.getMessage();
            }
        }
    }

    public void register(Matcher matcher) {
        Account account = getUserInfo(matcher);
        boolean isComplete = false;
        boolean isBack = false;
        while (!isComplete && !isBack) {
            if (isBack && account.getPassword().equals("back"))
                return;
            try {
                control.register(account, manager.getTocken());
                isComplete = true;
            } catch (NoAccessException e) {
                e.getMessage();
            }
            isBack = true;
        }
    }

    private Account getUserInfo(Matcher matcher) {
        Account account = new Account();
        matcher.find();
        account.setRole(matcher.group(1));
        account.setUsername(matcher.group(2));
        do {
            manager.inputOutput.println("enter password and notice it shouldn't be \"back\"");
            account.setPassword(manager.inputOutput.nextLine());
        } while (account.getPassword().equals("back"));
        manager.inputOutput.println("enter first name");
        account.setFirstName(manager.inputOutput.nextLine());
        manager.inputOutput.println("enter last name");
        account.setLastName(manager.inputOutput.nextLine());
        manager.inputOutput.println("enter email");
        account.setEmail(manager.inputOutput.nextLine());
        account.setToken(manager.getTocken());
        return account;
    }

}
