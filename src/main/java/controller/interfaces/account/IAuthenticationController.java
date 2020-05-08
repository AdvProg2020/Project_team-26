package controller.interfaces.account;

import controller.account.Account;
import exception.*;

import javax.naming.AuthenticationException;

public interface IAuthenticationController {

    void login(String username, String password, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidPasswordException, Exceptions.IncorrectPasswordFormat, Exceptions.IncorrectUsernameFormat, Exceptions.UsernameAlreadyExists, PasswordIsWrongException, Exceptions.UserNameDoesntExist, InvalidTokenException, InvalidFormatException, AuthenticationException, InvalidAuthenticationException;

    void register(Account account, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidAccessDemand, Exceptions.IncorrectPasswordFormat, Exceptions.IncorrectUsernameFormat, Exceptions.UsernameAlreadyExists, NoAccessException, InvalidFormatException, InvalidTokenException, InvalidAuthenticationException;

    void logout(String token) throws Exceptions.UnSuccessfulLogout, InvalidTokenException, NotLoggedINException;
}
