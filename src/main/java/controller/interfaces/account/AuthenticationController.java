package controller.interfaces.account;

import controller.Exceptions;
import controller.account.Account;

public interface AuthenticationController {

    void login(String username, String password, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidPasswordException;

    void register(Account account , String token) throws Exceptions.InvalidUserNameException;

    void logout(String token) throws Exceptions.UnSuccessfulLogout;
}
