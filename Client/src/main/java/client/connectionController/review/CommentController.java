package client.connectionController.review;


import client.connectionController.interfaces.review.ICommentController;
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

import java.util.Arrays;
import java.util.List;

public class CommentController implements ICommentController {

    public void addComment(String description, String title, int productId, String token) throws NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", description);
        jsonObject.put("title", title);
        jsonObject.put("productId", productId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.addCommentAddress);
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }
    }

    public void confirmComment(int id, String token) throws InvalidTokenException, NoAccessException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.confirmCommentAddress);
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }

    }

    public void rejectComment(int id, String token) throws InvalidTokenException, NoAccessException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.rejectCommentAddress);
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }

    }

    public List<Comment> getAllComments(String token) throws InvalidTokenException, NoAccessException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        try {
            return getCommentListFromServer(jsonObject, Constants.getAllCommentsAdress);
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("lkcsa");
        }

    }

    public List<Comment> getConfirmedComments(int productId, String token) throws InvalidIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("productId", productId);
        try {
            return getCommentListFromServer(jsonObject, Constants.getConfirmedCommentsAddresss);
        } catch (HttpClientErrorException e) {
            throw new InvalidIdException("lsad");
        }
    }

    private List<Comment> getCommentListFromServer(JSONObject jsonObject, String address) throws HttpClientErrorException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Comment[]> responseEntity = restTemplate.postForEntity(address, httpEntity, Comment[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public void removeComment(int id, String token) throws NoAccessException, InvalidTokenException, NoObjectIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.removeCommentAddress);
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }
    }

}
