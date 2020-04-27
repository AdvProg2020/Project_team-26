package view.manager.users;

import controller.Exceptions;
import controller.account.Account;
import controller.account.ShowUserController;
import controller.account.UserInfoController;
import view.*;
import view.main.AuthenticationValidCommands;
import view.main.AuthenticationView;
import view.main.MainPageView;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageUsersForManager extends View implements ViewI {
    EnumSet<ValidCommandsForManageUsersForManagerView> validCommands;
    private ShowUserController controller;

    public ManageUsersForManager(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ValidCommandsForManageUsersForManagerView.class);
        controller = new ShowUserController();
    }

    @Override
    public View run() {
        showAll();
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("back")) {
            for (ValidCommandsForManageUsersForManagerView command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    private void showAll() {
        UserInfoController[] accounts = controller.getUsers(manager.getTocken());
        for (UserInfoController account : accounts) {
            System.out.println("account username : " + account.getUsername(manager.getTocken()));
        }
    }

    protected void deleteUser(Matcher matcher) {
        matcher.find();
        try {
            controller.delete(matcher.group(1), manager.getTocken());
        } catch (Exceptions.InvalidDeleteDemand invalidDeleteDemand) {
            System.out.println(invalidDeleteDemand.getMessage());
        }
    }

    protected void createManagerProfile() {
        System.out.println("please enter the username");
        String command = "create account manager " + manager.scan.nextLine();
        new AuthenticationView(manager, command).register(
                Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher(command));
    }

    protected void viewUser(Matcher matcher) {
        matcher.find();
        UserInfoController account = controller.getUserByName(matcher.group(1), manager.getTocken());
        show(account);
    }

    private void show(UserInfoController account) {
        System.out.println("account username : " + account.getUsername(manager.getTocken()));
        System.out.println("{");
        System.out.println("first name : " + account.getFirstName(manager.getTocken()));
        System.out.println("last name : " + account.getLastName(manager.getTocken()));
        System.out.println("email : " + account.getEmail(manager.getTocken()));
        System.out.println("role : " + account.getRole(manager.getTocken()));
        System.out.println("}");
    }

    protected void logOut() {
        new MainPageView(manager).logout(manager.getTocken());
    }

    protected void printError() {

    }
}
