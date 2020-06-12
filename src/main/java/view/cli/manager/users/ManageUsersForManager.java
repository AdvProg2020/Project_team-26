package view.cli.manager.users;

import controller.interfaces.account.IShowUserController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import view.cli.ControllerContainer;
import view.cli.View;
import view.cli.ViewManager;
import view.cli.main.AuthenticationValidCommands;
import view.cli.main.AuthenticationView;
import view.cli.main.MainPageView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageUsersForManager extends View {
    private EnumSet<ValidCommandsForManageUsersForManagerView> validCommands;
    private IShowUserController showUserController;

    public ManageUsersForManager(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(ValidCommandsForManageUsersForManagerView.class);
        showUserController = (IShowUserController) manager.getController(ControllerContainer.Controller.ShowUserController);
    }

    @Override
    public void run() {
        manager.inputOutput.println("users manage page :");
        showAll();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (ValidCommandsForManageUsersForManagerView command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (!isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    protected void showAll() {
        try {
            showUserController.getUsers(manager.getToken()).forEach(user -> manager.inputOutput.println(user.getFullName() + " with username : " +
                    user.getUsername()));
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void createManagerProfile() {
        manager.inputOutput.println("enter username");
        String userName = manager.inputOutput.nextLine();
        String command = "create account manager " + userName;
        AuthenticationView authenticationView = new AuthenticationView(manager, command);
        authenticationView.register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher(command));
    }

    protected void viewUser(Matcher matcher) {
        matcher.find();
        try {
            showUserController.getUserByName(matcher.group(1), manager.getToken())
                    .getDetails().forEach((field, value) -> manager.inputOutput.println(
                    field + " : " + value));
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }


    protected void logOut() {
        new MainPageView(manager).logout();
    }

    protected void deleteUser(Matcher matcher) {
        matcher.find();
        try {
            showUserController.delete(matcher.group(1), manager.getToken());
        } catch (NoAccessException | NoObjectIdException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("view [username]");
        commandList.add("delete user [username]");
        commandList.add("create manager profile");
        commandList.add("logout");
        commandList.add("show all");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }

}