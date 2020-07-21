package client.connectionController.account;

import client.connectionController.interfaces.account.IUserInfoController;
import client.exception.*;
import client.gui.Constants;
import net.minidev.json.JSONObject;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import java.util.Map;


public class UserInfoController implements IUserInfoController {


    public void changePassword(String oldPassword, String newPassword, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("oldPassword", oldPassword);
        jsonObject.put("newPassword", newPassword);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangePasswordWithOldPasswordAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void changePassword(String newPassword, String token) throws InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newPassword", newPassword);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangePasswordAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void changeImage(byte[] image, String token) throws InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("image", org.apache.commons.codec.binary.Base64.encodeBase64String(image));
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangeImageAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }


    public void changeInfo(String key, String value, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("value", value);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangeInfoAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case InvalidAuthenticationException:
                    throw InvalidAuthenticationException.getHttpException(e.getResponseBodyAsString());
                case InvalidFormatException:
                    throw InvalidFormatException.getHttpException(e.getResponseBodyAsString());
                case NoSuchField:
                    throw NoSuchField.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void changeBalance(Long newCredit, String token) throws InvalidTokenException, NotLoggedINException, InvalidAuthenticationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newCredit", newCredit);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangeBalanceAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidAuthenticationException:
                    throw InvalidAuthenticationException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public String getCompanyName(String token) throws InvalidTokenException, NotLoggedINException, NoSuchField {
        try {
            return Constants.manager.getStringValueFromServerByAddress(Constants.getUserInfoControllerGetCompanyNameAddress(), token);
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case NoSuchField:
                    throw NoSuchField.getHttpException(e.getResponseBodyAsString());
                default:
                    return null;
            }
        }
    }

    public void changeInfo(Map<String, String> values, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("values", values);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getUserInfoControllerChangeMultipleInfoAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case InvalidAuthenticationException:
                    throw InvalidAuthenticationException.getHttpException(e.getResponseBodyAsString());
                case InvalidFormatException:
                    throw InvalidFormatException.getHttpException(e.getResponseBodyAsString());
                case NoSuchField:
                    throw NoSuchField.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public String getBalance(String token) throws NotLoggedINException, InvalidTokenException {
        try {
            return Constants.manager.getStringValueFromServerByAddress(Constants.getUserInfoControllerGetBalanceAddress(), token);
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                default:
                    return null;
            }
        }
    }

    public String getRole(String token) throws NotLoggedINException, InvalidTokenException {
        try {
            return Constants.manager.getStringValueFromServerByAddress(Constants.getUserInfoControllerGetRoleAddress(), token);
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                default:
                    return null;
            }
        }
    }
}
