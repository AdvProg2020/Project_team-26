package client.connectionController.account;

import client.connectionController.interfaces.account.IUserInfoController;
import client.exception.*;
import client.gui.Constants;

import client.model.*;
import client.model.enums.Role;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserInfoController implements IUserInfoController {


    public void changePassword(String oldPassword, String newPassword, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("oldPassword", oldPassword);
        jsonObject.put("newPassword", newPassword);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangePasswordWithOldPasswordAddress());
        } catch (HttpClientErrorException e) {
//TODO
            throw new client.exception.NoAccessException("jzfo");
        }
    }

    public void changePassword(String newPassword, String token) throws InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newPassword", newPassword);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangePasswordAddress());
        } catch (HttpClientErrorException e) {
//TODO
            throw new NotLoggedINException("jzfo");
        }
    }

    public void changeImage(byte[] image, String token) throws InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("image", image);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangeImageAddress());
        } catch (HttpClientErrorException e) {
//TODO
            throw new NotLoggedINException("jzfo");
        }
    }


    public void changeInfo(String key, String value, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("value", value);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangeInfoAddress());
        } catch (HttpClientErrorException e) {
//TODO
            throw new NotLoggedINException("jzfo");
        }
    }

    public void changeBalance(Long newCredit, String token) throws InvalidTokenException, NotLoggedINException, InvalidAuthenticationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newCredit", newCredit);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangeBalanceAddress());
        } catch (HttpClientErrorException e) {
//TODO
            throw new NotLoggedINException("jzfo");
        }
    }

    public String getCompanyName(String token) throws InvalidTokenException, NotLoggedINException, NoSuchField {
        try {
            return Constants.manager.getStringValueFromServerByAddress(Constants.getUserInfoControllerGetCompanyNameAddress(), token);
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }
    }

    public void changeInfo(Map<String, String> values, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("values", values);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangeMultipleInfoAddress());
        } catch (HttpClientErrorException e) {
//TODO
            throw new NotLoggedINException("jzfo");
        }
    }

    public String getBalance(String token) throws NotLoggedINException, InvalidTokenException {
        try {
            return Constants.manager.getStringValueFromServerByAddress(Constants.getUserInfoControllerGetBalanceAddress(), token);
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }
    }

    public String getRole(String token) throws NotLoggedINException, InvalidTokenException {
        try {
            return Constants.manager.getStringValueFromServerByAddress(Constants.getUserInfoControllerGetRoleAddress(), token);
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }
    }
}
