package client.connectionController.product;


import client.connectionController.interfaces.product.IProductController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import com.google.gson.Gson;


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

    public void createProduct(Product product, String token, byte[] image) throws ObjectAlreadyExistException, NotSellerException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        Gson gson = new Gson();
        String productString = gson.toJson(product);
        jsonObject.put("product", productString);
        jsonObject.put("image", org.apache.commons.codec.binary.Base64.encodeBase64String(image));
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerCreateProductAddress());
        } catch (HttpClientErrorException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotSellerException:
                    throw NotSellerException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case ObjectAlreadyExistException:
                    throw ObjectAlreadyExistException.getHttpException(e.getResponseBodyAsString());
            }
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
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotSellerException:
                    throw NotSellerException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public Product getProductById(int id, String token) throws InvalidIdException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Product product = restTemplate.getForObject(Constants.getProductControllerGetProductByIdAddress() + "/" + id, Product.class);
            return product;
        } catch (HttpClientErrorException e) {
            throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
        }
    }

    public Product getProductByName(String name, String token) throws NoObjectIdException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Product product = restTemplate.getForObject(Constants.getProductControllerGetProductByNameAddress() + "/" + name, Product.class);
            return product;
        } catch (UnknownHttpStatusCodeException e) {
            throw NoObjectIdException.getHttpException(e.getResponseBodyAsString());
        }
    }

    public void removeProduct(int id, String token) throws InvalidIdException, InvalidTokenException, NoAccessException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerRemoveProductAddress());
        } catch (HttpClientErrorException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
            }
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
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
            }
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
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
            }
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
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotLoggedInException:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NoObjectIdException:
                    throw NoObjectIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void editProduct(int id, Product newProduct, String token) throws InvalidIdException, NotSellerException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newProduct", newProduct);
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerEditProductAddress());
        } catch (HttpClientErrorException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotSellerException:
                    throw NotSellerException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    @Override
    public void setFileForProduct(String newProduct, String token, byte[] file) {
        JSONObject jsonObject = new JSONObject();
        Gson gson = new Gson();
        jsonObject.put("token", token);
        jsonObject.put("file", org.apache.commons.codec.binary.Base64.encodeBase64String(file));
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerSetFileForProductAddress() + "/" + newProduct);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    public void editProductSeller(int id, ProductSeller newProductSeller, String token) throws InvalidIdException, InvalidTokenException, NotSellerException, NoAccessException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newProductSeller", newProductSeller);
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getProductControllerEditProductSellerAddress());
        } catch (HttpClientErrorException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotSellerException:
                    throw NotSellerException.getHttpException(e.getResponseBodyAsString());
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public Product getProductByProductSellerId(int productSellerId) {
        RestTemplate restTemplate = new RestTemplate();
        Product product = restTemplate.getForObject(Constants.getProductControllerGetProductByProductSellerIdAddress() + "/" + productSellerId, Product.class);
        return product;
    }

    public String getProductNameByProductSellerId(int productSellerId) {
        RestTemplate restTemplate = new RestTemplate();
        String name = restTemplate.getForObject(Constants.getProductControllerGetProductNameByProductSellerIdAddress() + "/" + productSellerId, String.class);
        return name;
    }


}
