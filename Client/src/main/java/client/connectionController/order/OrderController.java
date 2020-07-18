package client.connectionController.order;

import client.connectionController.interfaces.order.IOrderController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import net.minidev.json.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderController implements IOrderController {


    @Override
    public List<Order> getOrders(String token) throws NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        try {
            return getListFromServer(jsonObject, Constants.getOrderControllerGetOrdersAddress());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("lkcsa");
        }
    }

    @Override
    public List<Order> getOrdersWithFilter(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("sortField", sortField);
        jsonObject.put("isAscending", isAscending);
        jsonObject.put("startIndex", startIndex);
        jsonObject.put("endIndex", endIndex);
        try {
            return getListFromServer(jsonObject, Constants.getOrderControllerGetOrdersWithFilterAddress());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("lkcsa");
        }
    }

    @Override
    public List<Customer> getProductBuyerByProductId(int productId, String token) throws InvalidTokenException, NotLoggedINException, NoAccessException, InvalidIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("productId", productId);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Customer[]> responseEntity = restTemplate.postForEntity(Constants.getOrderControllerGetProductBuyerByProductIdAddress(), httpEntity, Customer[].class);
            return Arrays.asList(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("lkcsa");
        }
    }

    @Override
    public Order getASingleOrder(int id, String token) throws NoAccessException, InvalidIdException, NoObjectIdException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("id", id);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Order> responseEntity = restTemplate.postForEntity(Constants.getOrderControllerGetASingleOrderAddress(), httpEntity, Order.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("lkcsa");
        }
    }

    @Override
    public List<Order> getOrderHistoryForSeller(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("sortField", sortField);
        jsonObject.put("isAscending", isAscending);
        jsonObject.put("startIndex", startIndex);
        jsonObject.put("endIndex", endIndex);
        try {
            return getListFromServer(jsonObject, Constants.getOrderControllerGetOrderHistoryForSellerAddress());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("lkcsa");
        }
    }

    private List<Order> getListFromServer(JSONObject jsonObject, String address) throws HttpClientErrorException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Order[]> responseEntity = restTemplate.postForEntity(address, httpEntity, Order[].class);
        return Arrays.asList(responseEntity.getBody());
    }
}
