package client.controller.account;


import client.exception.*;
import client.gui.Constants;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
public class AuthenticationController {


    public AuthenticationController() {
    }

    public void login(String username, String password, String token) throws InvalidFormatException, PasswordIsWrongException, InvalidTokenException, InvalidAuthenticationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForLocation(Constants.loginAddress, httpEntity);
        } catch (HttpClientErrorException e) {

        }
        //TODO handle excepton
    }

    public void register(Account account, String token) throws InvalidFormatException, NoAccessException, InvalidAuthenticationException, NoAccessException, InvalidTokenException, AlreadyLoggedInException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account", account);
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForLocation(Constants.registerAddress, httpEntity);
        } catch (HttpClientErrorException e) {

        }
        //TODO handle excepton
    }

    public void logout(String token) throws NotLoggedINException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForLocation(Constants.logoutAddress, httpEntity);
        } catch (HttpClientErrorException e) {

        }
        //TODO handle excepton
    }

    public boolean doWeHaveAManager() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Boolean responseEntity = restTemplate.getForObject(Constants.doWeHaveManagerAddress, Boolean.class);
            return responseEntity;
        } catch (HttpClientErrorException e) {
//TODO
        }
        return false;
    }

}
