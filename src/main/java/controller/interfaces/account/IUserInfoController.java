package controller.interfaces.account;

import exception.*;

public interface IUserInfoController {

    void changePassword(String oldPassword, String newPassword, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException;

    void changeInfo(String key, String value, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField;

    String getBalance(String token) throws NotLoggedINException, InvalidTokenException;

    String getRole(String token) throws NotLoggedINException, InvalidTokenException;

    String getCompanyName(String token) throws InvalidTokenException, NotLoggedINException, NoSuchField;
}
