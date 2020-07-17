package client.controller.account;

import client.gui.Account;
import client.gui.Constants;
import exception.*;
import javafx.util.Pair;
import model.Customer;
import model.Promo;
import model.Session;
import model.User;
import model.enums.Role;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import repository.PromoRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AuthenticationController {


    public AuthenticationController() {
    }

    public void login(String username, String password, String token) throws InvalidFormatException, PasswordIsWrongException, InvalidTokenException, InvalidAuthenticationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("token", Constants.manager.getToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constants.loginAddress, httpEntity, String.class);
        }catch (HttpClientErrorException e ){

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
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constants.registerAddress, httpEntity, String.class);
        }catch (HttpClientErrorException e ){

        }
        //TODO handle excepton
    }

    public void logout(String token) throws NotLoggedINException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            userSession.logout();
        }
    }

    public boolean doWeHaveAManager() {
        return userRepository.doWeHaveAManager();
    }

}
