package controller.account;

import controller.Exceptions;

public class AuthenticationController implements controller.interfaces.account.AuthenticationController {

    public void login(String username, String password, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidPasswordException {


    }

    public void register(Account account , String token) throws Exceptions.InvalidUserNameException {


    }

    public void logout(String token) throws Exceptions.UnSuccessfulLogout {

    }

}
