package client.connectionController;


import client.connectionController.interfaces.request.IRequestController;
import client.exception.*;
import client.gui.Constants;
import client.model.Request;
import client.model.User;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RequestController implements IRequestController {

    public void acceptOffRequest(int requestId, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", requestId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAcceptOffRequestِAddress());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
            e.getStatusCode().
        }

    }

    public void rejectOffRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", requestId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getRejectOffRequestِAdd());
        } catch (HttpClientErrorException e) {

        }
    }

    public void acceptProductRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", requestId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAcceptProductRequest());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }
    }

    public void rejectProductRequest(int requestId, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", requestId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getRejectProductRequest());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }
    }

    public void acceptProductSellerRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", requestId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAcceptProductSellerRequest());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }
    }

    public void rejectProductSellerRequest(int requestId, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", requestId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getRejectProductSellerRequest());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }
    }

    public List<Request> getAllRequests(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("sortField", sortField);
        jsonObject.put("isAscending", isAscending);
        jsonObject.put("startIndex", startIndex);
        jsonObject.put("endIndex", endIndex);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Request[]> responseEntity = restTemplate.postForEntity(Constants.getGetAllRequests(), httpEntity, Request[].class);
            return Arrays.asList(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }
    }

}
