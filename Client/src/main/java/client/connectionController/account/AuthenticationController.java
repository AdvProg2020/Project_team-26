package client.connectionController.account;


import client.connectionController.interfaces.account.IAuthenticationController;
import client.exception.*;
import client.gui.Constants;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

public class AuthenticationController implements IAuthenticationController {


    public AuthenticationController() {
    }

    public void login(String username, String password, String token) throws InvalidFormatException, PasswordIsWrongException, InvalidTokenException, InvalidAuthenticationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAuthenticationControllerLoginAddress());
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
        jsonObject.put("account", account);
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
