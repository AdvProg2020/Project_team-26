package controller.interfaces.account;

import controller.Exceptions;
import controller.account.Account;
import exception.NoAccessException;
import exception.PasswordIsWrong;

public interface IAuthenticationController {

    void login(String username, String password, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidPasswordException, Exceptions.IncorrectPasswordFormat, Exceptions.IncorrectUsernameFormat, Exceptions.UsernameAlreadyExists, PasswordIsWrong, Exceptions.UserNameDoesntExist;

    void register(Account account, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidAccessDemand, Exceptions.IncorrectPasswordFormat, Exceptions.IncorrectUsernameFormat, Exceptions.UsernameAlreadyExists, NoAccessException;

    void logout(String token) throws Exceptions.UnSuccessfulLogout;
}
