package controller.interfaces.account;

import controller.account.Account;
import exception.*;

import javax.naming.AuthenticationException;

public interface IAuthenticationController {

    void login(String username, String password, String token) throws PasswordIsWrongException,InvalidTokenException, InvalidFormatException, InvalidAuthenticationException;

    void register(Account account, String token) throws  NoAccessException, InvalidFormatException, InvalidTokenException, InvalidAuthenticationException;

    void logout(String token) throws  InvalidTokenException, NotLoggedINException;
}
