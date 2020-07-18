package client.connectionController;

import client.connectionController.interfaces.session.ISessionController;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.exception.NotLoggedINException;
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

import java.util.Map;

public class SessionController implements ISessionController {


    public boolean isUserLoggedIn(String token) throws InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(Constants.getIsUserLoggedInAddress(), httpEntity, Boolean.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }

    }


    public String createToken() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(Constants.getCreateTokenAddress(), String.class);
        } catch (HttpClientErrorException e) {
//TODO
            return "null";
        }
    }

    public Role getUserRole(String token) throws NotLoggedINException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Role> responseEntity = restTemplate.postForEntity(Constants.getGetUserRoleAddress(), httpEntity, Role.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new NotLoggedINException("jzfo");
        }

    }

    public User getUser(String token) throws InvalidTokenException {
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
            throw new InvalidTokenException("jzfo");
        }

    }
}
