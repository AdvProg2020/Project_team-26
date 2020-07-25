package client.connectionController.account;


import client.ControllerContainer;
import client.connectionController.interfaces.account.IAuthenticationController;
import client.connectionController.interfaces.account.IShowUserController;
import client.exception.*;
import client.gui.Constants;
import client.model.Account;
import client.model.Message;
import client.model.User;
import client.model.enums.MessageType;
import client.model.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.Map;

public class AuthenticationController implements IAuthenticationController {


    public AuthenticationController() {
    }

    public void login(String username, String password, String token, String captcha) throws InvalidFormatException, PasswordIsWrongException, InvalidTokenException, InvalidAuthenticationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("token", token);
        jsonObject.put("captcha", captcha);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAuthenticationControllerLoginAddress());
            User user = ((IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController)).getUserByToken(Constants.manager.getToken());
            Constants.manager.setLoggedInUser(user);
            Constants.manager.sendMessageTOWebSocket("login", new Message(user.getUsername(), "", "", MessageType.JOIN, user.getRole()));
            System.out.println("called for login online");
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidFormatException:
                    throw InvalidFormatException.getHttpException(e.getResponseBodyAsString());
                case PasswordIsWrongException:
                    throw PasswordIsWrongException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case InvalidAuthenticationException:
                    throw InvalidAuthenticationException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void register(Account account, String token) throws InvalidFormatException, InvalidAuthenticationException, NoAccessException, InvalidTokenException, AlreadyLoggedInException {
        JSONObject jsonObject = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        jsonObject.put("account", objectMapper.convertValue(account, Account.class));
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAuthenticationControllerRegister());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidFormatException:
                    throw InvalidFormatException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidAuthenticationException:
                    throw InvalidAuthenticationException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case AlreadyLoggedInException:
                    throw AlreadyLoggedInException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public String getCaptcha() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(Constants.getAuthenticationControllerGetCaptchaAddress(), String.class);
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    public void logout(String token) throws NotLoggedINException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAuthenticationControllerLogoutAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public boolean doWeHaveAManager() {
        RestTemplate restTemplate = new RestTemplate();
        Boolean responseEntity = restTemplate.getForObject(Constants.getAuthenticationControllerDoWeHaveAManagerAddress(), Boolean.class);
        return responseEntity;
    }

}
