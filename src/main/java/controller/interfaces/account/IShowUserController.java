package controller.interfaces.account;

import exception.InvalidTokenException;
import exception.NoAccessException;
import model.User;

import java.util.ArrayList;
import java.util.Map;

public interface IShowUserController {
    ArrayList<User> getUsers(String token) throws NoAccessException, InvalidTokenException;

    User getUserByName(String username, String token) throws NoAccessException, InvalidTokenException;
    User getUserById(int id,String token) throws NoAccessException, InvalidTokenException;

    Map<String, String> getUserInfo(String token) throws NoAccessException, InvalidTokenException;

    void delete(String username , String token) throws NoAccessException, NoAccessException, InvalidTokenException;
}
