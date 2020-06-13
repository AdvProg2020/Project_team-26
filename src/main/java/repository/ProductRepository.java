package repository;

import model.*;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends Repository<Product> {

    Product getByName(String name);

    void addRequest(Product product, User requestedBy);

    void editRequest(Product product, User requestedBy);

    void deleteRequest(int id, User requestedBy);

    void acceptRequest(int requestId);

    void rejectRequest(int requestId);

    List<ProductRequest> getAllRequests(String sortField, boolean isAscending);

    ProductRequest getProductRequestById(int requestId);

    List<ProductRequest> getAllSellerRequests(int sellerId);

    List<Product> getAllSortedAndFiltered(Map<String, String> filter, Pageable pageable);

    List<Product> getAllSortedAndFilteredInOff(Map<String, String> filter, Pageable pageable);

    List<Product> getAllProductsWithFilterForSeller(Map<String, String> filter, Pageable pageable, int sellerId);
}

