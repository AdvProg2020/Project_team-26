package controller.interfaces.account;

import exception.*;
import model.User;

import java.util.List;
import java.util.Map;

public interface IUserInfoController {

    void changePassword(String oldPassword, String newPassword, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException;

    void changePassword(String newPassword, String token) throws InvalidTokenException, NotLoggedINException;

    void changeInfo(String key, String value, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField;

    String getBalance(String token) throws NotLoggedINException, InvalidTokenException;

    String getRole(String token) throws NotLoggedINException, InvalidTokenException;

    String getCompanyName(String token) throws InvalidTokenException, NotLoggedINException, NoSuchField;

    void changeImage(byte[] image, String token) throws InvalidTokenException, NotLoggedINException;

    public void changeInfo(Map<String, String> values, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField;

    public void changeBalance(Long newCredit, String token) throws InvalidTokenException, NotLoggedINException, InvalidAuthenticationException;
}
