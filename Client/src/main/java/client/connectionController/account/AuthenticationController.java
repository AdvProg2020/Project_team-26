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
public class AuthenticationController implements IAuthenticationController {


    public AuthenticationController()  {
    }

    public void login(String username, String password, String token) throws InvalidFormatException, PasswordIsWrongException, InvalidTokenException, InvalidAuthenticationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject,Constants.getAuthenticationControllerLoginAddress());
        } catch (HttpClientErrorException e) {

        }
        //TODO handle excepton
    }

    public void register(Account account, String token) throws InvalidFormatException, NoAccessException, InvalidAuthenticationException, NoAccessException, InvalidTokenException, AlreadyLoggedInException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account", account);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject,Constants.getAuthenticationControllerRegister());
        } catch (HttpClientErrorException e) {

        }
        //TODO handle excepton
    }

    public void logout(String token) throws NotLoggedINException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject,Constants.getAuthenticationControllerLogoutAddress());
        } catch (HttpClientErrorException e) {

        }
        //TODO handle excepton
    }

    public boolean doWeHaveAManager() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Boolean responseEntity = restTemplate.getForObject(Constants.getAuthenticationControllerDoWeHaveAManagerAddress(), Boolean.class);
            return responseEntity;
        } catch (HttpClientErrorException e) {
//TODO
        }
        return false;
    }

}
