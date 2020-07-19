package client.connectionController.cart;


import client.connectionController.interfaces.cart.ICartController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import javafx.util.Pair;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import repository.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class CartController implements ICartController {

    public CartController() {
    }

    public void addOrChangeProduct(int productSellerId, int amount, String token)
            throws InvalidIdException, NotEnoughProductsException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productSellerId", productSellerId);
        jsonObject.put("amount", amount);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCartControllerAddOrChangeProductAddress());
        } catch (UnknownHttpStatusCodeException e) {
            try {
                switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                    case InvalidIdException:
                        throw InvalidIdException.getHttpException(e);
                    case NotEnoughProductsException:
                        throw NotEnoughProductsException.getHttpException(e);
                    case InvalidTokenException:
                        throw InvalidTokenException.getHttpException(e);
                }
            } catch (IOException e1) {
                System.out.println(e1.getStackTrace());
            }
        }
    }

    public Cart getCart(String token) throws InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Cart> responseEntity = restTemplate.postForEntity(Constants.getCartControllerGetCartAddress(), httpEntity, Cart.class);
            return responseEntity.getBody();
        } catch (UnknownHttpStatusCodeException e) {
            try {
                switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                    case InvalidIdException:
                        throw InvalidTokenException.getHttpException(e);
                }
            } catch (IOException e1) {
                System.out.println(e1.getStackTrace());
            }
        }
    }

    public void setAddress(String address, String token) throws InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", address);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCartControllerSetAddressAddress());
        } catch (UnknownHttpStatusCodeException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void usePromoCode(String promoCode, String token) throws InvalidTokenException, InvalidPromoCodeException, PromoNotAvailableException, NotLoggedINException, NoAccessException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoCode", promoCode);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCartControllerUsePromoCodeAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void checkout(String token)
            throws InvalidTokenException, NotLoggedINException, NoAccessException, NotEnoughProductsException, NotEnoughCreditException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCartControllerCheckoutAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }


    public long getTotalPrice(Cart cart, String token) throws InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("cart", cart);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Long> responseEntity = restTemplate.postForEntity(Constants.getCartControllerGetTotalPriceAddress(), httpEntity, Long.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }
    }

    public int getAmountInCartBySellerId(int productSelleId, String token) throws InvalidTokenException, NoSuchObjectException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("productSelleId", productSelleId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Integer> responseEntity = restTemplate.postForEntity(Constants.getCartControllerGetAmountInCarBySellerIdAddress(), httpEntity, Integer.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }
    }
}