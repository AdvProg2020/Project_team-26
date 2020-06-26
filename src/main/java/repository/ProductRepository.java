package repository;

import model.*;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends Repository<Product> {

    Product getByName(String name);

    void addRequest(Product product, User requestedBy);

    void editRequest(Product product, User requestedBy);

    void deleteRequest(int id, User requestedBy);

    Product getProductBySellerId(int productId, int sellerId);

    List<Product> getAllSortedAndFiltered(Map<String, String> filter, Pageable pageable);

    List<Product> getAllSortedAndFilteredInOff(Map<String, String> filter, Pageable pageable);

    List<Product> getAllProductsWithFilterForSeller(Map<String, String> filter, Pageable pageable, int sellerId);
}

