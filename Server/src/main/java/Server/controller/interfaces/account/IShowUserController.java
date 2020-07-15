package Server.controller.interfaces.account;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import model.Admin;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IShowUserController {
    ArrayList<User> getUsers(String token) throws NoAccessException, InvalidTokenException;

    User getUserByName(String username, String token) throws NoAccessException, InvalidTokenException;
    User getUserById(int id,String token) throws NoAccessException, InvalidTokenException;
    User getUserByToken(String token) throws InvalidTokenException;

    public List<Admin> getManagers(int id);

    Map<String, String> getUserInfo(String token) throws NoAccessException, InvalidTokenException;

    void delete(String username , String token) throws NoAccessException, NoAccessException, InvalidTokenException, NoObjectIdException;

}
