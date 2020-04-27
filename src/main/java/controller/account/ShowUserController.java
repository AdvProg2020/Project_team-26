package controller.account;

import controller.Exceptions;
import model.User;

import java.util.Map;

public class ShowUserController {

    public UserInfoController[] getUsers(String token) {
        return null;
    }

    public UserInfoController getUserByName(String username, String token) {
        return null;
    }

    public Map<String, String> getUserInfo(String token){
        return null;
    }

    public void delete(String username , String token) throws Exceptions.InvalidDeleteDemand {

    }
}
