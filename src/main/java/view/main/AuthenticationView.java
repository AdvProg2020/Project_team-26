package view.main;

import controller.Exceptions;
import controller.account.Account;
import controller.account.AuthenticationController;
import view.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationView extends View implements ViewI {
    private String input;
    private AuthenticationController control;

    AuthenticationView(ViewManager manager, String command) {
        super(manager);
        input = command;
        control = new AuthenticationController();
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
        matcher.find();
        System.out.println("enter password or type back if you want to return to previous");
        String password = manager.scan.nextLine();
        String username = matcher.group(1);
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            if (password.equals("back") || username.equals("back"))
                return;
            try {
                control.login(username, password, manager.getTocken());
                isLoggedIn = true;
            } catch (Exceptions.InvalidPasswordException wrongPassword) {
                wrongPassword.getMessage();
                password = manager.scan.nextLine();
            } catch (Exceptions.InvalidUserNameException wrongUserName) {
                wrongUserName.getMessage();
                username = manager.scan.nextLine();
            }
        }
    }

    private void register(Matcher matcher) {
        Account account = getUserInfo(matcher);
        boolean isComplete = false;
        boolean isBack = false;
        while (!isComplete && !isBack) {
            if (isBack && account.getPassword().equals("back"))
                return;
            try {
                control.register(account);
                isComplete = true;
            } catch (Exceptions.InvalidUserNameException wrongUsername) {
                System.out.println(wrongUsername.getMessage());
                System.out.println("please enter another username");
                account.setUsername(manager.scan.nextLine());
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
            System.out.println("enter password and notice it shouldn't be \"back\"");
            account.setPassword(manager.scan.nextLine());
        } while (account.getPassword().equals("back"));
        System.out.println("enter first name");
        account.setFirstName(manager.scan.nextLine());
        System.out.println("enter last name");
        account.setLastName(manager.scan.nextLine());
        System.out.println("enter email");
        account.setEmail(manager.scan.nextLine());
        account.setToken(manager.getTocken());
        return account;
    }

}
