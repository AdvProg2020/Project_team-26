package client.connectionController;

import client.connectionController.interfaces.IBankController;
import client.exception.*;
import client.gui.Constants;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.util.Map;

public class BankController implements IBankController {
    private String sendRequest(JSONObject jsonObject, String address) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(address, httpEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public String createAccount(String userName, String password, String firstName, String lastName, String repeatedPass) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstname", firstName);
        jsonObject.put("lastname", lastName);
        jsonObject.put("username", userName);
        jsonObject.put("password", password);
        jsonObject.put("repeat_password", repeatedPass);
        return sendRequest(jsonObject, Constants.getBankControllerCreateAccountAddress());
    }


    @Override
    public String chargeAccount(int bankSourceAccountId, String description, long money, String token) throws InvalidTokenException, NotLoggedINException, NoAccessException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", "" + money);
        jsonObject.put("userId", "" + bankSourceAccountId);
        jsonObject.put("description", description);
        jsonObject.put("token", token);
        try {
            return sendRequest(jsonObject, Constants.getBankControllerChargeAccountAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    @Override
    public String withdrawFromAccount(int bankSourceAccountId, String description, long money, String token) throws InvalidTokenException, NotLoggedINException, NoAccessException, NotEnoughCreditException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", "" + money);
        jsonObject.put("userId", "" + bankSourceAccountId);
        jsonObject.put("description", description);
        jsonObject.put("token", token);
        try {
            return sendRequest(jsonObject, Constants.getBankControllerWithdrawFromAccountAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw NotEnoughCreditException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    @Override
    public String getToken(String username, String password, String token) throws InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("token", token);
        try {
            return sendRequest(jsonObject, Constants.getBankControllerGetTokenAddress());
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
        }
    }
}
