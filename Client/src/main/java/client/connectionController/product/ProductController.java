package client.connectionController.product;


import client.connectionController.interfaces.product.IProductController;
import client.exception.*;
import client.gui.Constants;
import client.model.Order;
import client.model.Product;
import client.model.ProductSeller;
import client.model.Request;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductController implements IProductController {

    private Product getProductFromServer(JSONObject jsonObject, String address) throws HttpClientErrorException {
        // Constants.manager.<Product>getItemFromServer(jsonObject,address,Product.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(address, httpEntity, Product.class);
        return responseEntity.getBody();
    }

    private List<Product> getProductListFromServer(JSONObject jsonObject, String address) throws HttpClientErrorException {
        //Constants.manager.<Product>getListItemsFromServer(jsonObject,address,Product[].class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product[]> responseEntity = restTemplate.postForEntity(address, httpEntity, Product[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public void createProduct(Product product, String token) throws ObjectAlreadyExistException, NotSellerException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("product", product);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerCreateProductAddress());
        } catch (HttpClientErrorException e) {
            throw new NotSellerException("ksamd");
        }
    }

    public void addSeller(int productId, ProductSeller productSeller, String token) throws NotSellerException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", productId);
        jsonObject.put("token", token);
        jsonObject.put("productSeller", productSeller);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerAddSellerAddress());
        } catch (HttpClientErrorException e) {
            throw new NotSellerException("ksamd");
        }
    }

    public Product getProductById(int id, String token) throws InvalidIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            return getProductFromServer(jsonObject, Constants.getGetProductByIdAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidIdException("ksamd");
        }
    }

    public Product getProductByName(String name, String token) throws NoObjectIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("token", token);
        try {
            return getProductFromServer(jsonObject, Constants.getProductControllerGetProductByNameAddress());
        } catch (HttpClientErrorException e) {
            throw new NoObjectIdException("ksamd");
        }
    }

    public void removeProduct(int id, String token) throws InvalidIdException, InvalidTokenException, NoAccessException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerRemoveProductAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void removeSeller(int productId, int productSellerId, String token) throws InvalidIdException, InvalidTokenException, NoAccessException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", productId);
        jsonObject.put("productSellerId", productSellerId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerRemoveSellerAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public List<Product> getAllProductWithFilter(Map<String, String> filter, String fieldName, boolean isAscending, int startIndex, int endIndex, String token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("filter", filter);
        jsonObject.put("fieldName", fieldName);
        jsonObject.put("isAscending", isAscending);
        jsonObject.put("startIndex", startIndex);
        jsonObject.put("endIndex", endIndex);
        try {
            return getProductListFromServer(jsonObject, Constants.getProductControllerGetAllProductWithFilterAddress());
        } catch (HttpClientErrorException e) {
            return new ArrayList<>();
        }
    }

    public List<Product> getAllProductWithFilterForSellerId(Map<String, String> filter, String fieldName, boolean isAscending, int startIndex, int endIndex, String token) throws NotLoggedINException, InvalidTokenException, NoAccessException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("filter", filter);
        jsonObject.put("fieldName", fieldName);
        jsonObject.put("isAscending", isAscending);
        jsonObject.put("startIndex", startIndex);
        jsonObject.put("endIndex", endIndex);
        try {
            return getProductListFromServer(jsonObject, Constants.getProductControllerGetAllProductWithFilterForSellerIdAddress());
        } catch (HttpClientErrorException e) {
            return new ArrayList<>();
        }
    }

    public ProductSeller getProductSellerByIdAndSellerId(int productId, String token) throws InvalidIdException, InvalidTokenException, NotLoggedINException, NoAccessException, NoObjectIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("productId", productId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<ProductSeller> responseEntity = restTemplate.postForEntity(Constants.getProductControllerGetProductSellerByIdAndSellerIdAddress(), httpEntity, ProductSeller.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
//TODO
            throw new InvalidTokenException("jhf");
        }
    }

    public void editProduct(int id, Product newProduct, String token) throws InvalidIdException, NotSellerException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newProduct", newProduct);
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControlerEditProductAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void editProductSeller(int id, ProductSeller newProductSeller, String token) throws InvalidIdException, InvalidTokenException, NotSellerException, NoAccessException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newProductSeller", newProductSeller);
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControlerEditProductSellerAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

}
