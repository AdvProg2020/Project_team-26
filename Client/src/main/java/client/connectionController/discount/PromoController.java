package client.connectionController.discount;

import client.connectionController.interfaces.discount.IPromoController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import client.model.enums.*;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PromoController implements IPromoController {

    public String getRandomPromoForUserSet(String token) throws InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        try {
            return Constants.manager.getStringValueFromServerByAddress(jsonObject,addresss);
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public Promo getPromoCodeTemplateByCode(String codeId, String token) throws InvalidIdException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("codeId", codeId);
        jsonObject.put("token", token);
        try {
            return getPromoFromServer(jsonObject,addresss);
        } catch (HttpClientErrorException e) {
            throw new InvalidIdException("ksamd");
        }
    }
    private Promo getPromoFromServer(JSONObject jsonObject , String address){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Promo> responseEntity  = (restTemplate.postForEntity(address, httpEntity,Promo.class));
        return responseEntity.getBody();
    }

    public Promo getPromoCodeTemplateById(int codeId, String token) throws InvalidIdException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("codeId", codeId);
        jsonObject.put("token", token);
        try {
          return getPromoFromServer(jsonObject,addresss);
        } catch (HttpClientErrorException e) {
            throw new InvalidIdException("ksamd");
        }
    }

    public List<Promo> getAllPromoCodeForCustomer(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sortField", sortField);
        jsonObject.put("isAscending", isAscending);
        jsonObject.put("startIndex", startIndex);
        jsonObject.put("endIndex", endIndex);
        jsonObject.put("token", token);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Promo[]> responseEntity  = (restTemplate.postForEntity(address, httpEntity,Promo[].class));
            return Arrays.asList(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public int createPromoCode(Promo promo, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promo", promo);
        jsonObject.put("token", token);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Integer> responseEntity  = (restTemplate.postForEntity(address, httpEntity,Integer.class));
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void removePromoCode(int promoCodeId, String token) throws NotLoggedINException, NoAccessException, InvalidIdException, InvalidTokenException, NoObjectIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoCodeId", promoCodeId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void addCustomer(int promoId, int customerId, String token) throws NoAccessException, InvalidIdException, ObjectAlreadyExistException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("customerId", customerId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }

    }

    public void removeCustomer(int promoId, int customerId, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("customerId", customerId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void setPercent(int promoId, double percent, String token) throws InvalidIdException, NoAccessException, InvalidTokenException, InvalidDiscountPercentException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("percent", percent);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void setMaxDiscount(int promoId, long maxDiscount, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("maxDiscount", maxDiscount);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }


    public void setTime(int promoId, Date date, String type, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("date", date);
        jsonObject.put("type", type);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }
}
