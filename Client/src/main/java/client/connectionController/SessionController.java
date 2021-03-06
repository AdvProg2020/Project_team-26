package client.connectionController;

import client.connectionController.interfaces.session.ISessionController;
import client.exception.HttpExceptionEquivalent;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.exception.NotLoggedINException;
import client.gui.Constants;
import client.model.*;
import client.model.enums.Role;
import com.google.gson.Gson;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

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
            ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(Constants.getSessionControllerIsUserLoggedInAddress(), httpEntity, Boolean.class);
            return responseEntity.getBody();
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
        }
    }


    public String createToken() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(Constants.getSessionControllerCreateTokenAddress(), String.class);
    }

    public Role getUserRole(String token) throws NotLoggedINException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Role> responseEntity = restTemplate.postForEntity(Constants.getSessionControllerGetUserRoleAddress(), httpEntity, Role.class);
            return responseEntity.getBody();
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
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
            ResponseEntity<User> responseEntity = restTemplate.postForEntity(Constants.getSessionControllerGetUserAddress(), httpEntity, User.class);
            return responseEntity.getBody();
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
        }

    }

    @Override
    public double getCommission(String token) throws NoAccessException, InvalidTokenException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(Constants.getSessionControllerGetCommissionAddress() + "/" + token, Double.class);
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    @Override
    public long getMinCredit(String token) throws NoAccessException, InvalidTokenException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(Constants.getSessionControllerGetMinCreditAddress() + "/" + token, Long.class);
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    @Override
    public void setCommission(double amount, String token) throws NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        Gson gson = new Gson();
        String amountString = gson.toJson(amount);
        jsonObject.put("amount", amountString);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getSessionControllerSetCommissionAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }

    }

    @Override
    public void setMinCredit(long amount, String token) throws NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        Gson gson = new Gson();
        String amountString = gson.toJson(amount);
        jsonObject.put("amount", amountString);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getSessionControllerSetMinCreditAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }
}
