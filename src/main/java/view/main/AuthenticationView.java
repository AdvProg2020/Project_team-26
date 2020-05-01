package view.main;

import controller.Exceptions;
import controller.interfaces.account.IAuthenticationController;
import view.View;
import view.ViewManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationView extends View {
    private String input;
    IAuthenticationController control;

    public AuthenticationView(ViewManager manager, String command, IAuthenticationController control) {
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

    private void login(Matcher matcher) {
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
            } catch (Exceptions.InvalidPasswordException wrongPassword) {
                wrongPassword.getMessage();
                password = manager.inputOutput.nextLine();
            } catch (Exceptions.FieldsExistWithSameName wrongUserName) {
                wrongUserName.getMessage();
                username = manager.inputOutput.nextLine();
            }
        }
    }

    public void register(Matcher matcher) {
        AccountForView account = getUserInfo(matcher);
        boolean isComplete = false;
        boolean isBack = false;
        while (!isComplete && !isBack) {
            if (isBack && account.getPassword().equals("back"))
                return;
            try {
                control.register(account, manager.getTocken());
                isComplete = true;
            } catch (Exceptions.FieldsExistWithSameName wrongUsername) {
                manager.inputOutput.println(wrongUsername.getMessage());
                manager.inputOutput.println("please enter another username");
                account.setUsername(manager.inputOutput.nextLine());
            } catch (Exceptions.InvalidAccessDemand accessDemand) {
                accessDemand.getMessage();
                return;
            }
            isBack = true;
        }
    }

    private AccountForView getUserInfo(Matcher matcher) {
        AccountForView account = new AccountForView();
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
