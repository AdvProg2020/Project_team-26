package repository;

import model.Product;
import model.ProductRequest;
import model.ProductSeller;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends Repository<Product> {

    Product getByName(String name);

    void addRequest(Product product);

    void acceptRequest(int requestId);

    void rejectRequest(int requestId);

    ProductRequest getProductRequestById(int requestId);

    void editRequest(Product product);

    void deleteRequest(int id);

    List<Product> getAllSortedAndFiltered(Map<String, String> filter, String sortField, boolean isAscending);

    List<Product> getAllSortedAndFilteredInOff(Map<String, String> filter, String sortField, boolean isAscending);

    List<ProductRequest> getAllRequests(String sorField, boolean isAscending);

    List<ProductRequest> getAllSellerRequests(int sellerId);

    List<Product> getAllProductsWithFilterForSellerId(Map<String, String> filter, String fieldName, boolean isAscending, int id);
}

