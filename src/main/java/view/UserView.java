package view;

import controller.interfaces.account.IShowUserController;
import controller.interfaces.account.IUserInfoController;
import exception.*;
import model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class UserView {
    private static UserView userView;

    private UserView() {
        UserView.userView = new UserView();
    }

    public static UserView getInstance() {
        if (UserView.userView == null) {
            UserView.userView = new UserView();
        }
        return UserView.userView;
    }

    public void viewPersonalInfo(ViewManager manager, IShowUserController userController) {
        try {
            userController.getUserInfo(manager.getToken()).forEach((key, info) -> manager.inputOutput.println(key + " : " + info));
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    public void edit(ArrayList<String> editableFields, ViewManager manager, IUserInfoController infoController) {
        manager.inputOutput.println("enter back to end editing");
        editableFields.forEach(i -> manager.inputOutput.println(i));
        while (true) {
            manager.inputOutput.println("enter the field");
            String field = manager.inputOutput.nextLine();
            if (field.matches("back"))
                return;
            if (field.matches("password")) {
                changePassword(manager, infoController);
            } else if (editableFields.stream().filter(i -> field.matches(field)).collect(Collectors.toList()).size() > 0) {
                manager.inputOutput.println("enter new one");
                try {
                    infoController.changeInfo(field, manager.inputOutput.nextLine(), manager.getToken());
                } catch (NotLoggedINException e) {
                    manager.loginInAllPagesEssential();
                } catch (InvalidTokenException e) {
                   manager.setTokenFromController(e.getMessage());
                } catch (InvalidAuthenticationException e) {
                    e.printStackTrace();
                } catch (InvalidFormatException | NoSuchField  e) {
                    manager.inputOutput.println(e.getMessage());
                }
            }
        }
    }

    public void changePassword(ViewManager manager, IUserInfoController infoController) {
        manager.inputOutput.println("enter old password");
        String oldPassword = manager.inputOutput.nextLine();
        if (oldPassword.matches("back"))
            return;
        manager.inputOutput.println("enter new password");
        String newPassword = manager.inputOutput.nextLine();
        if (newPassword.matches("back"))
            return;
        try {
            infoController.changePassword(oldPassword, newPassword, manager.getToken());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "if you dont want to log in type back ");
            if (manager.inputOutput.nextLine().matches("back"))
                return;
            manager.loginInAllPagesEssential();
        }
    }

    public void balance(ViewManager manager, IUserInfoController userInfoController) {
        try {
            manager.inputOutput.println("balance is : " + userInfoController.getBalance(manager.getToken()));
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage() + "if you dont want to log in type back ");
            if (manager.inputOutput.nextLine().matches("back"))
                return;
            manager.loginInAllPagesEssential();
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

    public void initialEditFields(ArrayList<String> editableFields, ViewManager manager, IShowUserController userController) {
        try {
            editableFields.clear();
            userController.getUserInfo(manager.getToken()).forEach((key, info) -> editableFields.add(key));
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }
    }

}
