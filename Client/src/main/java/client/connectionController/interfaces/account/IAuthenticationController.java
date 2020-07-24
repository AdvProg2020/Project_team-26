package client.connectionController.interfaces.account;


import client.exception.*;
import client.gui.Constants;
import client.model.Account;
import net.minidev.json.JSONObject;

import java.util.Map;

public interface IAuthenticationController {

    void login(String username, String password, String token, String captcha) throws PasswordIsWrongException,InvalidTokenException, InvalidFormatException, InvalidAuthenticationException;

    void register(Account account, String token) throws NoAccessException, InvalidFormatException, InvalidTokenException, InvalidAuthenticationException, AlreadyLoggedInException;

    void logout(String token) throws  InvalidTokenException, NotLoggedINException;

    public String getCaptcha();
}
