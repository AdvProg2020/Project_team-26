package repository;

import model.Product;
import model.ProductRequest;
import model.ProductSeller;
import model.User;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends Repository<Product> {

    Product getByName(String name);

    void addRequest(Product product, User requestedBy);

    void editRequest(Product product, User requestedBy);

    void deleteRequest(int id, User requestedBy);

    void acceptRequest(int requestId);

    void rejectRequest(int requestId);

    ProductRequest getProductRequestById(int requestId);

    List<Product> getAllSortedAndFiltered(Map<String, String> filter, String sortField, boolean isAscending);

    List<Product> getAllSortedAndFilteredInOff(Map<String, String> filter, String sortField, boolean isAscending);

    List<ProductRequest> getAllRequests(String sorField, boolean isAscending);

    List<ProductRequest> getAllSellerRequests(int sellerId);

    List<Product> getAllProductsWithFilterForSellerId(Map<String, String> filter, String fieldName, boolean isAscending, int id);
}

