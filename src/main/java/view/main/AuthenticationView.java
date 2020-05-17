package view.main;

import controller.account.Account;
import controller.account.AuthenticationController;
import interfaces.account.IAuthenticationController;
import exception.*;
import model.Role;
import view.ControllerContainer;
import view.View;
import view.ViewManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthenticationView extends View {
    IAuthenticationController control;

    public AuthenticationView(ViewManager manager, String command) {
        super(manager);
        super.input = command;
        control = (AuthenticationController) manager.getController(ControllerContainer.Controller.AuthenticationController);
    }

    @Override
    public void run() {
        if (super.input.matches(AuthenticationValidCommands.LoginAccount.toString()))
            login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher(input));
        else if (super.input.matches(AuthenticationValidCommands.CreateAccount.toString()))
            register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher(input));
    }

    public void login(Matcher matcher) {
        matcher.find();
        Account account = new Account();
        manager.inputOutput.println("enter password or type back if you want to return to previous");
        account.setPassword(manager.inputOutput.nextLine());
        account.setUsername(matcher.group(1));
        while (true) {
            if (account.getUsername().equals("back") || account.getPassword().equals("back"))
                return;
            try {
                control.login(account.getUsername(), account.getPassword(), manager.getToken());
                manager.setUserLoggedIn(true);
                manager.inputOutput.println("logged in");
                return;
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
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
        while (true) {
            if (account.getPassword().equals("back") || account.getUsername().equals("back"))
                return;
            try {
                control.register(account, manager.getToken());
                manager.inputOutput.println("registered");
                return;
            } catch (NoAccessException e) {
                manager.inputOutput.println(e.getMessage());
                return;
            } catch (InvalidFormatException e) {
                manager.inputOutput.println(e.getMessage());
                correctField(e.getFieldName(), account);
            } catch (InvalidAuthenticationException e) {
                manager.inputOutput.println(e.getMessage());
                correctField(e.getFieldName(), account);
            } catch (AlreadyLoggedInException e) {
                manager.inputOutput.println(e.getMessage() + "first logout then register");
                return;
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage() + "\nnew token will be set try again");
                return;
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
            case "Email":
                account.setEmail(manager.inputOutput.nextLine());
                break;
        }
    }

    private Account getUserInfo(Matcher matcher) {
        Account account = new Account();
        matcher.find();
        account.setRole(setRole(matcher.group(1)));
        account.setUsername(matcher.group(2));
        /**
         * you can not type back here to go to privoius
         * if it goes to controller it is better
         */
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
        account.setToken(manager.getToken());
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
