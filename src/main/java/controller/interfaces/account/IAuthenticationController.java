package controller.interfaces.account;

import controller.Exceptions;
import controller.account.Account;

public interface IAuthenticationController {

    void login(String username, String password, String token) throws Exceptions.FieldsExistWithSameName, Exceptions.InvalidPasswordException;

    void register(Account account, String token) throws Exceptions.FieldsExistWithSameName, Exceptions.InvalidAccessDemand;

    void logout(String token) throws Exceptions.UnSuccessfulLogout;
}
