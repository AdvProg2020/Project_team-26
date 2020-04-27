package controller.account;

import controller.Exceptions;

public class UserInfoController implements controller.interfaces.account.UserInfoController {

    public String getEmail(String token) {
        return null;
    }

    public void changeEmail(String email, String token) {
    }

    public String getUsername(String token) {
        return null;
    }

    public void changeUsername(String username, String token) {
    }
    public String getFirstName(String token){
        return null;
    }
    public  String getLastName(String token){
        return null;
    }

    public String getPassword(String token) {
        return null;
    }

    public void setPassword(String oldPassword, String newPassword, String token) {
    }

    public int getInfo(String key, String token) {
        return 0;
    }

    public void changeInfo(String key, String value, String token) throws Exceptions.InvalidFiledException{
    }

    public String getRole(String token) {
        return null;
    }
}
