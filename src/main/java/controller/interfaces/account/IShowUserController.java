package controller.interfaces.account;

import controller.Exceptions;
import controller.account.UserInfoController;

import java.util.Map;

public interface IShowUserController {
    UserInfoController[] getUsers(String token);

    UserInfoController getUserByName(String username, String token) throws Exceptions.TheParameterDoesNOtExist;
    UserInfoController getUserById(int id,String token);

    Map<String, String> getUserInfo(String token);

    void delete(String username , String token) throws Exceptions.InvalidDeleteDemand;
}
