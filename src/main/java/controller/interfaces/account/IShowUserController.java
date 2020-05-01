package controller.interfaces.account;

import controller.Exceptions;
import controller.account.UserInfoController;
import exception.NoAccessException;
import model.User;

import java.util.ArrayList;
import java.util.Map;

public interface IShowUserController {
    ArrayList<User> getUsers(String token) throws NoAccessException;

    User getUserByName(String username, String token) throws NoAccessException;
    User getUserById(int id,String token) throws NoAccessException;

    Map<String, String> getUserInfo(String token) throws NoAccessException;

    void delete(String username , String token) throws Exceptions.InvalidDeleteDemand, NoAccessException;
}
