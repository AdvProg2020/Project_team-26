package client.connectionController.discount;

import client.connectionController.interfaces.discount.IOffController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OffController implements IOffController {


    public void createNewOff(Off newOff, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newOff", newOff);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerCreateNewOffAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }

    }

    public void addProductToOff(Off off, int productId, long priceInOff, double percent, boolean isFirstTime, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("off", off);
        jsonObject.put("productId", productId);
        jsonObject.put("priceInOff", priceInOff);
        jsonObject.put("percent", percent);
        jsonObject.put("isFirstTime", isFirstTime);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case ObjectAlreadyExistException:
                    throw ObjectAlreadyExistException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void removeProductFromOff(Off off, int productId, boolean isForAdd, String token) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("off", off);
        jsonObject.put("productId", productId);
        jsonObject.put("isForAdd", isForAdd);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerRemoveProductFromOffAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case ObjectAlreadyExistException:
                    throw ObjectAlreadyExistException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void removeAOff(int id, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerRemoveAOffAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public List<Product> getAllProductWithOff(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, String token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sortField", sortField);
        jsonObject.put("isAscending", isAscending);
        jsonObject.put("startIndex", startIndex);
        jsonObject.put("endIndex", endIndex);
        jsonObject.put("filter", filter);
        jsonObject.put("token", token);
        // Constants.manager.<Product>getItemFromServer(jsonObject,address,Product.class);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Product[]> responseEntity = restTemplate.postForEntity(Constants.getOffControllerGetAllProductsWithOffAddress(), httpEntity, Product[].class);
            return Arrays.asList(responseEntity.getBody());
        } catch (UnknownHttpStatusCodeException e) {
            return new ArrayList<>();
        }
    }

    public List<Off> getAllOfForSellerWithFilter(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sortField", sortField);
        jsonObject.put("isAscending", isAscending);
        jsonObject.put("startIndex", startIndex);
        jsonObject.put("endIndex", endIndex);
        jsonObject.put("token", token);
        // Constants.manager.<Product>getItemFromServer(jsonObject,address,Product.class);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Off[]> responseEntity = restTemplate.postForEntity(Constants.getOffControllerGetAllOfForSellerWithFilterAddress(), httpEntity, Off[].class);
            return Arrays.asList(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("Sfdf");
        }
    }

    public Off getOff(int id, String token) throws InvalidIdException {
      /*  JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        // Constants.manager.<Product>getItemFromServer(jsonObject,address,Product.class);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Off> responseEntity = restTemplate.postForEntity(Constants.getOffControllerGetOffAddress(), httpEntity, Off.class);
            return responseEntity.getBody();
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                default:
                    return null;
            }
        }*/
        try {
            RestTemplate restTemplate = new RestTemplate();
            Off off = restTemplate.getForObject(Constants.getOffControllerGetOffAddress() + "/" + id, Off.class);
            return off;
        } catch (HttpClientErrorException e) {
          throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
        }
    }

    public void edit(Off newOff, int id, String token) throws NoAccessException, InvalidTokenException, InvalidIdException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("newOff", newOff);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerEditAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }
}
