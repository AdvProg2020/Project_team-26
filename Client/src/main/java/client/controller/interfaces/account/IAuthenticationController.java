package client.controller.interfaces.account;

import client.gui.Account;
import exception.*;

public interface IAuthenticationController {

    void login(String username, String password, String token) throws PasswordIsWrongException,InvalidTokenException, InvalidFormatException, InvalidAuthenticationException;

    void register(Account account, String token) throws NoAccessException, InvalidFormatException, InvalidTokenException, InvalidAuthenticationException, AlreadyLoggedInException;

    void logout(String token) throws  InvalidTokenException, NotLoggedINException;
}
