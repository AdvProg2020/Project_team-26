package client.connectionController.interfaces.account;


import client.exception.*;
import client.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IShowUserController {
    ArrayList<User> getUsers(String token) throws NoAccessException, InvalidTokenException;

    User getUserByName(String username, String token) throws NoAccessException, InvalidTokenException;

    User getUserById(int id, String token) throws NoAccessException, InvalidTokenException;

    User getUserByToken(String token) throws InvalidTokenException;

    List<User> getManagers(int id);

    List<User> getOnlineSupporter(String token);

    List<User> getAllOnlineUser(String token) throws NoAccessException, InvalidTokenException;


    Map<String, String> getUserInfo(String token) throws NoAccessException, InvalidTokenException;

    void delete(String username, String token) throws NoAccessException, NoAccessException, InvalidTokenException, NoObjectIdException, client.exception.NoAccessException;

}
