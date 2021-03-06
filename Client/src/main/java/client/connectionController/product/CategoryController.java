package client.connectionController.product;

import client.connectionController.interfaces.category.ICategoryController;
import client.exception.InvalidIdException;
import client.exception.*;
import client.gui.Constants;
import client.model.Category;
import client.model.CategoryFeature;
import client.model.Product;
import client.model.enums.FeatureType;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CategoryController implements ICategoryController {
    private Category getCategoryFromServer(JSONObject jsonObject, String address) throws UnknownHttpStatusCodeException {
        // Constants.manager.<Category>getItemFromServer(jsonObject,address,Category.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Category> responseEntity = restTemplate.postForEntity(address, httpEntity, Category.class);
        return responseEntity.getBody();
    }

    private List<Product> getProductOfCategoryListFromServer(JSONObject jsonObject, String address) throws UnknownHttpStatusCodeException {
        //Constants.manager.<Product>getListItemsFromServer(jsonObject,address,Product[].class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product[]> responseEntity = restTemplate.postForEntity(address, httpEntity, Product[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    private List<Category> getCategoryListFromServer(JSONObject jsonObject, String address) throws UnknownHttpStatusCodeException {
        //Constants.manager.<Category>getListItemsFromServer(jsonObject,address,Category[].class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Category[]> responseEntity = restTemplate.postForEntity(address, httpEntity, Category[].class);
        return Arrays.asList(responseEntity.getBody());
    }


    public void addCategory(int patternId, Category newCategory, String token) throws InvalidIdException, ObjectAlreadyExistException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newCategory", newCategory);
        jsonObject.put("patternId", patternId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCategoryControllerAddCategoryAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case ObjectAlreadyExistException:
                    throw ObjectAlreadyExistException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void addAttribute(int id, String attributeName, FeatureType featureType, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("attributeName", attributeName);
        jsonObject.put("featureType", featureType);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCategoryControllerAddAttributeAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void removeAttribute(int id, String attributeName, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("attributeName", attributeName);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCategoryControllerRemoveAttributeAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }


    public void removeACategory(int id, int parentId, String token) throws InvalidIdException, NoAccessException, InvalidTokenException, NoObjectIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("parentId", parentId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCategoryControllerRemoveACategoryAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NoObjectIdException:
                    throw NoObjectIdException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void addProduct(int id, int productId, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("productId", productId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCategoryControllerAddProductAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void removeProduct(int id, int productId, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("productId", productId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getCategoryControllerRemoveProductAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }


    public List<Category> getAllCategories(int id, String token) throws InvalidIdException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Category categories[] = restTemplate.getForObject(Constants.getCategoryControllerGetAllCategoriesAddress() + "/" + id, Category[].class);
            return Arrays.asList(categories);
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
        }
    }

    @Override
    public void getExceptionOfIfCategoryExist(int id, String token) throws InvalidIdException {

    }

    public List<CategoryFeature> getAttribute(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        //Constants.manager.<Category>getListItemsFromServer(jsonObject,address,Category[].class);
        HttpHeaders httpHeaders = new HttpHeaders();
        try {

            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<CategoryFeature[]> responseEntity = restTemplate.postForEntity(Constants.getCategoryControllerGetAttributeAddress(), httpEntity, CategoryFeature[].class);
            return Arrays.asList(responseEntity.getBody());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }

    }

    public Category getCategory(int id, String token) throws InvalidIdException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Category category = restTemplate.getForObject(Constants.getCategoryControllerGetCategoryAddress() + "/" + id, Category.class);
            return category;
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
        }
    }

    public List<Category> getSubCategories(int id, String token) throws InvalidIdException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Category[] categories = restTemplate.getForObject(Constants.getCategoryControllerGetSubCategories() + "/" + id, Category[].class);
            return Arrays.asList(categories);
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
        }
    }

    public Category getCategoryByName(String name, String token) throws InvalidIdException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Category category = restTemplate.getForObject(Constants.getCategoryControllerGetCategoryByNameAddress() + "/" + name, Category.class);
            return category;
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
        }
    }

    public List<Product> getProducts(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            return getProductOfCategoryListFromServer(jsonObject, Constants.getCategoryControllerGetProductsAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public List<Product> getAllProducts(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, int id, String token) throws InvalidIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("filter",filter);
        jsonObject.put("sortField",sortField);
        jsonObject.put("isAscending",isAscending);
        jsonObject.put("startIndex",startIndex);
        jsonObject.put("endIndex",endIndex);
        jsonObject.put("id", id);
        jsonObject.put("token", token);
        try {
            return getProductOfCategoryListFromServer(jsonObject,Constants.getCategoryControllerGetAllProductsAddress());
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
        }
    }

    public List<Product> getAllProductsInOff(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, int id, String token) throws InvalidIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("filter",filter);
        jsonObject.put("sortField",sortField);
        jsonObject.put("isAscending",isAscending);
        jsonObject.put("startIndex",startIndex);
        jsonObject.put("endIndex",endIndex);
        jsonObject.put("id",id);
        jsonObject.put("token",token);
        try {
            return getProductOfCategoryListFromServer(jsonObject, Constants.getCategoryControllerGetAllProductsInOffAddress());
        } catch (UnknownHttpStatusCodeException e) {
            throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
        }
    }
}
