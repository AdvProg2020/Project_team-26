package view.main;

import controller.account.Account;
import controller.account.AuthenticationController;
import controller.interfaces.account.IAuthenticationController;
import exception.*;
import model.Role;
import model.repository.RepositoryContainer;
import view.InputOutput;
import view.View;
import view.ViewManager;

import javax.naming.AuthenticationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthenticationView extends View {
    private String input;
    IAuthenticationController control;

    public AuthenticationView(ViewManager manager, String command) {
        super(manager);
        input = command;
        control = new AuthenticationController(new RepositoryContainer());
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
        Account account = new Account();
        manager.inputOutput.println("enter password or type back if you want to return to previous");
        account.setPassword(manager.inputOutput.nextLine());
        account.setUsername(matcher.group(1));
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            if (account.getUsername().equals("back") || account.getPassword().equals("back"))
                return;
            try {
                control.login(account.getUsername(), account.getPassword(), manager.getTocken());
                isLoggedIn = true;
                manager.setUserLoggedIn(true);
            } catch (InvalidTokenException e) {
                manager.inputOutput.println(e.getMessage());
                return;
            } catch (InvalidFormatException e) {
                manager.inputOutput.println(e.getMessage());
                correctField(e.getFieldName(), account);
            } catch (InvalidAuthenticationException e) {
                manager.inputOutput.println(e.getMessage());
                correctField(e.getFieldName(), account);
            } catch (PasswordIsWrongException e) {
                manager.inputOutput.println(e.getMessage() + "\nplease correct password.");
                account.setPassword(manager.inputOutput.nextLine());
            }
        }
    }

    public void register(Matcher matcher) {
        Account account = getUserInfo(matcher);
        boolean isComplete = false;
        while (!isComplete) {
            if (isComplete || account.getPassword().equals("back") || account.getUsername().equals("back"))
                return;
            try {
                control.register(account, manager.getTocken());
                isComplete = true;
            } catch (NoAccessException | InvalidTokenException e) {
                manager.inputOutput.println(e.getMessage());
                return;
            } catch (InvalidFormatException e) {
                manager.inputOutput.println(e.getMessage());
                correctField(e.getFieldName(), account);
            } catch (InvalidAuthenticationException e) {
                manager.inputOutput.println(e.getMessage());
                correctField(e.getFieldName(), account);
            }
        }
    }
    private void correctField(String field, Account account) {
        manager.inputOutput.println("correct the field " + field + ".");
        switch (field) {
            case "Password":
                account.setPassword(manager.inputOutput.nextLine());
                break;
            case "Username":
                account.setUsername(manager.inputOutput.nextLine());
                break;
        }
    }

    private Account getUserInfo(Matcher matcher) {
        Account account = new Account();
        matcher.find();
        account.setRole(setRole(matcher.group(1)));
        account.setUsername(matcher.group(2));
        while (account.getUsername().equals("back")) {
            manager.inputOutput.println("enter username and notice it shouldn't be \"back\"");
            account.setUsername(manager.inputOutput.nextLine());
        }
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

    private Role setRole(String type) {
        if (type.equals("manager"))
            return Role.ADMIN;
        if (type.equals("seller"))
            return Role.SELLER;
        return Role.CUSTOMER;
    }
}
