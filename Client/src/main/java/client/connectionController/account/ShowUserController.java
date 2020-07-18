package client.connectionController.account;

import client.connectionController.interfaces.account.IShowUserController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ShowUserController implements IShowUserController {


    public ArrayList<User> getUsers(String token) throws NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<User[]> responseEntity = restTemplate.postForEntity(Constants.getGetAllUsersAddress(), httpEntity, User[].class);
            return (ArrayList<User>) Arrays.asList(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("jzfo");
        }
        //TODO handle excepton
    }

    public User getUserByName(String username, String token) throws NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<User> responseEntity = restTemplate.postForEntity(Constants.getGetUserByNameAddress(), httpEntity, User.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new NoAccessException("jzfo");
        }
    }

    public User getUserById(int id, String token) throws NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<User> responseEntity = restTemplate.postForEntity(Constants.getGetUserByIdAddress(), httpEntity, User.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new NoAccessException("jzfo");
        }
    }

    public Map<String, String> getUserInfo(String token) throws NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(Constants.getGetUserInfoAddress(), httpEntity, Map.class);
            return (Map<String, String>) responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new NoAccessException("jzfo");
        }
    }

    public List<Admin> getManagers(int id) {
        //TODO
        try {
            RestTemplate restTemplate = new RestTemplate();
            Admin[] admins = restTemplate.getForObject(Constants.getGetManagersAddress() + id, Admin[].class);
            return Arrays.asList(admins);
        } catch (HttpClientErrorException e) {
            //TODO
        }
        return new ArrayList<>();
    }

    public void delete(String username, String token) throws NoAccessException, InvalidTokenException, NoObjectIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject,Constants.getDeleteUserAddress());
        } catch (HttpClientErrorException e) {
//TODO
            throw new NoAccessException("jzfo");
        }
    }

    public User getUserByToken(String token) throws InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<User> responseEntity = restTemplate.postForEntity(Constants.getGetUserByTokenAddress(), httpEntity, User.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }
    }

}
