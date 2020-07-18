package client.connectionController.interfaces.account;


import client.exception.*;

import java.util.Map;

public interface IUserInfoController {

    void changePassword(String oldPassword, String newPassword, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException, client.exception.InvalidTokenException, client.exception.NoAccessException, client.exception.NotLoggedINException;

    void changePassword(String newPassword, String token) throws InvalidTokenException, NotLoggedINException, client.exception.InvalidTokenException, client.exception.NotLoggedINException;

    void changeInfo(String key, String value, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField;

    String getBalance(String token) throws NotLoggedINException, InvalidTokenException;

    String getRole(String token) throws NotLoggedINException, InvalidTokenException;

    String getCompanyName(String token) throws InvalidTokenException, NotLoggedINException, NoSuchField;

    void changeImage(byte[] image, String token) throws InvalidTokenException, NotLoggedINException;

    public void changeInfo(Map<String, String> values, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField;

    public void changeBalance(Long newCredit, String token) throws InvalidTokenException, NotLoggedINException, InvalidAuthenticationException;
}
